package com.rishiqing.qywx.service.demo;

import com.google.common.eventbus.AsyncEventBus;
import com.rishiqing.qywx.dao.mapper.isv.SuiteDao;
import com.rishiqing.qywx.dao.model.isv.SuiteDO;
import com.rishiqing.qywx.service.event.message.CorpSuiteMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:service-test-spring-context.xml")
public class DemoEventTest {
    @Autowired
    private AsyncEventBus demoEventBus;
    @Test
    public void test_demo(){
        String corpId = "wxec002534a59ea2e7";
        String suiteKey = "tj146dbe5cecf74725";
        CorpSuiteMessage message = new CorpSuiteMessage();
        message.setSuiteKey(suiteKey);
        message.setCorpId(corpId);
        System.out.println("----eventBus Id: " + Thread.currentThread().getId());
        demoEventBus.post(message);
    }
}
