package com.rishiqing.qywx.service.event.listener.mq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author Wallace Mao
 * Date: 2018-02-08 17:34
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:service-test-spring-context.xml")
public class PushCorpHandlerTest {
    @Autowired
    private PushCorpHandler pushCorpHandler;

    @Test
    public void test_handleCreateCorp(){
        /**
         * 清理sql
        update isv_corp ic set ic.rsq_id = NULL where ic.corp_id like 'wxec002534a59ea2e7';
        update isv_corp_dept icd set icd.rsq_id = NULL where icd.corp_id like 'wxec002534a59ea2e7';
        update isv_corp_staff ics set ics.rsq_user_id = NULL, ics.rsq_username = NULL, ics.rsq_password = NULL, ics.rsq_login_token = NULL where icd.corp_id like 'wxec002534a59ea2e7';
        */
         String corpId = "wxec002534a59ea2e7";
         pushCorpHandler.handleCreateCorp(corpId);
    }

    @Test
    public void test_handleCreateDept(){
        String corpId = "wxec002534a59ea2e7";
        Map map = new HashMap();
        map.put("SuiteId", "wxe0de6067085faf0d");
        map.put("AuthCorpId", "wxec002534a59ea2e7");
        map.put("InfoType", "change_contact");
        map.put("TimeStamp", new Date().getTime());
        map.put("ChangeType", "create_party");
        map.put("Id", "222222");
        map.put("Name", "测试部门");
        map.put("ParentId", "");
        map.put("Order", "");
        pushCorpHandler.handleCreateDept(corpId, map);
    }
}