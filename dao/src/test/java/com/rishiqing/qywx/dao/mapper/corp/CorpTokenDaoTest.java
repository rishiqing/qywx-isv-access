package com.rishiqing.qywx.dao.mapper.corp;

import com.rishiqing.qywx.dao.model.corp.CorpTokenDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:dao-test-spring-context.xml")
public class CorpTokenDaoTest {
    private String CORP_ID = "demo_corp_id";
    private String SUITE_KEY = "xxxxxx";
    private String CORP_TOKEN = "xxxxyyyyzzzz";
    private String CORP_TOKEN_2 = "xxxxyyyyzzzz2";
    private Long EXPIRES_IN = 7200L;
    private Long EXPIRES_IN_2 = 7300L;
    @Autowired
    private CorpTokenDao corpTokenDao;

    @Test
    public void test_saveOrUpdateCorpToken(){
        CorpTokenDO objDO = new CorpTokenDO();
        objDO.setCorpId(CORP_ID);
        objDO.setSuiteKey(SUITE_KEY);
        objDO.setCorpToken(CORP_TOKEN);
        objDO.setExpiresIn(EXPIRES_IN);
        corpTokenDao.saveOrUpdateCorpToken(objDO);

        CorpTokenDO objAfterSave = corpTokenDao.getCorpTokenBySuiteKeyAndCorpId(SUITE_KEY, CORP_ID);
        assertEquals(CORP_ID, objAfterSave.getCorpId());
        assertEquals(SUITE_KEY, objAfterSave.getSuiteKey());
        assertEquals(CORP_TOKEN, objAfterSave.getCorpToken());
        assertEquals(EXPIRES_IN, objAfterSave.getExpiresIn());

        objAfterSave.setCorpToken(CORP_TOKEN_2);
        objAfterSave.setExpiresIn(EXPIRES_IN_2);
        corpTokenDao.saveOrUpdateCorpToken(objAfterSave);
        CorpTokenDO objAfterUpdate = corpTokenDao.getCorpTokenBySuiteKeyAndCorpId(SUITE_KEY, CORP_ID);
        assertEquals(CORP_TOKEN_2, objAfterUpdate.getCorpToken());
        assertEquals(EXPIRES_IN_2, objAfterUpdate.getExpiresIn());

        corpTokenDao.removeCorpTokenBySuiteKeyAndCorpId(SUITE_KEY, CORP_ID);
        CorpTokenDO objAfterRemove = corpTokenDao.getCorpTokenBySuiteKeyAndCorpId(SUITE_KEY, CORP_ID);
        assertNull(objAfterRemove);
    }
}
