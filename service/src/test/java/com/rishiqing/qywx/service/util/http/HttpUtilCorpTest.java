package com.rishiqing.qywx.service.util.http;

import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.qywx.service.common.corp.CorpSuiteManageService;
import com.rishiqing.qywx.service.common.corp.CorpTokenManageService;
import com.rishiqing.qywx.service.common.isv.SuiteTokenManageService;
import com.rishiqing.qywx.service.exception.AccessTokenExpiredException;
import com.rishiqing.qywx.service.exception.HttpException;
import com.rishiqing.qywx.service.model.corp.CorpSuiteVO;
import com.rishiqing.qywx.service.model.corp.CorpTokenVO;
import com.rishiqing.qywx.service.model.isv.SuiteTokenVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:service-test-spring-context.xml")
public class HttpUtilCorpTest {
    private static final Logger logger = LoggerFactory.getLogger("SERVICE_HTTP_REQUEST_LOGGER");
    @Autowired
    private CorpTokenManageService corpTokenManageService;
    @Autowired
    private CorpSuiteManageService corpSuiteManageService;
    @Autowired
    private SuiteTokenManageService suiteTokenManageService;
    @Autowired
    private HttpUtil httpUtil;
    @Autowired
    private HttpUtilCorp httpUtilCorp;
    @Test
    public void test_getDepartmentList(){
        String corpId = "wxec002534a59ea2e7";
        String suiteKey = "tj146dbe5cecf74725";
        CorpTokenVO corpTokenVO = corpTokenManageService.getCorpToken(suiteKey, corpId);
        try {
            JSONObject json = httpUtilCorp.getDepartmentList(corpTokenVO, null);
            System.out.println(json);
            assertTrue(json.containsKey("department"));
        } catch (UnirestException | HttpException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_getDepartmentStaffList(){
        String corpId = "wxec002534a59ea2e7";
        String suiteKey = "tj146dbe5cecf74725";
        CorpTokenVO corpTokenVO = corpTokenManageService.getCorpToken(suiteKey, corpId);
        try {
            JSONObject json = httpUtilCorp.getDepartmentStaffList(corpTokenVO, null);
            System.out.println(json);
            assertTrue(json.containsKey("userlist"));
        } catch (UnirestException | HttpException e) {
            e.printStackTrace();
        }
    }
}
