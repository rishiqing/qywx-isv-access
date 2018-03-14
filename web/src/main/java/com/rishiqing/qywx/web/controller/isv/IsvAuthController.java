package com.rishiqing.qywx.web.controller.isv;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.qywx.service.constant.RequestUrl;
import com.rishiqing.qywx.service.model.corp.CorpSuiteVO;
import com.rishiqing.qywx.service.model.isv.SuitePreAuthCodeVO;
import com.rishiqing.qywx.service.model.isv.SuiteTokenVO;
import com.rishiqing.qywx.service.util.http.converter.Json2BeanConverter;
import com.rishiqing.qywx.web.service.IsvAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 从日事清网站发起授权的相关接口，包括两个主要接口：
 * 1  toAuth：从日事清网站跳转到toAuth接口，在toAuth接口中获取预授权，并跳转到企业微信提供的授权页面，由用户确认授权
 * 2  afterAuth：该接口在toAuth的授权页面中传给企业微信，当用户确认授权后，企业微信将跳转到该接口
 * @see [https://work.weixin.qq.com/api/doc#10974]
 * @author Wallace Mao
 * Date: 2018-03-12 18:34
 */
@Controller
@RequestMapping("/isvAuth")
public class IsvAuthController {
    private static Logger logger = LoggerFactory.getLogger("WEB_ISV_AUTH_LOGGER");

    @Autowired
    private Map isvGlobal;
    @Autowired
    private IsvAuthService isvAuthService;

    @RequestMapping("/to")
    public String toAuth(){
        logger.debug("----begin to fetch pre auth code----");
        try {
            SuitePreAuthCodeVO suitePreAuthCodeVO = isvAuthService.prepareAuth();
            String authPage = makeAuthPage(suitePreAuthCodeVO);
            logger.debug("authPath is: " + authPage);
            return "redirect:" + authPage;
        } catch (Exception e) {
            logger.error("/isvAuth/to internal exception: ", e);
            return "redirect:/error.html";
        }
    }

    @RequestMapping("/after")
    public String afterAuth(
            @RequestParam("auth_code") String authCode,
            @RequestParam("expires_in") String expiresIn,
            @RequestParam("state") String state
    ){
        logger.debug("----begin to auth corp from isv----auth_code: {}, expires_in: {}, state: {}", authCode, expiresIn, state);
        try {
            String authPage = "/checkpreload.html";
            isvAuthService.afterAuth(authCode);
            return "redirect:" + authPage;
        } catch (Exception e) {
            logger.error("/isvAuth/after internal exception: ", e);
            return "redirect:/error.html";
        }
    }

    private String makeAuthPage(SuitePreAuthCodeVO suitePreAuthCodeVO) throws UnsupportedEncodingException {
        String redirectUrl = (String)isvGlobal.get("isvAuthRedirectUri");
        String state = "STATE";

        StringBuilder sb = new StringBuilder(
                RequestUrl.QYWX_WEB_INSTALL
        );
        sb.append("?suite_id=");
        sb.append(suitePreAuthCodeVO.getSuiteKey());
        sb.append("&pre_auth_code=");
        sb.append(suitePreAuthCodeVO.getSuitePreAuthCode());
        sb.append("&redirect_uri=");
        sb.append(URLEncoder.encode(redirectUrl, "UTF-8"));
        sb.append("&state=");
        sb.append(state);
        return sb.toString();
    }
}
