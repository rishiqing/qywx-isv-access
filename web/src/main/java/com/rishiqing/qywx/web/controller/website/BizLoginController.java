package com.rishiqing.qywx.web.controller.website;

import com.rishiqing.common.exception.HttpException;
import com.rishiqing.qywx.service.constant.RequestUrl;
import com.rishiqing.qywx.service.model.website.WebsiteLoginInfoVO;
import com.rishiqing.qywx.web.service.RsqLoginService;
import com.rishiqing.qywx.web.service.website.QywxRegisterService;
import com.rishiqing.qywx.web.service.website.WebsiteOauthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-06-12 21:03
 * 企业微信后台日事清微应用的“业务设置”中点击“前往服务商后台”跳转的url
 */
@Controller
@RequestMapping("/qywxBizLogin")
public class BizLoginController {
    private static Logger logger = LoggerFactory.getLogger("WEB_OAUTH_LOGGER");

    @Autowired
    private WebsiteOauthService websiteOauthService;
    @Autowired
    private RsqLoginService rsqLoginService;
    @Autowired
    private Map isvGlobal;

    @RequestMapping(value = "/to", method = {RequestMethod.GET})
    public String toBizLogin(
            @RequestParam("auth_code") String authCode
    ){
        logger.info("----qywxBizLogin to----");
        try {
            WebsiteLoginInfoVO loginInfoVO = websiteOauthService.getWebsiteLoginInfo(authCode, null);
            String loginToken = rsqLoginService.generateLoginToken(loginInfoVO.getCorpStaffVO());
            logger.info("----qywx login string encoded---- {}", loginToken);
            String redirectUrl = makeRedirectUrl(loginToken);
            return "redirect:" + redirectUrl;
        } catch (HttpException e) {
            logger.error("/qywxBizLogin/to http exception: ", e);
            return "redirect:/error.html";
        } catch (Exception e) {
            logger.error("/qywxBizLogin/to internal exception: ", e);
            return "redirect:/error.html";
        }
    }

    private String makeRedirectUrl(String token) throws UnsupportedEncodingException {
        return isvGlobal.get("rsqUrlAuthRedirect") +
                "?token=" +
                URLEncoder.encode(token, "UTF-8") +
                "&redirect=" +
                URLEncoder.encode((String)isvGlobal.get("rsqUrlBizCorpBack"), "UTF-8");
    }
}
