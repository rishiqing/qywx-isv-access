package com.rishiqing.qywx.web.controller.isv;

import com.rishiqing.common.exception.HttpException;
import com.rishiqing.qywx.service.model.corp.LoginUserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * 日事清网站扫码登录相关的接口
 * @author Wallace Mao
 * Date: 2018-05-10 18:52
 */
@Controller
@RequestMapping("/websiteOauth")
public class WebsiteOauthController {
    private static Logger logger = LoggerFactory.getLogger("WEB_OAUTH_LOGGER");

    @RequestMapping("/afterLogin")
    public String afterOauth(
            @RequestParam("appid") String appId,
            @RequestParam("auth_code") String authCode,
            @RequestParam(value = "state", required = false) String state
    ){
        logger.info("----oauth after----authCode: {}, appId: {}, state: {}", authCode, appId, state);
        try {
            return "redirect:/qywx-login-test.html";
        } catch (HttpException e) {
            logger.error("/oauth/after http exception: ", e);
            return "redirect:/error.html";
        } catch (Exception e) {
            logger.error("/oauth/after internal exception: ", e);
            return "redirect:/error.html";
        }
    }
}
