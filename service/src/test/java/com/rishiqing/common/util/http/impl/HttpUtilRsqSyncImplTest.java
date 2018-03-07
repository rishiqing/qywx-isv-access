package com.rishiqing.common.util.http.impl;

import com.alibaba.fastjson.JSONArray;
import com.rishiqing.common.exception.RsqSyncException;
import com.rishiqing.common.model.RsqCommonUserVO;
import com.rishiqing.common.model.RsqDepartmentVO;
import com.rishiqing.common.model.RsqTeamVO;
import com.rishiqing.common.util.http.HttpUtilRsqSync;
import com.rishiqing.qywx.dao.model.isv.SuiteDO;
import com.rishiqing.qywx.service.common.isv.SuiteManageService;
import com.rishiqing.qywx.service.model.isv.SuiteVO;
import junit.framework.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Wallace Mao
 * Date: 2018-02-08 17:34
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:service-test-spring-context.xml")
public class HttpUtilRsqSyncImplTest {
    @Autowired
    private HttpUtilRsqSync httpUtilRsqSync;
    @Autowired
    private SuiteManageService suiteManageService;

    private Long dateNum = new Date().getTime();

    /**
     * 测试部门的增删改查
     */
    @Test
    public void test_manageDepartment(){
        SuiteVO suite = suiteManageService.getSuiteInfoByKey("wxe0de6067085faf0d");
        String appName = suite.getRsqAppName();
        String appToken = suite.getRsqAppToken();
        String teamName = "企业微信" + dateNum + "测试企业1";
        String teamNote = "备注信息1";
        String corpId = "qywx-corp-id-" + dateNum + "-1";

        RsqTeamVO team = new RsqTeamVO();
        team.setName(teamName);
        team.setNote(teamNote);
        team.setCorpId(corpId);

        try {
            // 创建团队
            RsqTeamVO rTeam = httpUtilRsqSync.createCorp(appName, appToken, team);

            assertNotNull(rTeam.getId());
            assertEquals(teamName, rTeam.getName());
            assertEquals(teamNote, rTeam.getNote());
            assertEquals(corpId, rTeam.getOuterId());

            //  创建部门
            RsqDepartmentVO dept1 = createDepartment(corpId, 1);
            dept1.setTeamId(String.valueOf(rTeam.getId()));

            RsqDepartmentVO rDept1 = httpUtilRsqSync.createDepartment(appName, appToken, rTeam, dept1);
            assertNotNull(rDept1.getId());
            assertEquals(dept1.getName(), rDept1.getName());
            assertEquals(dept1.getTeamId(), rDept1.getTeamId());

            RsqDepartmentVO dept2 = createDepartment(corpId, 2);
            dept2.setTeamId(String.valueOf(rTeam.getId()));
            dept2.setParentId(String.valueOf(rDept1.getId()));
            RsqDepartmentVO rDept2 = httpUtilRsqSync.createDepartment(appName, appToken, rTeam, dept2);
            assertNotNull(rDept2.getId());
            assertEquals(rDept2.getParentId(), String.valueOf(rDept1.getId()));

            RsqDepartmentVO dept3 = createDepartment(corpId, 3);
            dept3.setTeamId(String.valueOf(rTeam.getId()));
            RsqDepartmentVO rDept3 = httpUtilRsqSync.createDepartment(appName, appToken, rTeam, dept3);
            assertNotNull(rDept3.getId());

            // 修改部门
            String newName = "修改过的部门名称";
            rDept1.setName(newName);
            rDept1 = httpUtilRsqSync.updateDepartment(appName, appToken, rTeam, rDept1);
            assertEquals(newName, rDept1.getName());

            rDept2.setParentId(String.valueOf(rDept3.getId()));
            rDept2 = httpUtilRsqSync.updateDepartment(appName, appToken, rTeam, rDept2);
            assertEquals(rDept2.getParentId(), String.valueOf(rDept3.getId()));

            //  删除部门
            httpUtilRsqSync.deleteDepartment(appName, appToken, rTeam, rDept1);

        } catch (RsqSyncException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试部门成员的增删改查
     */
    @Test
    public void test_manageUser(){
        SuiteVO suite = suiteManageService.getSuiteInfoByKey("wxe0de6067085faf0d");
        String appName = suite.getRsqAppName();
        String appToken = suite.getRsqAppToken();
        String teamName = "企业微信" + dateNum + "测试企业2";
        String teamNote = "备注信息2";
        String corpId = "qywx-corp-id-" + dateNum + "-2";

        RsqTeamVO team = new RsqTeamVO();
        team.setName(teamName);
        team.setNote(teamNote);
        team.setCorpId(corpId);

        try {
            // 创建团队
            RsqTeamVO rTeam = httpUtilRsqSync.createCorp(appName, appToken, team);

            assertNotNull(rTeam.getId());
            assertEquals(teamName, rTeam.getName());
            assertEquals(teamNote, rTeam.getNote());
            assertEquals(corpId, rTeam.getOuterId());

            //  创建部门
            RsqDepartmentVO dept1 = createDepartment(corpId, 1);
            dept1.setTeamId(String.valueOf(rTeam.getId()));
            RsqDepartmentVO rDept1 = httpUtilRsqSync.createDepartment(appName, appToken, rTeam, dept1);
            assertNotNull(rDept1.getId());

            RsqDepartmentVO dept2 = createDepartment(corpId, 2);
            dept2.setTeamId(String.valueOf(rTeam.getId()));
            RsqDepartmentVO rDept2 = httpUtilRsqSync.createDepartment(appName, appToken, rTeam, dept2);
            assertNotNull(rDept2.getId());

            //  创建成员
            RsqCommonUserVO user1 = createCommonUser(corpId, 1);
            RsqCommonUserVO rUser1 = httpUtilRsqSync.createUser(appName, appToken, rTeam, user1);
            assertNotNull(rUser1.getId());
            assertEquals(user1.getUsername(), rUser1.getUsername());
            assertEquals(user1.getRealName(), rUser1.getRealName());
            assertEquals(user1.getUnionId(), rUser1.getUnionId());
            assertEquals(rTeam.getId(), rUser1.getTeamId());
            assertEquals(appName, rUser1.getFromClient());

            JSONArray deptArr = new JSONArray();
            deptArr.add(rDept1.getId());
            RsqCommonUserVO user2 = createCommonUser(corpId, 2);
            user2.setDepartment(JSONArray.toJSONString(deptArr));
            RsqCommonUserVO rUser2 = httpUtilRsqSync.createUser(appName, appToken, rTeam, user2);
            assertNotNull(rUser2.getId());

            //  修改成员
            String newRealName = "新的realName";
            String newUnionId = "newUnionId-" + dateNum;
            rUser1.setRealName(newRealName);
            rUser1.setUnionId(newUnionId);
            rUser1 = httpUtilRsqSync.updateUser(appName, appToken, rTeam, rUser1);
            assertEquals(newRealName, rUser1.getRealName());
            assertEquals(newUnionId, rUser1.getUnionId());

            JSONArray newDept = new JSONArray();
            newDept.add(rDept1.getId());
            newDept.add(rDept2.getId());
            rUser1.setDepartment(JSONArray.toJSONString(newDept));
            rUser1 = httpUtilRsqSync.updateUser(appName, appToken, rTeam, rUser1);
            assertNotNull(rUser1.getId());

            //  设置管理员
            rUser1.setAdmin(true);
            rUser1 = httpUtilRsqSync.updateUser(appName, appToken, rTeam, rUser1);
            assertNotNull(rUser1.getId());

            //  删除成员
            httpUtilRsqSync.userLeaveTeam(appName, appToken, rTeam, rUser1);
            assertNotNull(rUser1.getId());

        } catch (RsqSyncException e) {
            e.printStackTrace();
        }
    }

    private RsqDepartmentVO createDepartment(String corpId, Integer number){
        RsqDepartmentVO dept = new RsqDepartmentVO();
        dept.setCorpId(corpId);
        dept.setDeptId(String.valueOf(number));
        dept.setName("企业微信" + dateNum + "测试部门" + number);
        dept.setOrderNum(Long.valueOf(number));
        return dept;
    }

    private RsqCommonUserVO createCommonUser(String corpId, Integer number){
        RsqCommonUserVO user = new RsqCommonUserVO();
        user.setUsername("qywxtest" + dateNum + "_" + number + "@rsqqywxtest.com");
        user.setPassword(String.valueOf(dateNum));
        user.setRealName("真实名称" + number);
        user.setCorpId(corpId);
        user.setUserId("qywxteset" + dateNum + "_" + number);
        user.setUnionId("unionId-" + dateNum + "-" + number);
        return user;
    }
}
