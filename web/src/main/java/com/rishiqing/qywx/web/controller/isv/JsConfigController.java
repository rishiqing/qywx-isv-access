package com.rishiqing.qywx.web.controller.isv;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller

public class JsConfigController {
    @RequestMapping("/get_js_config")
    @ResponseBody
    public String getJsConfig(
            @RequestParam("url") String url,
            @RequestParam("corpid") String corpId,
            @RequestParam("appid") String appId
    ){
        return "";
    }
}
