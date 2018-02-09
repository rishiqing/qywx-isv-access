package com.rishiqing.qywx.service.event.message.mq;

import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @author Wallace Mao
 * Date: 2018-02-09 19:52
 */
public class DemoMessage implements MessageCreator {
    private String corpId;
    private String msg;

    public DemoMessage(String corpId, String msg) {
        this.corpId = corpId;
        this.msg = msg;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    @Override
    public Message createMessage(Session session) throws JMSException {
        MapMessage message = session.createMapMessage();
        message.setString("corpId", this.corpId);
        message.setString("msg", this.msg);
        return message;
    }
}
