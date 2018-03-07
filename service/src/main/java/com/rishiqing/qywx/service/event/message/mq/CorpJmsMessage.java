package com.rishiqing.qywx.service.event.message.mq;

import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @author Wallace Mao
 * Date: 2018-02-10 14:08
 */
public class CorpJmsMessage implements MessageCreator {
    private String corpId;

    public CorpJmsMessage(String corpId) {
        this.corpId = corpId;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    @Override
    public Message createMessage(Session session) throws JMSException {
        MapMessage message = session.createMapMessage();
        message.setString("corpId", this.corpId);
        return message;
    }
}
