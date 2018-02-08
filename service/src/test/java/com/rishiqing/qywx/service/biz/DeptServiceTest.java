package com.rishiqing.qywx.service.biz;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.qywx.service.biz.corp.DeptService;
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
public class DeptServiceTest {
    @Autowired
    private DeptService deptService;
    @Autowired
    private CorpTokenManageService corpTokenManageService;

    @Test
    public void test_fetchDepartmentList(){
        String corpId = "wxec002534a59ea2e7";
        String suiteKey = "tj146dbe5cecf74725";
        CorpTokenVO corpTokenVO = corpTokenManageService.getCorpToken(suiteKey, corpId);
        try {
            deptService.fetchAndSaveDeptInfo(corpTokenVO, null);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }

}
