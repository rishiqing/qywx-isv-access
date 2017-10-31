package com.rishiqing.qywx.dao.mapper.corp;

import com.rishiqing.qywx.dao.model.corp.CorpAppDO;
import com.rishiqing.qywx.dao.model.corp.CorpSuiteDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:dao-test-spring-context.xml")
public class CorpSuiteDaoTest {
    private String CORP_ID = "demo_corp_id";
    private String SUITE_KEY = "xxxxxx";
    private String PERMANENT_CODE = "xxxxyyyyzzzz";
    private String PERMANENT_CODE_2 = "xxxxyyyyzzzz2";
    @Autowired
    private CorpSuiteDao corpSuiteDao;

    @Test
    public void test_saveOrUpdateCorp(){
        CorpSuiteDO objDO = new CorpSuiteDO();
        objDO.setCorpId(CORP_ID);
        objDO.setSuiteKey(SUITE_KEY);
        objDO.setPermanentCode(PERMANENT_CODE);
        corpSuiteDao.saveOrUpdateCorpSuite(objDO);

        CorpSuiteDO objAfterSave = corpSuiteDao.getCorpSuiteBySuiteKeyAndCorpId(SUITE_KEY, CORP_ID);
        assertEquals(CORP_ID, objAfterSave.getCorpId());
        assertEquals(SUITE_KEY, objAfterSave.getSuiteKey());
        assertEquals(PERMANENT_CODE, objAfterSave.getPermanentCode());

        objAfterSave.setPermanentCode(PERMANENT_CODE_2);
        corpSuiteDao.saveOrUpdateCorpSuite(objAfterSave);
        CorpSuiteDO objAfterUpdate = corpSuiteDao.getCorpSuiteBySuiteKeyAndCorpId(SUITE_KEY, CORP_ID);
        assertEquals(PERMANENT_CODE_2, objAfterUpdate.getPermanentCode());

        corpSuiteDao.removeCorpSuiteBySuiteKeyAndCorpId(SUITE_KEY, CORP_ID);
        CorpSuiteDO objAfterRemove = corpSuiteDao.getCorpSuiteBySuiteKeyAndCorpId(SUITE_KEY, CORP_ID);
        assertNull(objAfterRemove);
    }
}
