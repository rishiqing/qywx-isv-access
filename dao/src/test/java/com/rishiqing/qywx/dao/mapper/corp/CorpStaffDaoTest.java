package com.rishiqing.qywx.dao.mapper.corp;

import com.rishiqing.qywx.dao.model.corp.CorpDO;
import com.rishiqing.qywx.dao.model.corp.CorpStaffDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:dao-test-spring-context.xml")
public class CorpStaffDaoTest {
    private String CORP_ID = "demo_corp_id";
    private String USER_ID = "http://demoavatar";
    private String NAME = "Wallace";
    private String NAME_2 = "Amelie";
    private String DEPARTMENT = "[1,2]";
    private String ORDER_IN_DEPTS = "[11,22]";
    private String IS_LEADER_IN_DEPTS = "[1,0]";
    private String POSITION = "CEO";
    private String MOBILE = "13810001000";
    private String GENDER = "1";
    private String EMAIL = "aaa@qq.com";
    private String AVATAR = "http://squareavatarurl";
    private String TEL = "010-51234123";
    private String ENGLISH_NAME = "Wallace";
    private Long STATUS = 1L;
    private String EXTATTR = "{aaa:bbb}";
    private String RSQ_USER_ID = "123";
    private String RSQ_USERNAME = "tgbyhn@rishiqing.com";
    private String RSQ_PASSWORD = "edcVFR";
    private String RSQ_LOGIN_TOKEN = "xXxxxxxxxxx";
    @Autowired
    private CorpStaffDao corpStaffDao;

    @Test
    public void test_saveOrUpdateCorp(){
        CorpStaffDO corpStaffDO = new CorpStaffDO();
        corpStaffDO.setCorpId(CORP_ID);
        corpStaffDO.setUserId(USER_ID);
        corpStaffDO.setName(NAME);
        corpStaffDO.setDepartment(DEPARTMENT);
        corpStaffDO.setOrderInDepts(ORDER_IN_DEPTS);
        corpStaffDO.setIsLeaderInDepts(IS_LEADER_IN_DEPTS);
        corpStaffDO.setPosition(POSITION);
        corpStaffDO.setMobile(MOBILE);
        corpStaffDO.setGender(GENDER);
        corpStaffDO.setEmail(EMAIL);
        corpStaffDO.setAvatar(AVATAR);
        corpStaffDO.setTel(TEL);
        corpStaffDO.setEnglishName(ENGLISH_NAME);
        corpStaffDO.setStatus(STATUS);
        corpStaffDO.setExtattr(EXTATTR);
        corpStaffDao.saveOrUpdateCorpStaff(corpStaffDO);

        CorpStaffDO corpStaffAfterSave = corpStaffDao.getCorpStaffByCorpIdAndUserId(CORP_ID, USER_ID);
        assertEquals(CORP_ID, corpStaffAfterSave.getCorpId());
        assertEquals(USER_ID, corpStaffAfterSave.getUserId());
        assertEquals(NAME, corpStaffAfterSave.getName());
        assertEquals(DEPARTMENT, corpStaffAfterSave.getDepartment());
        assertEquals(ORDER_IN_DEPTS, corpStaffAfterSave.getOrderInDepts());
        assertEquals(IS_LEADER_IN_DEPTS, corpStaffAfterSave.getIsLeaderInDepts());
        assertEquals(POSITION, corpStaffAfterSave.getPosition());
        assertEquals(MOBILE, corpStaffAfterSave.getMobile());
        assertEquals(GENDER, corpStaffAfterSave.getGender());
        assertEquals(EMAIL, corpStaffAfterSave.getEmail());
        assertEquals(AVATAR, corpStaffAfterSave.getAvatar());
        assertEquals(TEL, corpStaffAfterSave.getTel());
        assertEquals(ENGLISH_NAME, corpStaffAfterSave.getEnglishName());
        assertEquals(STATUS, corpStaffAfterSave.getStatus());
        assertEquals(EXTATTR, corpStaffAfterSave.getExtattr());

        corpStaffAfterSave.setName(NAME_2);
        corpStaffAfterSave.setRsqUserId(RSQ_USER_ID);
        corpStaffAfterSave.setRsqUsername(RSQ_USERNAME);
        corpStaffAfterSave.setRsqPassword(RSQ_PASSWORD);
        corpStaffAfterSave.setRsqLoginToken(RSQ_LOGIN_TOKEN);
        corpStaffDao.saveOrUpdateCorpStaff(corpStaffAfterSave);
        CorpStaffDO corpStaffAfterUpdate = corpStaffDao.getCorpStaffByCorpIdAndUserId(CORP_ID, USER_ID);
        assertEquals(NAME_2, corpStaffAfterUpdate.getName());
        assertEquals(RSQ_USER_ID, corpStaffAfterUpdate.getRsqUserId());
        assertEquals(RSQ_USERNAME, corpStaffAfterUpdate.getRsqUsername());
        assertEquals(RSQ_PASSWORD, corpStaffAfterUpdate.getRsqPassword());
        assertEquals(RSQ_LOGIN_TOKEN, corpStaffAfterUpdate.getRsqLoginToken());

        corpStaffDao.removeCorpStaffByCorpIdAndUserId(CORP_ID, USER_ID);
        CorpStaffDO corpStaffAfterRemove = corpStaffDao.getCorpStaffByCorpIdAndUserId(CORP_ID, USER_ID);
        assertNull(corpStaffAfterRemove);
    }
}
