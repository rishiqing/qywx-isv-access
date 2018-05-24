package com.rishiqing.qywx.service.event.listener.mq;

import com.rishiqing.qywx.service.event.message.mq.DemoMessage;
import com.rishiqing.qywx.service.event.service.QueueService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Wallace Mao
 * Date: 2018-02-09 19:47
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:service-test-spring-context.xml")
public class DemoMqListenerTest {
    @Autowired
    private QueueService queueService;

    @Test
    public void test_sendEvent(){
        System.out.println("=======begin");
        int max = 1;
        for(int i = 0; i < max; i++ ){
            queueService.sendToDemo(new DemoMessage("xxxxxx-" + i, "hello mq message"));
        }
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("=======end");
    }
}
