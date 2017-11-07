package com.rishiqing.qywx.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.qywx.service.common.corp.CorpTokenManageService;
import com.rishiqing.qywx.service.exception.HttpException;
import com.rishiqing.qywx.service.model.corp.CorpTokenVO;
import com.rishiqing.qywx.service.model.corp.LoginUserVO;
import com.rishiqing.qywx.service.util.http.HttpUtilAuth;
import com.rishiqing.qywx.service.util.http.converter.Json2BeanConverter;
import com.rishiqing.qywx.web.service.OauthService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class OauthServiceImpl implements OauthService {
    @Autowired
    private HttpUtilAuth httpUtilAuth;
    @Autowired
    private CorpTokenManageService corpTokenManageService;
    @Autowired
    private Map isvGlobal;
    @Override
    public LoginUserVO getLoginUserByCode(String corpId, String code) throws HttpException, UnirestException {
        String suiteKey = (String)isvGlobal.get("suiteKey");
        CorpTokenVO corpTokenVO = corpTokenManageService.getCorpToken(suiteKey, corpId);
        JSONObject json = httpUtilAuth.getLoginUser(corpTokenVO, code);
        LoginUserVO loginUserVO = Json2BeanConverter.generateLoginUser(corpId, json);
        return loginUserVO;
    }
}
