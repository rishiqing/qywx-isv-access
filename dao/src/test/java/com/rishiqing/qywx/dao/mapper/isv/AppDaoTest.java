package com.rishiqing.qywx.dao.mapper.isv;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:dao-test-spring-context.xml")
public class AppDaoTest {
    @Autowired
    private AppDao appDao;

    @Test
    public void test_getApp(){
        Assert.assertEquals(1, appDao.getAllApp().size());
    }
}
