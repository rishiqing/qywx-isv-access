package com.rishiqing.qywx.service.event.service.impl;

import com.rishiqing.qywx.service.common.corp.CorpManageService;
import com.rishiqing.qywx.service.constant.CallbackInfoType;
import com.rishiqing.qywx.service.event.service.QueueService;
import com.rishiqing.qywx.service.model.corp.CorpVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:service-test-spring-context.xml")
public class QueueServiceImplTest {
    @Autowired
    private QueueService queueService;
    @Autowired
    private CorpManageService corpManageService;

    @Test
    public void test_sendToPushCorpAll(){
        CorpVO corpVO = corpManageService.getCorpByCorpId("wxec002534a59ea2e7");
        queueService.sendToPushCorpAuthCallback(corpVO, CallbackInfoType.CREATE_AUTH, null);
    }

}