package com.rishiqing.qywx.web.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.common.exception.ActiveCorpException;
import com.rishiqing.qywx.service.biz.corp.CorpService;
import com.rishiqing.qywx.service.common.isv.GlobalSuite;
import com.rishiqing.qywx.service.common.isv.SuitePreAuthCodeManageService;
import com.rishiqing.qywx.service.common.isv.SuiteTokenManageService;
import com.rishiqing.qywx.service.model.corp.CorpSuiteVO;
import com.rishiqing.qywx.service.model.isv.SuitePreAuthCodeVO;
import com.rishiqing.qywx.service.model.isv.SuiteTokenVO;
import com.rishiqing.qywx.service.util.http.HttpUtilAuth;
import com.rishiqing.qywx.service.util.http.converter.Json2BeanConverter;
import com.rishiqing.qywx.web.service.IsvAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-03-12 18:42
 */
public class IsvAuthServiceImpl implements IsvAuthService {
    private static final Logger activeLogger = LoggerFactory.getLogger("SERVICE_ACTIVE_CORP_LOGGER");

    @Autowired
    private HttpUtilAuth httpUtilAuth;
    @Autowired
    private SuiteTokenManageService suiteTokenManageService;
    @Autowired
    private SuitePreAuthCodeManageService suitePreAuthCodeManageService;
    @Autowired
    private GlobalSuite suite;
    @Autowired
    private CorpService corpService;
    @Override
    public SuitePreAuthCodeVO prepareAuth() {
        SuiteTokenVO suiteTokenVO = suiteTokenManageService.getSuiteToken(suite.getSuiteKey());
        SuitePreAuthCodeVO suitePreAuthCodeVO = Json2BeanConverter.generateSuitePreAuthCode(
                httpUtilAuth.getPreAuthCode(suiteTokenVO)
        );
        suitePreAuthCodeVO.setSuiteKey(suite.getSuiteKey());
        suitePreAuthCodeManageService.saveSuitePreAuthCode(suitePreAuthCodeVO);
        return suitePreAuthCodeVO;
    }

    @Override
    public void afterAuth(String authCode) throws ActiveCorpException {
        assert authCode != null;
        //   activeCorp方法内需要马上返回，耗时操作异步执行
        corpService.activeCorp(authCode);
    }
}
