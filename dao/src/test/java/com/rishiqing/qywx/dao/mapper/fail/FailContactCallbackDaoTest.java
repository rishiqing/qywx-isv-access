package com.rishiqing.qywx.dao.mapper.fail;

import com.rishiqing.qywx.dao.model.fail.FailContactCallbackDO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:dao-test-spring-context.xml")
public class FailContactCallbackDaoTest {
    @Autowired
    private FailContactCallbackDao failContactCallbackDao;

    @Test
    public void test_saveAndRemove(){
        FailContactCallbackDO fail = new FailContactCallbackDO();
        fail.setSuiteKey("suiteKeyXXXX");
        fail.setCorpId("corpIdYYYY");
        fail.setFailType("FAIL_TYPEZZZ");
        fail.setFailNote("");
        fail.setChangeType("create_user");
        fail.setEventMsg("{}");
        failContactCallbackDao.saveOrUpdateFailContactCallback(fail);
        Assert.assertNotNull(fail.getId());
        failContactCallbackDao.removeFailContactCallbackById(fail.getId());
    }
}