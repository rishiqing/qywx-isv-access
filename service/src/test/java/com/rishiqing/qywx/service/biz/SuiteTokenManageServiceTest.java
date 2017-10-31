package com.rishiqing.qywx.service.biz;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.qywx.service.biz.isv.SuiteTokenManageService;
import com.rishiqing.qywx.service.exception.HttpException;
import com.rishiqing.qywx.service.model.isv.SuiteTokenVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
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
        String key = (String)isvGlobal.get("suiteKey");
        try {
            suiteTokenManageService.fetchAndSaveSuiteToken(key);
            SuiteTokenVO suiteTokenVO = suiteTokenManageService.getSuiteToken(key);
            Date updateTime = suiteTokenVO.getTokenUpdateTime();
            Date now = new Date();
            assertTrue(now.getTime() - updateTime.getTime() < 2000);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }
}
