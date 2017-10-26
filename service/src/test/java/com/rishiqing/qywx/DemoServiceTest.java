package com.rishiqing.qywx;

import com.rishiqing.qywx.service.biz.isv.DemoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:service-test-spring-context.xml")
public class DemoServiceTest {
    @Autowired
    DemoService demoService;

    @Test
    public void test_printHello(){
        Assert.assertEquals("hello", demoService.printHelloWorld());
    }
}
