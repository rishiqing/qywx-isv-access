package com.rishiqing.qywx.service.biz.isv.impl;

import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.qywx.dao.mapper.isv.SuiteTokenDao;
import com.rishiqing.qywx.dao.model.isv.SuiteDO;
import com.rishiqing.qywx.service.biz.isv.SuiteManageService;
import com.rishiqing.qywx.service.biz.isv.SuiteTicketManageService;
import com.rishiqing.qywx.service.biz.isv.SuiteTokenManageService;
import com.rishiqing.qywx.service.exception.HttpException;
import com.rishiqing.qywx.service.model.isv.SuiteTicketVO;
import com.rishiqing.qywx.service.model.isv.SuiteTokenVO;
import com.rishiqing.qywx.service.model.isv.SuiteVO;
import com.rishiqing.qywx.service.model.isv.helper.SuiteTokenConverter;
import com.rishiqing.qywx.service.util.http.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class SuiteTokenManageServiceImpl implements SuiteTokenManageService {
    @Autowired
    private SuiteTokenDao suiteTokenDao;
    @Autowired
    private SuiteManageService suiteManageService;
    @Autowired
    private SuiteTicketManageService suiteTicketManageService;

    public SuiteTokenVO getSuiteToken(String suiteKey) {
        return SuiteTokenConverter.suiteTokenDO2SuiteTokenVO(
                suiteTokenDao.getSuiteTokenBySuiteKey(suiteKey)
        );
    }

    public void fetchAndSaveSuiteToken(String suiteKey) throws HttpException, UnirestException {
        SuiteVO suite = suiteManageService.getSuiteInfoByKey(suiteKey);
        SuiteTicketVO ticket = suiteTicketManageService.getSuiteTicket(suiteKey);
        JSONObject json = HttpUtil.getSuiteToken(suiteKey, suite.getSuiteSecret(), ticket.getTicket());
        String token = json.getString("suite_access_token");
        long expired = json.getLong("expires_in");

        SuiteTokenVO suiteTokenVO = new SuiteTokenVO();
        suiteTokenVO.setSuiteKey(suiteKey);
        suiteTokenVO.setSuiteToken(token);
        suiteTokenVO.setExpiresIn(expired);
        suiteTokenDao.saveOrUpdateSuiteToken(
                SuiteTokenConverter.suiteTokenVO2SuiteTokenDO(suiteTokenVO)
        );
    }
}
