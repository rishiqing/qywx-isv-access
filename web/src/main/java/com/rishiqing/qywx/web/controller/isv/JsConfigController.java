package com.rishiqing.qywx.web.controller.isv;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.qywx.service.common.corp.CorpJsapiTicketManageService;
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

public class JsConfigController {
    private static Logger logger = LoggerFactory.getLogger("WEB_JS_CONFIG_LOGGER");

    @Autowired
    private JsConfigService jsConfigService;

    @RequestMapping("/get_js_config")
    @ResponseBody
    public String getJsConfig(
            @RequestParam("url") String url,
            @RequestParam("corpid") String corpId,
            @RequestParam(value = "appid", required = false) String appId
    ){
        Map<String, Object> map = null;
        String result = "";
        try {
            map = jsConfigService.getJsapiSignature(url, corpId);
            map.put("errcode", ResultCode.NO_ERROR);
            map.put("errmsg", ResultCode.NO_ERROR_MSG);
        } catch (JsConfigException e) {
            logger.error("get_js_config error: ", e);
            map = new HashMap<>();
            map.put("errcode", ResultCode.JS_CONFIG_ERROR);
            map.put("errmsg", ResultCode.JS_CONFIG_ERROR_MSG);
        } catch (Exception e){
            logger.error("internal error: ", e);
            map = new HashMap<>();
            map.put("errcode", ResultCode.SYS_ERROR);
            map.put("errmsg", ResultCode.SYS_ERROR_MSG);
        }
        return JSONObject.toJSONString(map);
    }
}
