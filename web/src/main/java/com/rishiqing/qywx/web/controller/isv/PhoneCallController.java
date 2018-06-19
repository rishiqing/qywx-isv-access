package com.rishiqing.qywx.web.controller.isv;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rishiqing.qywx.service.common.isv.GlobalSuite;
import com.rishiqing.qywx.service.common.isv.IsvManageService;
import com.rishiqing.qywx.service.model.isv.IsvVO;
import com.rishiqing.qywx.service.model.isv.SuitePreAuthCodeVO;
import com.rishiqing.qywx.service.util.http.HttpUtilIsv;
import com.rishiqing.qywx.service.util.http.converter.Json2BeanConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Wallace Mao
 * Date: 2018-06-19 20:07
 */
@Controller
@RequestMapping("/phone")
public class PhoneCallController {
    private static Logger logger = LoggerFactory.getLogger("WEB_ISV_AUTH_LOGGER");

    @Autowired
    private HttpUtilIsv httpUtilIsv;
    @Autowired
    private GlobalSuite suite;
    @Autowired
    private IsvManageService isvManageService;

    @RequestMapping(value = "/call", method = {RequestMethod.POST})
    @ResponseBody
    public String call(
            @RequestBody String body
    ){
        logger.debug("----/phone/call----{}", body);
        try {
            JSONObject json = JSON.parseObject(body);
            //  读取套件基本信息
            String corpId = suite.getCorpId();
            IsvVO isv = isvManageService.getIsv(corpId);
            httpUtilIsv.postCall(isv, Json2BeanConverter.generatePhoneCallInfo(json));
            return "success";
        } catch (Exception e) {
            logger.error("/phone/call internal exception: ", e);
            return "fail";
        }
    }
}
