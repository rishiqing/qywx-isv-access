package com.rishiqing.qywx.dao.mapper.corp;

import com.rishiqing.qywx.dao.model.corp.CorpAppDO;
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
public class CorpAppDaoTest {
    private String CORP_ID = "demo_corp_id";
    private Long AGENT_ID = 123L;
    private String NAME = "日事清";
    private String NAME_2 = "日事清2";
    private String ROUND_LOGO_URL = "http://roundlogourl";
    private String SQUARE_LOGO_URL = "http://squarelogourl";
    private String SUITE_KEY = "xxxxxx";
    private Long APP_ID = 99L;
    @Autowired
    private CorpAppDao corpAppDao;

    @Test
    public void test_saveOrUpdateCorp(){
        CorpAppDO corpAppDO = new CorpAppDO();
        corpAppDO.setCorpId(CORP_ID);
        corpAppDO.setAgentId(AGENT_ID);
        corpAppDO.setName(NAME);
        corpAppDO.setRoundLogoUrl(ROUND_LOGO_URL);
        corpAppDO.setSquareLogoUrl(SQUARE_LOGO_URL);
        corpAppDO.setSuiteKey(SUITE_KEY);
        corpAppDO.setAppId(APP_ID);
        corpAppDao.saveOrUpdateCorpApp(corpAppDO);

        CorpAppDO corpAppAfterSave = corpAppDao.getCorpAppByAppIdAndCorpId(APP_ID, CORP_ID);
        assertEquals(CORP_ID, corpAppAfterSave.getCorpId());
        assertEquals(AGENT_ID, corpAppAfterSave.getAgentId());
        assertEquals(NAME, corpAppAfterSave.getName());
        assertEquals(ROUND_LOGO_URL, corpAppAfterSave.getRoundLogoUrl());
        assertEquals(SQUARE_LOGO_URL, corpAppAfterSave.getSquareLogoUrl());
        assertEquals(SUITE_KEY, corpAppAfterSave.getSuiteKey());
        assertEquals(APP_ID, corpAppAfterSave.getAppId());

        corpAppAfterSave.setName(NAME_2);
        corpAppDao.saveOrUpdateCorpApp(corpAppAfterSave);
        CorpAppDO corpAppAfterUpdate = corpAppDao.getCorpAppByAppIdAndCorpId(APP_ID, CORP_ID);
        assertEquals(NAME_2, corpAppAfterUpdate.getName());

        corpAppDao.removeCorpAppByAppIdAndCorpId(APP_ID, CORP_ID);
        CorpAppDO corpAppAfterRemove = corpAppDao.getCorpAppByAppIdAndCorpId(APP_ID, CORP_ID);
        assertNull(corpAppAfterRemove);
    }
}
