package com.rishiqing.qywx.service.event.service.impl;

import com.google.common.eventbus.AsyncEventBus;
import com.rishiqing.qywx.service.common.isv.GlobalSuite;
import com.rishiqing.qywx.service.constant.CallbackChangeType;
import com.rishiqing.qywx.service.constant.CallbackInfoType;
import com.rishiqing.qywx.service.event.message.CorpSuiteMessage;
import com.rishiqing.qywx.service.event.message.mq.DemoMessage;
import com.rishiqing.qywx.service.event.service.AsyncService;
import com.rishiqing.qywx.service.model.corp.CorpVO;
import com.rishiqing.qywx.service.model.isv.SuiteVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;
import java.util.Map;

/**
 * 异步执行的通用方法，目前异步执行有两种：
 * 1  使用AsyncEventBus
 * 2  使用activemq
 * @author Wallace Mao
 * Date: 2018-02-10 11:08
 */
public class AsyncServiceImpl implements AsyncService {
    @Autowired
    private GlobalSuite suite;

    //  eventBus相关注入
    @Autowired
    private AsyncEventBus asyncFetchDeptAndStaffEventBus;

    //  mq相关注入
//    @Autowired
//    private JmsTemplate jmsTemplate;
//    @Autowired
//    @Qualifier("demoQueue")
//    private Queue demoQueue;
//    @Autowired
//    @Qualifier("pushCorpAllQueue")
//    private Queue pushCorpAllQueue;
//    @Autowired
//    @Qualifier("pushCorpCallbackQueue")
//    private Queue pushCorpCallbackQueue;

    @Override
    public void sendToFetchCorpAll(String corpId, String permanentCode) {
        CorpSuiteMessage msg = new CorpSuiteMessage();
        msg.setCorpId(corpId);
        msg.setPermanentCode(permanentCode);
        asyncFetchDeptAndStaffEventBus.post(msg);
    }

    @Override
    public void sendToDemo(DemoMessage message){
//        jmsTemplate.send(demoQueue, message);
    }

    @Override
    public void sendToPushCorpAuthCallback(final CorpVO corpVO, final CallbackInfoType type, final Map callbackMap){
        final String corpId = corpVO.getCorpId();
        final String typeKey = type.getKey();
//        jmsTemplate.send(pushCorpAllQueue, new MessageCreator() {
//            @Override
//            public Message createMessage(Session session) throws JMSException {
//                MapMessage mapMessage = session.createMapMessage();
//                mapMessage.setString("corpId", corpId);
//                mapMessage.setString("infoType", typeKey);
//                mapMessage.setObject("content", callbackMap);
//                return mapMessage;
//            }
//        });
    }

    @Override
    public void sendToPushCorpCallback(final CorpVO corpVO, final CallbackChangeType type, final Map callbackMap){
        final String corpId = corpVO.getCorpId();
        final String typeKey = type.getKey();
//        jmsTemplate.send(pushCorpCallbackQueue, new MessageCreator() {
//            @Override
//            public Message createMessage(Session session) throws JMSException {
//                MapMessage mapMessage = session.createMapMessage();
//                mapMessage.setString("corpId", corpId);
//                mapMessage.setString("changeType", typeKey);
//                mapMessage.setObject("content", callbackMap);
//                return mapMessage;
//            }
//        });
    }
}
