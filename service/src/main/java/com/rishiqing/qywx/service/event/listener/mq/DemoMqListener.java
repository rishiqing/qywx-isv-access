package com.rishiqing.qywx.service.event.listener.mq;

import com.rishiqing.qywx.service.event.message.mq.DemoMessage;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.command.ActiveMQMessage;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * @author Wallace Mao
 * Date: 2018-02-09 19:21
 */
public class DemoMqListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            MapMessage mapMessage = (MapMessage)message;
            System.out.println("--------" + mapMessage.getString("corpId"));
//            ActiveMQMapMessage mqMessage = (ActiveMQMapMessage)message;
//            System.out.println("---------------" + mqMessage.getString);
//            System.out.println("---------------++++" + demoMessage.getMsg());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
