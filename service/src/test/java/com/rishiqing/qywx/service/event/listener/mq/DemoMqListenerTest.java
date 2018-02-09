package com.rishiqing.qywx.service.event.listener.mq;

import com.rishiqing.qywx.service.event.message.mq.DemoMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.Queue;

/**
 * @author Wallace Mao
 * Date: 2018-02-09 19:47
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:service-test-spring-context.xml")
public class DemoMqListenerTest {
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    @Qualifier("demoQueue")
    private Queue demoQueue;

    @Test
    public void test_sendEvent(){
        System.out.println("=======begin");
        jmsTemplate.send(demoQueue,new DemoMessage("xxxxxx", "hello mq message"));
        System.out.println("=======end");
    }
}
