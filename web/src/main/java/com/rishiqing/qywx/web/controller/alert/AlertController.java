package com.rishiqing.qywx.web.controller.alert;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.qywx.service.biz.alert.AlertService;
import com.rishiqing.qywx.service.biz.alert.model.TodoAlertVO;
import com.rishiqing.qywx.service.biz.alert.model.converter.TodoAlertConverter;
import com.rishiqing.qywx.web.constant.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-04-10 15:15
 */
@Controller
@RequestMapping("/alert")
public class AlertController {
    private static final Logger logger = LoggerFactory.getLogger("WEB_ALERT_LOGGER");
    @Autowired
    private AlertService alertService;

    @RequestMapping(value = "/set", method = {RequestMethod.POST})
    @ResponseBody
    public Map setAlert(
            @RequestParam("corpid") String corpId,
            @RequestParam(value = "appid", required = false) Long appId,
            @RequestBody JSONObject json
    ){
        logger.debug("/alert/set: corpid: {}, appid: {}, json: {}", corpId, appId, json);
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            TodoAlertVO todoAlertVO = TodoAlertConverter.Json2TodoAlertVO(json);
            alertService.setAlert(corpId, todoAlertVO);
            result.put("errcode", ResultCode.NO_ERROR);
            result.put("errmsg", ResultCode.NO_ERROR_MSG);
        } catch (Exception e) {
            logger.error("/corp/staff error: ", e);
            result.put("errcode", ResultCode.SYS_ERROR);
            result.put("errmsg", ResultCode.SYS_ERROR_MSG);
        }
        return result;
    }

    @RequestMapping(value = "/remove", method = {RequestMethod.POST})
    @ResponseBody
    public Map removeAlert(
            @RequestParam("corpid") String corpId,
            @RequestParam(value = "appid", required = false) Long appId,
            @RequestBody JSONObject json
    ){
        logger.debug("/alert/remove: corpid: {}, appid: {}, json: {}", corpId, appId, json);
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            TodoAlertVO todoAlertVO = TodoAlertConverter.Json2TodoAlertVO(json);
            alertService.removeAlert(corpId, todoAlertVO);
            result.put("errcode", ResultCode.NO_ERROR);
            result.put("errmsg", ResultCode.NO_ERROR_MSG);
        } catch (Exception e) {
            logger.error("/corp/staff error: ", e);
            result.put("errcode", ResultCode.SYS_ERROR);
            result.put("errmsg", ResultCode.SYS_ERROR_MSG);
        }
        return result;
    }
}
