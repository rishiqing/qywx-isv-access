package com.rishiqing.qywx.service.biz.corp.impl;

import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.qywx.dao.mapper.corp.CorpDao;
import com.rishiqing.qywx.service.biz.corp.CorpAppManageService;
import com.rishiqing.qywx.service.biz.corp.CorpManageService;
import com.rishiqing.qywx.dao.mapper.isv.AppDao;
import com.rishiqing.qywx.dao.model.corp.CorpDO;
import com.rishiqing.qywx.service.biz.corp.CorpSuiteManageService;
import com.rishiqing.qywx.service.biz.corp.CorpTokenManageService;
import com.rishiqing.qywx.service.biz.isv.SuiteTokenManageService;
import com.rishiqing.qywx.service.exception.HttpException;
import com.rishiqing.qywx.service.exception.SuiteAccessTokenExpiredException;
import com.rishiqing.qywx.service.model.corp.CorpAppVO;
import com.rishiqing.qywx.service.model.corp.CorpSuiteVO;
import com.rishiqing.qywx.service.model.corp.CorpTokenVO;
import com.rishiqing.qywx.service.model.corp.CorpVO;
import com.rishiqing.qywx.service.model.corp.helper.CorpConverter;
import com.rishiqing.qywx.service.model.isv.SuiteTokenVO;
import com.rishiqing.qywx.service.util.http.HttpUtil;
import com.rishiqing.qywx.service.util.http.JsonConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CorpManageServiceImpl implements CorpManageService {
    private static final Logger logger = LoggerFactory.getLogger("CORP_MANAGE_LOGGER");
    @Autowired
    private CorpSuiteManageService corpSuiteManageService;
    @Autowired
    private CorpAppManageService corpAppManageService;
    @Autowired
    private CorpTokenManageService corpTokenManageService;
    @Autowired
    private CorpDao corpDao;
    @Override
    public CorpVO activeCorp(SuiteTokenVO suiteToken, String authCode) throws UnirestException, HttpException, SuiteAccessTokenExpiredException {
        JSONObject json = HttpUtil.getPermanentCode(
                suiteToken.getSuiteKey(),
                suiteToken.getSuiteToken(),
                authCode);
        System.out.println("activeCorp----" + json);
        String suiteKey = suiteToken.getSuiteKey();
        //1. 保存corp信息
        CorpVO corpVO = JsonConverter.generateCorp(json);
        String corpId = corpVO.getCorpId();
        this.saveOrUpdateCorp(corpVO);

        //2. 保存corpSuite信息
        CorpSuiteVO corpSuiteVO = JsonConverter.generateCorpSuite(suiteKey, corpId, json);
        corpSuiteManageService.saveCorpSuite(corpSuiteVO);

        //3. 保存corpToken信息
        CorpTokenVO corpTokenVO = JsonConverter.generateCorpToken(suiteKey, corpId, json);
        corpTokenManageService.saveCorpToken(corpTokenVO);

        //4. 保存corpApp信息
        List<CorpAppVO> list = JsonConverter.generateCorpAppList(suiteKey, corpId, json);
        if(list.size() > 5){
            //  报出警告，如果list数量过大，需要修改成批量插入
            logger.warn("too many CorpAppVO to be saved simutaniously, may cause db performance problem, number is {}", list.size());
        }
        for(CorpAppVO corpAppVO : list){
            corpAppManageService.saveCorpApp(corpAppVO);
        }

        //5. 通知activemq，获取用户信息
        //TODO to activemq
        return corpVO;
    }

    @Override
    public CorpVO fetchAndSaveCorpInfo(String corpId) {
        return null;
    }

    @Override
    public void saveOrUpdateCorp(CorpVO corpVO) {
        corpDao.saveOrUpdateCorp(
                CorpConverter.corpVO2CorpDO(corpVO)
        );
    }

    @Override
    public void markRemoveCorp(String corpId, Boolean authCanceled) {
        corpDao.updateCorpSetAuthCanceled(corpId, authCanceled);
    }
}
