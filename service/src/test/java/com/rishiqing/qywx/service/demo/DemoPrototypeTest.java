package com.rishiqing.qywx.service.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:service-test-spring-context.xml")
public class DemoPrototypeTest {
    private static final Logger logger = LoggerFactory.getLogger("CONSOLE_LOGGER");
    @Test
    public void test_demo(){
//        DemoPrototype prototype = new DemoPrototype();
//        String str = demoPrototype.showHello();
//        System.out.println(str);
        logger.info("hello info");
        logger.error("hello error");
    }
}
