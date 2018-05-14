package com.rishiqing.qywx.web.controller.website;

import com.rishiqing.common.exception.HttpException;
import com.rishiqing.qywx.service.common.crypto.CryptoUtil;
import com.rishiqing.qywx.service.model.corp.CorpStaffVO;
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
    private CryptoUtil cryptoUtil;
    @Autowired
    private Map isvGlobal;

    @RequestMapping(value = "/afterLogin", method = {RequestMethod.GET})
    public String afterOauth(
            @RequestParam("auth_code") String authCode,
            @RequestParam("appid") String corpId,  //  这里的appid其实就是isv所在企业的corpId
            @RequestParam(value = "state", required = false) String state
    ){
        logger.info("----oauth after----authCode: {}, appId: {}, state: {}", authCode, corpId, state);
        try {
            CorpStaffVO corpStaffVO = websiteOauthService.registerLoginUser(authCode, corpId);
            String loginStr = makeLoginString(corpStaffVO);
            logger.info("----qywx login string---- {}", loginStr);
            String loginToken = cryptoUtil.encrypt(loginStr);
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

    private String makeLoginString(CorpStaffVO corpStaffVO){
        return String.valueOf(new Date().getTime()) +
                "--" +
                corpStaffVO.getCorpId() +
                "--" +
                corpStaffVO.getUserId();
    }
    private String makeRedirectUrl(String token) throws UnsupportedEncodingException {
        return isvGlobal.get("rsqUrlAuthRedirect") +
                "?token=" +
                URLEncoder.encode(token, "UTF-8");
    }
}
