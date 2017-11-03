package com.rishiqing.qywx.service.biz.corp.impl;

import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.qywx.service.biz.corp.StaffService;
import com.rishiqing.qywx.service.common.corp.CorpStaffManageService;
import com.rishiqing.qywx.service.exception.HttpException;
import com.rishiqing.qywx.service.model.corp.CorpDeptVO;
import com.rishiqing.qywx.service.model.corp.CorpStaffVO;
import com.rishiqing.qywx.service.model.corp.CorpTokenVO;
import com.rishiqing.qywx.service.util.http.HttpUtilCorp;
import com.rishiqing.qywx.service.util.http.converter.Json2BeanConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;
import java.util.List;

public class StaffServiceImpl implements StaffService {
    private static final Logger limitLogger = LoggerFactory.getLogger("SYS_LIMIT_WARN_LOGGER");
    private static final Logger logger = LoggerFactory.getLogger("SERVICE_CORP_TRANSFER_LOGGER");
    @Autowired
    private HttpUtilCorp httpUtilCorp;
    @Autowired
    private CorpStaffManageService corpStaffManageService;

    @Override
    public void fetchAndSaveStaffList(CorpTokenVO corpTokenVO, @Nullable CorpDeptVO corpDeptVO) throws HttpException, UnirestException {
        JSONObject json = httpUtilCorp.getDepartmentStaffList(corpTokenVO, corpDeptVO);
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
}
