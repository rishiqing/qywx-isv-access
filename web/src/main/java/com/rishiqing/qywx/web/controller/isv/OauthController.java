package com.rishiqing.qywx.web.controller.isv;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.qywx.web.constant.ResultCode;
import com.rishiqing.qywx.web.exception.JsConfigException;
import com.rishiqing.qywx.web.service.JsConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/oauth")
public class OauthController {
    private static Logger logger = LoggerFactory.getLogger("WEB_OAUTH_LOGGER");

    @RequestMapping("/after")
    @ResponseBody
    public String getJsConfig(
            @RequestParam("code") String code,
            @RequestParam(value = "state", required = false) String state
    ){
        logger.info("----oauth after----code: {}, state: {}", code, state);
        return code;
    }
}
