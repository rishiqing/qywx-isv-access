package com.rishiqing.qywx.dao.mapper.isv;

import com.rishiqing.qywx.dao.model.isv.SuiteTicketDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:dao-test-spring-context.xml")
public class SuiteTicketDaoTest {
    private static final String SUITE_KEY = "xxxxxxxxxxx";
    @Autowired
    private SuiteTicketDao suiteTicketDao;

    @Test
    public void test_saveOrUpdateSuiteTicket(){
        String testTicket = "helloticket";
        SuiteTicketDO suiteTicketDO = new SuiteTicketDO();
        suiteTicketDO.setSuiteKey(SUITE_KEY);
        suiteTicketDO.setTicket(testTicket);
        suiteTicketDao.saveOrUpdateSuiteTicket(suiteTicketDO);

        SuiteTicketDO suiteTicketDOAfterSave = suiteTicketDao.getSuiteTicketBySuiteKey(SUITE_KEY);
        assertEquals(testTicket, suiteTicketDOAfterSave.getTicket());
    }
}
