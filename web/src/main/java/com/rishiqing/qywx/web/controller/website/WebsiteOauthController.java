package com.rishiqing.qywx.web.controller.website;

import com.rishiqing.common.exception.HttpException;
import com.rishiqing.qywx.service.common.crypto.CryptoUtil;
import com.rishiqing.qywx.service.common.isv.GlobalSuite;
import com.rishiqing.qywx.service.constant.RequestUrl;
import com.rishiqing.qywx.service.model.corp.CorpStaffVO;
import com.rishiqing.qywx.web.service.RsqLoginService;
import com.rishiqing.qywx.web.service.website.WebsiteOauthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-05-10 18:52
 * 日事清网站扫码登录相关的接口
 * @see [https://work.weixin.qq.com/api/doc#10991/%E4%BB%8E%E7%AC%AC%E4%B8%89%E6%96%B9%E5%8D%95%E7%82%B9%E7%99%BB%E5%BD%95]
 */
@Controller
@RequestMapping("/websiteOauth")
public class WebsiteOauthController {
    private static Logger logger = LoggerFactory.getLogger("WEB_OAUTH_LOGGER");
    @Autowired
    private WebsiteOauthService websiteOauthService;
    @Autowired
    private RsqLoginService rsqLoginService;
    @Autowired
    private Map isvGlobal;
    @Autowired
    private GlobalSuite suite;

    @RequestMapping(value = "/to", method = {RequestMethod.GET})
    public String afterOauth(){
        logger.info("----web websiteOauth----");
        //  这里可以做一些额外的处理
        try {
            return "redirect:" + makeWebsiteOauthUrl();
        } catch (Exception e) {
            logger.error("/websiteOauth/to http exception: ", e);
            return "redirect:/error.html";
        }
    }

    @RequestMapping(value = "/afterLogin", method = {RequestMethod.GET})
    public String afterOauth(
            @RequestParam("auth_code") String authCode,
            @RequestParam("appid") String corpId,  //  这里的appid其实就是isv所在企业的corpId
            @RequestParam("state") String state,
            HttpServletResponse response
    ){
        logger.info("----oauth after----authCode: {}, appId: {}, state: {}", authCode, corpId, state);
        try {
            //  检查state
            Boolean stateValid = websiteOauthService.checkState(state);
            if(!stateValid){
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return null;
            }
            CorpStaffVO corpStaffVO = websiteOauthService.registerLoginUser(authCode, corpId);
            String loginToken = rsqLoginService.generateLoginToken(corpStaffVO);
            logger.info("----qywx login string encoded---- {}", loginToken);
            String redirectUrl = makeRedirectUrl(loginToken);
            return "redirect:" + redirectUrl;
        } catch (HttpException e) {
            logger.error("/websiteOauth/afterLogin http exception: ", e);
            return "redirect:/error.html";
        } catch (Exception e) {
            logger.error("/websiteOauth/afterLogin internal exception: ", e);
            return "redirect:/error.html";
        }
    }

    private String makeWebsiteOauthUrl() throws UnsupportedEncodingException {
        //  ?appid=wxec002534a59ea2e7&redirect_uri=&state=web_login@gyoss9&usertype=member
        //  防止跨域攻击
        return RequestUrl.QYWX_WEB_OAUTH +
                "?appid=" +
                suite.getCorpId() +
                "&redirect_uri=" +
                URLEncoder.encode((String)isvGlobal.get("rsqUrlOauthRedirect"), "UTF-8") +
                "&state=" +
                websiteOauthService.generateState() +
                "&usertype=member";
    }
    private String makeRedirectUrl(String token) throws UnsupportedEncodingException {
        return isvGlobal.get("rsqUrlAuthRedirect") +
                "?token=" +
                URLEncoder.encode(token, "UTF-8");
    }
}
