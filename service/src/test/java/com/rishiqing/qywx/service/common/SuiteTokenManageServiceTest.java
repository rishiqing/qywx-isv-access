package com.rishiqing.qywx.service.common;

import com.rishiqing.qywx.service.common.isv.SuiteTokenManageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:service-test-spring-context.xml")
public class SuiteTokenManageServiceTest {
    @Autowired
    private Map isvGlobal;
    @Autowired
    private SuiteTokenManageService suiteTokenManageService;
    @Test
    public void test_fetchAndSaveSuiteToken(){
    }
}
