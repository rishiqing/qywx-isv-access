package com.rishiqing.qywx.service.event.service.impl;

import com.rishiqing.qywx.service.common.corp.CorpManageService;
import com.rishiqing.qywx.service.event.service.AsyncService;
import com.rishiqing.qywx.service.model.corp.CorpVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:service-test-spring-context.xml")
public class AsyncServiceImplTest {
    @Autowired
    private AsyncService asyncService;
    @Autowired
    private CorpManageService corpManageService;

    @Test
    public void test_sendToPushCorpAll(){
        CorpVO corpVO = corpManageService.getCorpByCorpId("wxec002534a59ea2e7");
        asyncService.sendToPushCorpAll(corpVO);
    }

}