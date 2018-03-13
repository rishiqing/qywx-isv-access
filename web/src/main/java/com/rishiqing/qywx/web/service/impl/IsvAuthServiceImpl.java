package com.rishiqing.qywx.web.service.impl;

import com.rishiqing.qywx.service.common.isv.GlobalSuite;
import com.rishiqing.qywx.service.common.isv.SuitePreAuthCodeManageService;
import com.rishiqing.qywx.service.common.isv.SuiteTokenManageService;
import com.rishiqing.qywx.service.model.isv.SuitePreAuthCodeVO;
import com.rishiqing.qywx.service.model.isv.SuiteTokenVO;
import com.rishiqing.qywx.service.util.http.HttpUtilAuth;
import com.rishiqing.qywx.service.util.http.converter.Json2BeanConverter;
import com.rishiqing.qywx.web.service.IsvAuthService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-03-12 18:42
 */
public class IsvAuthServiceImpl implements IsvAuthService {
    @Autowired
    private HttpUtilAuth httpUtilAuth;
    @Autowired
    private SuiteTokenManageService suiteTokenManageService;
    @Autowired
    private SuitePreAuthCodeManageService suitePreAuthCodeManageService;
    @Autowired
    private GlobalSuite suite;
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
}
