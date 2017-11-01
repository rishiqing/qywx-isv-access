package com.rishiqing.qywx.dao.mapper.corp;

import com.rishiqing.qywx.dao.model.corp.CorpDO;
import com.rishiqing.qywx.dao.model.corp.CorpDeptDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.print.attribute.standard.MediaSize;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:dao-test-spring-context.xml")
public class CorpDeptDaoTest {
    private String CORP_ID = "demo_corp_id";
    private Long DEPT_ID = 2333L;
    private String NAME = "demo_corp";
    private String NAME_2 = "demo_corp2";
    private Long ORDER = 3444L;
    private Long PARENT_ID = 1L;
    @Autowired
    private CorpDeptDao corpDeptDao;

    @Test
    public void test_saveOrUpdateCorp(){
        CorpDeptDO corpDeptDO = new CorpDeptDO();
        corpDeptDO.setCorpId(CORP_ID);
        corpDeptDO.setDeptId(DEPT_ID);
        corpDeptDO.setName(NAME);
        corpDeptDO.setOrder(ORDER);
        corpDeptDO.setParentId(PARENT_ID);
        corpDeptDao.saveOrUpdateCorpDept(corpDeptDO);

        CorpDeptDO corpDeptAfterSave = corpDeptDao.getCorpDeptByCorpIdAndDeptId(CORP_ID, DEPT_ID);
        assertEquals(CORP_ID, corpDeptAfterSave.getCorpId());
        assertEquals(DEPT_ID, corpDeptAfterSave.getDeptId());
        assertEquals(NAME, corpDeptAfterSave.getName());
        assertEquals(ORDER, corpDeptAfterSave.getOrder());
        assertEquals(PARENT_ID, corpDeptAfterSave.getParentId());

        corpDeptAfterSave.setName(NAME_2);
        corpDeptDao.saveOrUpdateCorpDept(corpDeptAfterSave);
        CorpDeptDO corpDeptAfterUpdate = corpDeptDao.getCorpDeptByCorpIdAndDeptId(CORP_ID, DEPT_ID);
        assertEquals(NAME_2, corpDeptAfterUpdate.getName());

        corpDeptDao.removeCorpDeptByCorpIdAndDeptId(CORP_ID, DEPT_ID);
        CorpDeptDO corpDeptAfterRemove = corpDeptDao.getCorpDeptByCorpIdAndDeptId(CORP_ID, DEPT_ID);
        assertNull(corpDeptAfterRemove);
    }
}
