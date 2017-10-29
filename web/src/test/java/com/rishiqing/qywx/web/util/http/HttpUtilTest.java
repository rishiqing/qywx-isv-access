package com.rishiqing.qywx.web.util.http;

import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.qywx.service.biz.isv.SuiteManageService;
import com.rishiqing.qywx.service.biz.isv.SuiteTicketManageService;
import com.rishiqing.qywx.service.model.isv.SuiteTicketVO;
import com.rishiqing.qywx.service.model.isv.SuiteVO;
import com.rishiqing.qywx.web.exception.HttpException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:web-test-spring-context.xml")
public class HttpUtilTest {
    private static final long TICKET_EXPIRE = 7200L;
    @Autowired
    private Map isvGlobal;
    @Autowired
    private SuiteManageService suiteManageService;
    @Autowired
    private SuiteTicketManageService suiteTicketManageService;

    @Test
    public void test_getSuiteToken(){
        String key = (String)isvGlobal.get("suiteKey");
        SuiteVO suite = suiteManageService.getSuiteInfoByKey(key);
        SuiteTicketVO ticket = suiteTicketManageService.getSuiteTicket(key);

        try {
            JSONObject json = HttpUtil.getSuiteToken(key, suite.getSuiteSecret(), ticket.getTicket());
            String token = json.getString("suite_access_token");
            long expired = json.getLong("expires_in");
            assertNotNull(token);
            assertEquals(TICKET_EXPIRE, expired);
        } catch (UnirestException e) {
            e.printStackTrace();
        } catch (HttpException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_post(){}
}
