package com.rishiqing.qywx.service.event;

import com.google.common.eventbus.EventBus;
import com.rishiqing.qywx.service.common.corp.CorpTokenManageService;
import com.rishiqing.qywx.service.event.message.CorpSuiteMessage;
import com.rishiqing.qywx.service.model.corp.CorpTokenVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:service-test-spring-context.xml")
public class FetchDeptAndStaffEventTest {
    @Autowired
    private EventBus asyncFetchDeptAndStaffEventBus;
    @Autowired
    private CorpTokenManageService corpTokenManageService;
    @Test
    public void test_sendFetchDeptEvent(){
        String corpId = "wxec002534a59ea2e7";
        String suiteKey = "tj146dbe5cecf74725";
        CorpSuiteMessage message = new CorpSuiteMessage();
        message.setSuiteKey(suiteKey);
        message.setCorpId(corpId);
        asyncFetchDeptAndStaffEventBus.post(message);
    }

    @Test
    public void test_getToken(){
        String corpId = "wxec002534a59ea2e7";
        String suiteKey = "tj146dbe5cecf74725";
        CorpTokenVO corpTokenVO = corpTokenManageService.getCorpToken(suiteKey, corpId);
        System.out.println(corpTokenVO);
    }
}
