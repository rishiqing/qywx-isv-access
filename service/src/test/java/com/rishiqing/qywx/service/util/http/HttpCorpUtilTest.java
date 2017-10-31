package com.rishiqing.qywx.service.util.http;

import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.qywx.dao.mapper.corp.CorpSuiteDao;
import com.rishiqing.qywx.dao.mapper.corp.CorpTokenDao;
import com.rishiqing.qywx.dao.mapper.isv.SuiteTokenDao;
import com.rishiqing.qywx.dao.model.corp.CorpSuiteDO;
import com.rishiqing.qywx.dao.model.corp.CorpTokenDO;
import com.rishiqing.qywx.dao.model.isv.SuiteTokenDO;
import com.rishiqing.qywx.service.biz.isv.SuiteTokenManageService;
import com.rishiqing.qywx.service.exception.AccessTokenExpiredException;
import com.rishiqing.qywx.service.exception.HttpException;
import com.rishiqing.qywx.service.exception.SuiteAccessTokenExpiredException;
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
public class HttpCorpUtilTest {
    private static final Logger logger = LoggerFactory.getLogger("ISV_HTTP_REQUEST_LOGGER");
    @Autowired
    private CorpTokenDao corpTokenDao;
    @Autowired
    private SuiteTokenDao suiteTokenDao;
    @Autowired
    private CorpSuiteDao corpSuiteDao;
    @Autowired
    private SuiteTokenManageService suiteTokenManageService;
    @Test
    public void test_getDepartmentList(){
        String corpId = "wxec002534a59ea2e7";
        String suiteKey = "tj146dbe5cecf74725";
        CorpTokenDO corpTokenDO = corpTokenDao.getCorpTokenBySuiteKeyAndCorpId(suiteKey, corpId);
        try {
            JSONObject json = HttpCorpUtil.getDepartmentList(corpTokenDO.getCorpToken(), corpTokenDO.getCorpId());
            System.out.println(json);
            assertTrue(json.containsKey("department"));
        } catch (UnirestException | HttpException e) {
            e.printStackTrace();
        } catch (AccessTokenExpiredException e) {
            //  corp access token 过期
            logger.info("----access token expired----");
            SuiteTokenDO suiteToken = suiteTokenDao.getSuiteTokenBySuiteKey(suiteKey);
            CorpSuiteDO corpSuiteDO = corpSuiteDao.getCorpSuiteBySuiteKeyAndCorpId(suiteKey, corpId);
            try {
                HttpUtil.getCorpAccessToken(suiteKey, suiteToken.getSuiteToken(), corpId, corpSuiteDO.getPermanentCode());
            } catch (UnirestException | HttpException e1) {
                e1.printStackTrace();
            } catch (SuiteAccessTokenExpiredException e1) {
                //  suite access token 过期
                logger.info("----suite access token expired----");
                try {
                    suiteTokenManageService.fetchAndSaveSuiteToken(suiteKey);
                } catch (HttpException | UnirestException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }
}
