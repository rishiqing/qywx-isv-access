package com.rishiqing.qywx.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.qywx.service.common.corp.CorpTokenManageService;
import com.rishiqing.common.exception.HttpException;
import com.rishiqing.qywx.service.common.isv.SuiteTokenManageService;
import com.rishiqing.qywx.service.model.corp.CorpTokenVO;
import com.rishiqing.qywx.service.model.corp.LoginUserVO;
import com.rishiqing.qywx.service.model.isv.SuiteTokenVO;
import com.rishiqing.qywx.service.util.http.HttpUtilAuth;
import com.rishiqing.qywx.service.util.http.converter.Json2BeanConverter;
import com.rishiqing.qywx.web.service.OauthService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class OauthServiceImpl implements OauthService {
    @Autowired
    private HttpUtilAuth httpUtilAuth;
    @Autowired
    private SuiteTokenManageService suiteTokenManageService;
    @Autowired
    private Map isvGlobal;
    @Override
    public LoginUserVO getLoginUserByCode(String corpId, String code) {
        String suiteKey = (String)isvGlobal.get("suiteKey");

        SuiteTokenVO suiteTokenVO = suiteTokenManageService.getSuiteToken(suiteKey);
        JSONObject json = httpUtilAuth.getLoginUser(suiteTokenVO, code);
        LoginUserVO loginUserVO = Json2BeanConverter.generateLoginUser(corpId, json);
        return loginUserVO;
    }
}
