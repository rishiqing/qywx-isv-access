package com.rishiqing.qywx.service.event;

import com.google.common.eventbus.EventBus;
import com.rishiqing.qywx.service.event.message.CorpSuiteMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:service-test-spring-context.xml")
public class FetchDeptEventTest {
    @Autowired
    private EventBus asyncFetchDeptEventBus;
    @Test
    public void test_sendFetchDeptEvent(){
        CorpSuiteMessage message = new CorpSuiteMessage();
        message.setSuiteKey("DEMO_SUITE_KEY");
        message.setCorpId("DEMO_CORP_ID");
        asyncFetchDeptEventBus.post(message);
    }
}
