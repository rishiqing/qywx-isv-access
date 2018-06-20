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
import com.rishiqing.qywx.service.util.http.HttpUtilCorp;
import com.rishiqing.qywx.service.util.http.converter.Json2BeanConverter;
import com.rishiqing.qywx.web.service.OauthService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import java.util.Map;

public class OauthServiceImpl implements OauthService {
    @Autowired
    private HttpUtilAuth httpUtilAuth;
    @Autowired
    private HttpUtilCorp httpUtilCorp;
    @Autowired
    private SuiteTokenManageService suiteTokenManageService;
    @Autowired
    private CorpTokenManageService corpTokenManageService;
    @Autowired
    private Map isvGlobal;

    /**
     * 根据isv的suite_access_token和code获取登录用户的信息
     * @param corpId
     * @param code
     * @return
     */
    @Override
    public LoginUserVO getLoginUserByCode(String corpId, String code) {
        String suiteKey = (String)isvGlobal.get("suiteKey");

        SuiteTokenVO suiteTokenVO = suiteTokenManageService.getSuiteToken(suiteKey);
        JSONObject json = httpUtilAuth.getLoginUser(suiteTokenVO, code);
        LoginUserVO loginUserVO = Json2BeanConverter.generateLoginUser(corpId, json);
        return loginUserVO;
    }

    /**
     * 根据企业的access_token获取登录用户的信息
     * @param corpId
     * @param code
     * @return
     */
    @Override
    public LoginUserVO getCorpLoginUserByCode(String corpId, String code) {
        String suiteKey = (String)isvGlobal.get("suiteKey");

        CorpTokenVO corpTokenVO = corpTokenManageService.getCorpToken(suiteKey, corpId);
        JSONObject json = httpUtilCorp.getCorpLoginUserInfo(corpTokenVO, code);
        LoginUserVO loginUserVO = Json2BeanConverter.generateLoginUser(corpId, json);
        return loginUserVO;
    }

    @Override
    public Cookie generateClientUserCookie(String agentId, String corpId, LoginUserVO loginUserVO){
        String cookieNameTemplate = (String)isvGlobal.get("appCookieName");
        String cookieName = cookieNameTemplate
                .replaceAll("\\$AGENTID\\$", agentId)
                .replaceAll("\\$CORPID\\$", corpId);
        Cookie cookie = new Cookie(cookieName,loginUserVO.getUserId());
        cookie.setDomain("rishiqing.com");
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 365);
        return cookie;
    }
}
