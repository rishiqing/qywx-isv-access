package com.rishiqing.qywx.web.controller.isv;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.common.exception.HttpException;
import com.rishiqing.qywx.service.model.corp.LoginUserVO;
import com.rishiqing.qywx.web.service.OauthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/oauth")
public class OauthController {
    private static Logger logger = LoggerFactory.getLogger("WEB_OAUTH_LOGGER");

    @Autowired
    private OauthService oauthService;
    @Autowired
    private Map isvGlobal;

    /**
     * 获取当前登录用户，设置cookie，然后跳转到主页
     * @param corpId
     * @param agentId
     * @param code
     * @param state
     * @return
     */
    @RequestMapping("/after")
    public String afterOauth(
            HttpServletResponse response,
            @RequestParam("corpId") String corpId,
            @RequestParam("agentId") String agentId,
            @RequestParam("code") String code,
            @RequestParam(value = "state", required = false) String state
    ){
        logger.info("----oauth after----corpId: {}, agentId: {}, code: {}, state: {}", corpId, agentId, code, state);
        try {
            LoginUserVO loginUserVO = oauthService.getLoginUserByCode(corpId, code);
            String cookieNameTemplate = (String)isvGlobal.get("appCookieName");
            String mainPageTemplate = (String)isvGlobal.get("appMobileIndexPage");
            String cookieName = cookieNameTemplate
                    .replaceAll("\\$AGENTID\\$", agentId)
                    .replaceAll("\\$CORPID\\$", corpId);
            String mainPage = mainPageTemplate
                    .replaceAll("\\$AGENTID\\$", agentId)
                    .replaceAll("\\$CORPID\\$", corpId);
            Cookie cookie = new Cookie(cookieName,loginUserVO.getUserId());
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24 * 365);
            response.addCookie(cookie);
            return "redirect:" + mainPage;
        } catch (HttpException e) {
            logger.error("/oauth/after http exception: ", e);
            return "redirect:/error.html";
        } catch (Exception e) {
            logger.error("/oauth/after internal exception: ", e);
            return "redirect:/error.html";
        }
    }
}
