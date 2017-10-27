package com.rishiqing.qywx.dao.mapper.isv;

import com.rishiqing.qywx.dao.model.isv.SuiteDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:dao-test-spring-context.xml")
public class SuiteDaoTest {
    private final String SUITE_KEY = "tj146dbe5cecf74725-test";
    private final String SUITE_NAME = "日事清测试";

    @Autowired
    private SuiteDao suiteDao;

    @Test
    public void test_saveOrUpdateSuite() {
        SuiteDO suiteDO = new SuiteDO();
        suiteDO.setSuiteName(SUITE_NAME);
        suiteDO.setSuiteKey(SUITE_KEY);
        suiteDO.setSuiteSecret("xxxxxx_yyyyy-zzzzz_nnnnn");
        suiteDO.setEncodingAesKey("xxxxxxxxxxxxxx");
        suiteDO.setToken("tttttttttttttt");
        suiteDO.setCorpId("wxec00000000");
        suiteDao.saveOrUpdateSuite(suiteDO);

        SuiteDO suiteAfterSave = suiteDao.getSuiteBySuiteKey(SUITE_KEY);
        assertEquals(SUITE_NAME, suiteAfterSave.getSuiteName());

//        SuiteDO updateSuite = suiteDao.getSuiteBySuiteKey(SUITE_KEY);
//        assertEquals(SUITE_KEY, updateSuite.getSuiteKey());
//
//        updateSuite.setToken("aaaaa");
//        suiteDao.saveOrUpdateSuite(updateSuite);
//
//        SuiteDO suiteAfterUpdate = suiteDao.getSuiteBySuiteKey(SUITE_KEY);
//        assertEquals("aaaaa", suiteAfterUpdate.getToken());
    }

    @Test
    public void test_removeSuiteBySuiteKey(){

    }

    @Test
    public void test_getSuiteBySuiteKey(){
        SuiteDO suiteDO = suiteDao.getSuiteBySuiteKey(SUITE_KEY);
        System.out.println(suiteDO.getSuiteName());
        assertEquals(SUITE_NAME, suiteDO.getSuiteName());
    }
}
