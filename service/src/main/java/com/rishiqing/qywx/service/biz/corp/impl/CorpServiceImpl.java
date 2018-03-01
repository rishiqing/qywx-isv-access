package com.rishiqing.qywx.service.biz.corp.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.common.eventbus.AsyncEventBus;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.common.exception.ActiveCorpException;
import com.rishiqing.qywx.service.biz.corp.CorpService;
import com.rishiqing.qywx.service.common.corp.CorpAppManageService;
import com.rishiqing.qywx.service.common.corp.CorpManageService;
import com.rishiqing.qywx.service.common.corp.CorpSuiteManageService;
import com.rishiqing.qywx.service.common.corp.CorpTokenManageService;
import com.rishiqing.qywx.service.common.fail.CallbackFailService;
import com.rishiqing.qywx.service.common.isv.GlobalSuite;
import com.rishiqing.qywx.service.common.isv.SuiteTokenManageService;
import com.rishiqing.qywx.service.constant.ServiceConstant;
import com.rishiqing.qywx.service.event.message.CorpSuiteMessage;
import com.rishiqing.common.exception.HttpException;
import com.rishiqing.qywx.service.event.service.AsyncService;
import com.rishiqing.qywx.service.model.corp.*;
import com.rishiqing.qywx.service.model.isv.SuiteTokenVO;
import com.rishiqing.qywx.service.util.http.HttpUtil;
import com.rishiqing.qywx.service.util.http.converter.Json2BeanConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class CorpServiceImpl implements CorpService {
    private static final Logger logger = LoggerFactory.getLogger("SERVICE_CORP_TRANSFER_LOGGER");
    @Autowired
    private SuiteTokenManageService suiteTokenManageService;
    @Autowired
    private CorpSuiteManageService corpSuiteManageService;
    @Autowired
    private CorpAppManageService corpAppManageService;
    @Autowired
    private CorpTokenManageService corpTokenManageService;
    @Autowired
    private CorpManageService corpManageService;
    @Autowired
    private HttpUtil httpUtil;
    @Autowired
    private AsyncService asyncService;
    @Autowired
    private CallbackFailService callbackFailService;
    @Autowired
    private GlobalSuite suite;

    @Override
    public void activeCorp(String authCode) throws ActiveCorpException {
        try {
            logger.info("----begin to active corp----" + new Date());
            String suiteKey = suite.getSuiteKey();
            SuiteTokenVO suiteToken = suiteTokenManageService.getSuiteToken(suiteKey);
            JSONObject json = httpUtil.getPermanentCode(suiteToken, authCode);
            CorpSuiteVO corpSuiteVO = Json2BeanConverter.generateCorpSuite(suiteKey, null, json);
            //  异步获取企业的组织架构
            asyncService.sendToFetchCorpAll(corpSuiteVO.getPermanentCode());
            logger.info("----end active corp----" + new Date());
        } catch (Exception e) {
            //  致命错误，将导致无法获知用户已经开通应用
            throw new ActiveCorpException("active corp error", e);
        }
    }

    @Override
    public CorpVO fetchAndSaveCorpInfo(SuiteTokenVO suiteToken, CorpSuiteVO corpSuite){
        JSONObject json = httpUtil.getCorpAuthInfo(suiteToken, corpSuite);

        String suiteKey = suiteToken.getSuiteKey();
        //1. 保存corp信息
        CorpVO corpVO = Json2BeanConverter.generateCorp(json);
        String corpId = corpVO.getCorpId();
        corpManageService.saveOrUpdateCorp(corpVO);

        //2. 保存corpSuite信息
        CorpSuiteVO corpSuiteVO = Json2BeanConverter.generateCorpSuite(suiteKey, corpId, json);
        corpSuiteManageService.saveCorpSuite(corpSuiteVO);

        //3. 保存corpToken信息
        CorpTokenVO corpTokenVO = Json2BeanConverter.generateCorpToken(suiteKey, corpId, json);
        corpTokenManageService.saveCorpToken(corpTokenVO);

        //4. 保存corpApp信息
        //4.1  corpApp信息的问题
        //TODO 如果用户在选择可见范围时，没有选择全公司可见，那么需要提示用户
        List<CorpAppVO> list = Json2BeanConverter.generateCorpAppList(suiteKey, corpId, json);
        if(list.size() > ServiceConstant.CORP_APP_MAX_LIMIT){
            //  报出警告，如果list数量过大，需要修改成批量插入
            logger.warn("too many CorpAppVO to be saved simultaneously, may cause db performance problem, number is {}", list.size());
        }
        for(CorpAppVO corpAppVO : list){
            corpAppManageService.saveCorpApp(corpAppVO);
        }
        return corpVO;
    }

    @Override
    public CorpVO fetchAndChangeCorpInfo(SuiteTokenVO suiteToken, CorpSuiteVO corpSuite){
        JSONObject json = httpUtil.getCorpAuthInfo(suiteToken, corpSuite);
        String suiteKey = suiteToken.getSuiteKey();
        //1. 保存corp信息
        CorpVO corpVO = Json2BeanConverter.generateCorp(json);
        String corpId = corpVO.getCorpId();
        corpManageService.saveOrUpdateCorp(corpVO);

        //2. 保存corpApp信息，这里需要进行新旧CorpApp的对比
        List<CorpAppVO> list = Json2BeanConverter.generateCorpAppList(suiteKey, corpId, json);
        if(list.size() > ServiceConstant.CORP_APP_MAX_LIMIT){
            //  报出警告，如果list数量过大，需要修改成批量插入
            logger.warn("too many CorpAppVO to be saved simutaniously, may cause db performance problem, number is {}", list.size());
        }
        List<CorpAppVO> oldList = corpAppManageService.listCorpApp(corpId);
        for(CorpAppVO oldCorpApp : oldList){
            //  如果oldCorpApp在list中不存在，则执行删除
            boolean exists = false;
            for(CorpAppVO changedCorpApp : list){
                if(oldCorpApp.getAgentId().equals(changedCorpApp.getAgentId())){
                    exists = true;
                    break;
                }
            }
            if(!exists){
                corpAppManageService.removeCorpApp(oldCorpApp.getAppId(), oldCorpApp.getCorpId());
            }
        }
        for(CorpAppVO corpAppVO : list){
            corpAppManageService.saveCorpApp(corpAppVO);
        }

        return corpVO;
    }

    @Override
    public CorpJsapiTicketVO fetchAndSaveCorpJsapiTicket(CorpSuiteVO corpSuiteVO) {
        return null;
    }
}
