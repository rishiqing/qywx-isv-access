package com.rishiqing.qywx.web.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:web-test-spring-context.xml")
public class CallbackServiceTest {
    @Autowired
    private CallbackService callbackService;

    @Test
    public void test_verifyUrl(){}

    @Test
    public void test_receiveMessage(){}
}
