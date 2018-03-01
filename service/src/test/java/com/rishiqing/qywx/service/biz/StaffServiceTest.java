package com.rishiqing.qywx.service.biz;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.qywx.service.biz.corp.StaffService;
import com.rishiqing.qywx.service.common.corp.CorpTokenManageService;
import com.rishiqing.common.exception.HttpException;
import com.rishiqing.qywx.service.model.corp.CorpTokenVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:service-test-spring-context.xml")
public class StaffServiceTest {
    @Autowired
    private StaffService staffService;
    @Autowired
    private CorpTokenManageService corpTokenManageService;

    @Test
    public void test_fetchDepartmentStaffList(){
        String corpId = "wxec002534a59ea2e7";
        String suiteKey = "tj146dbe5cecf74725";
        CorpTokenVO corpTokenVO = corpTokenManageService.getCorpToken(suiteKey, corpId);
        try {
            staffService.fetchAndSaveStaffList(corpTokenVO, null);
        } catch (HttpException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
