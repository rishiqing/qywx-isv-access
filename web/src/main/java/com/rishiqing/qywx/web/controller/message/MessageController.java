package com.rishiqing.qywx.web.controller.message;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.qywx.service.common.message.SendMessageService;
import com.rishiqing.qywx.service.util.http.converter.Json2MapConverter;
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
 * Date: 2018-04-10 9:49
 */
@Controller
@RequestMapping("/msg")
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger("WEB_MESSAGE_LOGGER");
    @Autowired
    private SendMessageService sendMessageService;

    /**
     *
     * @param corpId
     * @param appId
     * @param json: json中至少需要包含touser/toparty/totag其中之一、textcard
     * @return
     */
    @RequestMapping(value = "/sendNotification", method = {RequestMethod.POST})
    @ResponseBody
    public Map sendMessage(
            @RequestParam("corpid") String corpId,
            @RequestParam(value = "appid", required = false) Long appId,
            @RequestBody JSONObject json
    ){
        logger.debug("/msg/sendNotification: corpid: {}, appid: {}, json: {}", corpId, appId, json);
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            //  默认使用textcard发送消息
            String msgType = "textcard";
            Map map = Json2MapConverter.generateSendMessageMap(msgType, json);
            sendMessageService.sendCorpMessage(corpId, map);
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
