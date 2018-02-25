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
set @corpId =  'wxec002534a59ea2e7';
update isv_corp ic set ic.rsq_id = NULL where ic.corp_id like @corpId;
update isv_corp_dept icd set icd.rsq_id = NULL where icd.corp_id like @corpId;
update isv_corp_staff ics set ics.rsq_user_id = NULL, ics.rsq_username = NULL, ics.rsq_password = NULL, ics.rsq_login_token = NULL where ics.corp_id like @corpId;
        */
        /**
         * 清理beta日事清后台sql
set @teamId = 101013;
set @appName = 'qywxtest';
update team t set t.outer_id = null where t.id = @teamId and t.from_app like @appName;
update department d set d.outer_id = null where d.team_id = @teamId and d.from_app like @appName;
update `user` u set u.outer_id = null where u.team_id = @teamId;
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
        map.put("Id", "444");
        map.put("Name", "测试部门");
        map.put("ParentId", "1");
        map.put("Order", "111");
        pushCorpHandler.handleCreateDept(corpId, map);
    }

    @Test
    public void test_handleUpdateDept(){
        String corpId = "wxec002534a59ea2e7";
        Map map = new HashMap();
        map.put("SuiteId", "wxe0de6067085faf0d");
        map.put("AuthCorpId", "wxec002534a59ea2e7");
        map.put("InfoType", "change_contact");
        map.put("TimeStamp", new Date().getTime());
        map.put("ChangeType", "update_party");
        map.put("Id", "13");
        map.put("Name", "临时测试子部门");
        map.put("ParentId", "12");
        pushCorpHandler.handleUpdateDept(corpId, map);
    }

    @Test
    public void test_handleDeleteDept(){
        String corpId = "wxec002534a59ea2e7";
        Map map = new HashMap();
        map.put("SuiteId", "wxe0de6067085faf0d");
        map.put("AuthCorpId", "wxec002534a59ea2e7");
        map.put("InfoType", "change_contact");
        map.put("TimeStamp", new Date().getTime());
        map.put("ChangeType", "delete_party");
        map.put("Id", "13");
        pushCorpHandler.handleDeleteDept(corpId, map);
    }

    @Test
    public void test_handleCreateUser(){
        String corpId = "wxec002534a59ea2e7";
        Map map = new HashMap();
        map.put("SuiteId", "wxe0de6067085faf0d");
        map.put("AuthCorpId", "wxec002534a59ea2e7");
        map.put("InfoType", "change_contact");
        map.put("TimeStamp", new Date().getTime());
        map.put("ChangeType", "create_user");
        map.put("UserID", "Wallace");
        map.put("Name", "毛文强222");
        map.put("Department", "12");
        map.put("Gender", "1");
        map.put("Avatar", "http://wx.qlogo.cn/mmopen/ajNVdqHZLLA3WJ6DSZUfiakYe37PKnQhBIeOQBO4czqrnZDS79FH5Wm5m4X69TBicnHFlhiafvDwklOpZeXYQQ2icg/0");
        map.put("EnglishName", "Wallace");
        map.put("ExtAttr", "");
        pushCorpHandler.handleCreateUser(corpId, map);
    }

    @Test
    public void test_handleUpdateUser(){
        String corpId = "wxec002534a59ea2e7";
        Map map = new HashMap();
        map.put("SuiteId", "wxe0de6067085faf0d");
        map.put("AuthCorpId", "wxec002534a59ea2e7");
        map.put("InfoType", "change_contact");
        map.put("TimeStamp", new Date().getTime());
        map.put("ChangeType", "update_user");
        map.put("UserID", "Wallace");
        map.put("Name", "毛文强222333");
        map.put("Department", "12");
        map.put("Gender", "1");
        map.put("Avatar", "http://wx.qlogo.cn/mmopen/ajNVdqHZLLA3WJ6DSZUfiakYe37PKnQhBIeOQBO4czqrnZDS79FH5Wm5m4X69TBicnHFlhiafvDwklOpZeXYQQ2icg/0");
        map.put("EnglishName", "Wallace");
        map.put("ExtAttr", "");
        pushCorpHandler.handleUpdateUser(corpId, map);
    }

    @Test
    public void test_handleDeleteUser(){
        String corpId = "wxec002534a59ea2e7";
        Map map = new HashMap();
        map.put("SuiteId", "wxe0de6067085faf0d");
        map.put("AuthCorpId", "wxec002534a59ea2e7");
        map.put("InfoType", "change_contact");
        map.put("TimeStamp", new Date().getTime());
        map.put("ChangeType", "delete_user");
        map.put("UserID", "Wallace");
        pushCorpHandler.handleDeleteUser(corpId, map);
    }
}