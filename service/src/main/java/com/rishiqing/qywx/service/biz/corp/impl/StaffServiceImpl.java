package com.rishiqing.qywx.service.biz.corp.impl;

import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.qywx.service.biz.corp.StaffService;
import com.rishiqing.qywx.service.common.corp.CorpDeptManageService;
import com.rishiqing.qywx.service.common.corp.CorpStaffManageService;
import com.rishiqing.common.exception.HttpException;
import com.rishiqing.qywx.service.exception.ObjectNotExistException;
import com.rishiqing.qywx.service.model.corp.*;
import com.rishiqing.qywx.service.model.corp.helper.CorpStaffConverter;
import com.rishiqing.qywx.service.model.isv.SuiteTokenVO;
import com.rishiqing.qywx.service.util.http.HttpUtilCorp;
import com.rishiqing.qywx.service.util.http.converter.Json2BeanConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;
import java.util.List;

public class StaffServiceImpl implements StaffService {
    private static final Logger limitLogger = LoggerFactory.getLogger("SYS_LIMIT_WARN_LOGGER");
    @Autowired
    private HttpUtilCorp httpUtilCorp;
    @Autowired
    private CorpStaffManageService corpStaffManageService;

    /**
     * 获取并保存一个公司的所有员工到本地
     * @param corpTokenVO
     */
    @Override
    public void fetchAndSaveStaffList(CorpTokenVO corpTokenVO, @Nullable CorpDeptVO corpDeptVO) {
        JSONObject json = httpUtilCorp.getDepartmentStaffList(corpTokenVO, corpDeptVO, true);
        String corpId = corpTokenVO.getCorpId();
        List<CorpStaffVO> staffList =
                Json2BeanConverter.generateStaffList(corpId, json);
        if(staffList.size() > 100){
            limitLogger.warn(
                    "corp staff list size is too long, corpId: {}, staff number: {}, " +
                            "should user batch insert!!", corpId, staffList.size());
        }
        for(CorpStaffVO staff : staffList){
            corpStaffManageService.saveOrUpdateCorpStaff(staff);
        }
    }

    @Override
    public void fetchAndSaveAdminList(SuiteTokenVO suiteTokenVO, CorpAppVO corpAppVO) {
        JSONObject json = httpUtilCorp.getAppAdminList(suiteTokenVO, corpAppVO);

        String corpId = corpAppVO.getCorpId();
        List<CorpStaffVO> staffList =
                Json2BeanConverter.generateCorpAdminList(corpId, json);
        if(staffList.size() > 100){
            limitLogger.warn(
                    "corp staff list size is too long, corpId: {}, staff number: {}, " +
                            "should user batch insert!!", corpId, staffList.size());
        }
        for(CorpStaffVO staff : staffList){
            CorpStaffVO dbStaff = corpStaffManageService.getCorpStaffByCorpIdAndUserId(staff.getCorpId(), staff.getUserId());
            if(null != dbStaff){
                CorpStaffConverter.mergeCorpStaffVO(staff, dbStaff);
                corpStaffManageService.saveOrUpdateCorpStaff(dbStaff);
            }
        }
    }

    @Override
    public void fetchAndSaveStaff(CorpTokenVO corpTokenVO, String userId) {
        JSONObject json = httpUtilCorp.getStaff(corpTokenVO, userId);
        String corpId = corpTokenVO.getCorpId();
        CorpStaffVO staff =
                Json2BeanConverter.generateCorpStaff(corpId, json);
        corpStaffManageService.saveOrUpdateCorpStaff(staff);
    }

    @Override
    public CorpStaffVO getStaff(CorpStaffVO corpStaffVO) {
        return corpStaffManageService.getCorpStaffByCorpIdAndUserId(corpStaffVO.getCorpId(), corpStaffVO.getUserId());
    }

    @Override
    public void createStaff(CorpStaffVO corpStaffVO) {
        corpStaffManageService.saveOrUpdateCorpStaff(corpStaffVO);
    }

    @Override
    public void updateStaff(CorpStaffVO corpStaffVO) {
        CorpStaffVO existsCorpStaff = corpStaffManageService.getCorpStaffByCorpIdAndUserId(
                corpStaffVO.getCorpId(),
                corpStaffVO.getUserId());
        if(null == existsCorpStaff){
            throw new ObjectNotExistException("updateStaff exception, corp staff with corpId and userId not exist"
                    + corpStaffVO.getCorpId() + corpStaffVO.getUserId());
        }
        CorpStaffConverter.mergeCorpStaffVO(corpStaffVO, existsCorpStaff);
        corpStaffManageService.saveOrUpdateCorpStaff(existsCorpStaff);
    }

    @Override
    public void deleteStaff(CorpStaffVO corpStaffVO) {
        CorpStaffVO existsCorpStaff = corpStaffManageService.getCorpStaffByCorpIdAndUserId(
                corpStaffVO.getCorpId(),
                corpStaffVO.getUserId());
        if(null == existsCorpStaff){
            throw new ObjectNotExistException("deleteStaff exception, corp staff with corpId and userId not exist"
                    + corpStaffVO.getCorpId() + corpStaffVO.getUserId());
        }
        corpStaffManageService.deleteCorpStaffByCorpIdAndUserId(corpStaffVO.getCorpId(), corpStaffVO.getUserId());
    }
}
