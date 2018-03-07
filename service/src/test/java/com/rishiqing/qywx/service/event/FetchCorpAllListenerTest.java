package com.rishiqing.qywx.service.event;

import com.rishiqing.qywx.service.callback.FetchCallbackHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Wallace Mao
 * Date: 2018-03-06 18:15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:service-test-spring-context.xml")
public class FetchCorpAllListenerTest {
    @Autowired
    private FetchCallbackHandler logFailFetchCallbackHandler;

    @Test
    public void test_fetchCorpAll(){
        try {
            String corpId = "wxec002534a59ea2e7";
            String permanentCode = "b7RpWT6KvD1k7-W5ernJI0cUE0yyQwMZ5FCaGsWDwME";
            logFailFetchCallbackHandler.handleFetchCorp(corpId, permanentCode);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
