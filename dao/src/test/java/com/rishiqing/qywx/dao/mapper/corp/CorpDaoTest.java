package com.rishiqing.qywx.dao.mapper.corp;

import com.rishiqing.qywx.dao.model.corp.CorpDO;
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
public class CorpDaoTest {
    private String CORP_ID = "demo_corp_id";
    private String AUTH_AVATAR = "http://demoavatar";
    private String AUTH_EMAIL = "demo_email@demo.com";
    private String AUTH_MOBILE = "13810001000";
    private String AUTH_NAME = "张三";
    private String AUTH_USER_ID = "xxxxxx";
    private Long CORP_AGENT_MAX = 99L;
    private String CORP_FULL_NAME = "北京创仕科锐信息技术有限公司";
    private String CORP_NAME = "日事清";
    private String CORP_NAME_2 = "日事清2";
    private String CORP_SQUARE_LOGO_URL = "http://squarelogourl";
    private String CORP_TYPE = "verified";
    private Long CORP_USER_MAX = 199L;
    private String CORP_WXQRCODE = "yyyyyyyyyyy";
    private Long SUBJECT_TYPE = 1L;
    private Long VERIFIED_END_TIME = new Date().getTime();
    @Autowired
    private CorpDao corpDao;

    @Test
    public void test_saveOrUpdateCorp(){
        CorpDO corpDO = new CorpDO();
        corpDO.setAuthAvatar(AUTH_AVATAR);
        corpDO.setAuthEmail(AUTH_EMAIL);
        corpDO.setAuthMobile(AUTH_MOBILE);
        corpDO.setAuthName(AUTH_NAME);
        corpDO.setAuthUserId(AUTH_USER_ID);
        corpDO.setCorpAgentMax(CORP_AGENT_MAX);
        corpDO.setCorpFullName(CORP_FULL_NAME);
        corpDO.setCorpId(CORP_ID);
        corpDO.setCorpName(CORP_NAME);
        corpDO.setCorpSquareLogoUrl(CORP_SQUARE_LOGO_URL);
        corpDO.setCorpType(CORP_TYPE);
        corpDO.setCorpUserMax(CORP_USER_MAX);
        corpDO.setCorpWxqrcode(CORP_WXQRCODE);
        corpDO.setSubjectType(SUBJECT_TYPE);
        corpDO.setVerifiedEndTime(VERIFIED_END_TIME);
        corpDao.saveOrUpdateCorp(corpDO);

        CorpDO corpAfterSave = corpDao.getCorpByCorpId(CORP_ID);
        assertEquals(AUTH_AVATAR, corpAfterSave.getAuthAvatar());
        assertEquals(AUTH_EMAIL, corpAfterSave.getAuthEmail());
        assertEquals(AUTH_MOBILE, corpAfterSave.getAuthMobile());
        assertEquals(AUTH_NAME, corpAfterSave.getAuthName());
        assertEquals(AUTH_USER_ID, corpAfterSave.getAuthUserId());
        assertEquals(CORP_AGENT_MAX, corpAfterSave.getCorpAgentMax());
        assertEquals(CORP_FULL_NAME, corpAfterSave.getCorpFullName());
        assertEquals(CORP_ID, corpAfterSave.getCorpId());
        assertEquals(CORP_NAME, corpAfterSave.getCorpName());
        assertEquals(CORP_SQUARE_LOGO_URL, corpAfterSave.getCorpSquareLogoUrl());
        assertEquals(CORP_TYPE, corpAfterSave.getCorpType());
        assertEquals(CORP_USER_MAX, corpAfterSave.getCorpUserMax());
        assertEquals(CORP_WXQRCODE, corpAfterSave.getCorpWxqrcode());
        assertEquals(SUBJECT_TYPE, corpAfterSave.getSubjectType());
        assertEquals(VERIFIED_END_TIME, corpAfterSave.getVerifiedEndTime());

        corpAfterSave.setCorpName(CORP_NAME_2);
        corpDao.saveOrUpdateCorp(corpAfterSave);
        CorpDO corpAfterUpdate = corpDao.getCorpByCorpId(CORP_ID);
        assertEquals(CORP_NAME_2, corpAfterUpdate.getCorpName());

        corpDao.removeCorpByCorpId(CORP_ID);
        CorpDO corpAfterRemove = corpDao.getCorpByCorpId(CORP_ID);
        assertNull(corpAfterRemove);
    }
}
