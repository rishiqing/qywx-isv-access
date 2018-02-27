package com.rishiqing.qywx.service.event.listener.mq;

import com.rishiqing.qywx.service.event.message.mq.DemoMessage;
import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.command.ActiveMQMessage;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2018-02-09 19:21
 */
public class DemoMqListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        try {
            MapMessage mapMessage = (MapMessage)message;
            String corpId = mapMessage.getString("corpId");
            System.out.println("== thread start----" + corpId + "----" + new Date());
            Thread.sleep(3000);
            System.out.println("== thread end----" + corpId + "----" + new Date());
            throw new RuntimeException("------test--------");
//            ActiveMQMapMessage mqMessage = (ActiveMQMapMessage)message;
//            System.out.println("---------------" + mqMessage.getString);
//            System.out.println("---------------++++" + demoMessage.getMsg());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
