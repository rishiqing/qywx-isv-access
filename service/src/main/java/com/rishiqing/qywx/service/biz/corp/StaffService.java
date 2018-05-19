package com.rishiqing.qywx.service.biz.corp;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.common.exception.HttpException;
import com.rishiqing.qywx.service.exception.ObjectNotExistException;
import com.rishiqing.qywx.service.model.corp.CorpAppVO;
import com.rishiqing.qywx.service.model.corp.CorpDeptVO;
import com.rishiqing.qywx.service.model.corp.CorpStaffVO;
import com.rishiqing.qywx.service.model.corp.CorpTokenVO;
import com.rishiqing.qywx.service.model.isv.SuiteTokenVO;

import javax.annotation.Nullable;

public interface StaffService {

    /**
     * 从远程获取用户列表
     * @param corpTokenVO
     * @param corpDeptVO
     */
    void fetchAndSaveStaffList(CorpTokenVO corpTokenVO, @Nullable CorpDeptVO corpDeptVO);

    void fetchAndSaveAdminList(SuiteTokenVO suiteTokenVO, CorpAppVO corpAppVO);

    void fetchAndSaveStaff(CorpTokenVO corpTokenVO, String userId);

    /**
     * 获取用户，corpStaffVO中至少需要提供corpId和userId
     * @param corpStaffVO
     * @return
     */
    CorpStaffVO getStaff(CorpStaffVO corpStaffVO);

    /**
     * 保存staff到本地
     * @param corpStaffVO
     */
    void createStaff(CorpStaffVO corpStaffVO);

    /**
     * 更新staff
     * 注意:corpStaffVO传入的参数可能不全,可能只包括修改过的属性
     * @param corpStaffVO
     */
    void updateStaff(CorpStaffVO corpStaffVO);

    /**
     * @param corpStaffVO
     */
    void deleteStaff(CorpStaffVO corpStaffVO);
}
