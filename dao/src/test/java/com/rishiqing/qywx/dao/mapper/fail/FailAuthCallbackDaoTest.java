package com.rishiqing.qywx.dao.mapper.fail;

import com.rishiqing.qywx.dao.model.fail.FailAuthCallbackDO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:dao-test-spring-context.xml")
public class FailAuthCallbackDaoTest {
    @Autowired
    private FailAuthCallbackDao failAuthCallbackDao;

    @Test
    public void test_saveAndRemove(){
        FailAuthCallbackDO fail = new FailAuthCallbackDO();
        fail.setSuiteKey("suiteKeyXXXX");
        fail.setCorpId("corpIdYYYY");
        fail.setFailType("FAIL_TYPEZZZ");
        fail.setFailNote("");
        fail.setInfoType("create_auth");
        failAuthCallbackDao.saveOrUpdateFailAuthCallback(fail);
        Assert.assertNotNull(fail.getId());
        failAuthCallbackDao.removeFailAuthCallbackById(fail.getId());
    }

}