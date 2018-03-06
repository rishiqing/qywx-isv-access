package com.rishiqing.qywx.service.event.service;

import com.rishiqing.qywx.service.constant.CallbackChangeType;
import com.rishiqing.qywx.service.constant.CallbackInfoType;
import com.rishiqing.qywx.service.event.message.mq.DemoMessage;
import com.rishiqing.qywx.service.model.corp.CorpVO;

import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-02-10 11:08
 */
public interface AsyncService {
    void sendToDemo(DemoMessage message);

    void sendToFetchCorpAll(String corpId, String permanentCode);

    void sendToPushCorpAuthCallback(CorpVO corpVO, CallbackInfoType type, Map callbackMap);

    void sendToPushCorpCallback(CorpVO corpVO, CallbackChangeType type, Map callbackMap);
}
