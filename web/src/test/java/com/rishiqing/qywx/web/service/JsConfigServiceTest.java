package com.rishiqing.qywx.web.service;

import com.rishiqing.qywx.web.exception.JsConfigException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:web-test-spring-context.xml")
public class JsConfigServiceTest {
    @Autowired
    private JsConfigService jsConfigService;
    @Test
    public void test_getSignature(){
        String corpId = "wxec002534a59ea2e7";
        String suiteKey = "tj146dbe5cecf74725";
        try {
            Map map = jsConfigService.getJsapiSignature("http://qywx.rsq.etoutiao.cn/rsqwebapp", corpId);
        } catch (JsConfigException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_refreshJsapiTicket(){
        String corpId = "wxec002534a59ea2e7";
        String suiteKey = "tj146dbe5cecf74725";
        try {
            jsConfigService.refreshJsapiTicket(corpId);
        } catch (JsConfigException e) {
            e.printStackTrace();
        }
    }
}
