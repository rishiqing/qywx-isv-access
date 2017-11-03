package com.rishiqing.qywx.service.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:service-test-spring-context.xml")
public class DemoPrototypeTest {
    @Autowired
    private DemoPrototype demoPrototype;
    @Test
    public void test_demo(){
//        DemoPrototype prototype = new DemoPrototype();
        String str = demoPrototype.showHello();
        System.out.println(str);
    }
}
