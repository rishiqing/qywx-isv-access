package com.rishiqing.qywx.web.controller.website;

import com.rishiqing.common.exception.HttpException;
import com.rishiqing.qywx.service.constant.RequestUrl;
import com.rishiqing.qywx.service.util.http.HttpUtilIsv;
import com.rishiqing.qywx.web.service.website.QywxRegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Wallace Mao
 * Date: 2018-05-12 21:03
 * 企业微信注册定制化调用的接口
 */
@Controller
@RequestMapping("/qywxRegister")
public class QywxRegisterController {
    private static Logger logger = LoggerFactory.getLogger("WEB_OAUTH_LOGGER");

    @Autowired
    private QywxRegisterService qywxRegisterService;

    @RequestMapping(value = "/show", method = {RequestMethod.GET})
    public String showPage(){
        logger.info("----qywxRegister show----");
        try {
            String code = qywxRegisterService.fetchRegisterCode();
            return "redirect:" + makeRegisterUrl(code);
        } catch (HttpException e) {
            logger.error("/qywxRegister/show http exception: ", e);
            return "redirect:/error.html";
        } catch (Exception e) {
            logger.error("/qywxRegister/show internal exception: ", e);
            return "redirect:/error.html";
        }
    }

    private String makeRegisterUrl(String code){
        return RequestUrl.QYWX_WEB_REGISTER + "?register_code=" + code;
    }
}
