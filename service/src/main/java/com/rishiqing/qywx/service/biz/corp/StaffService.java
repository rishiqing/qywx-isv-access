package com.rishiqing.qywx.service.biz.corp;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.qywx.service.exception.HttpException;
import com.rishiqing.qywx.service.model.corp.CorpDeptVO;
import com.rishiqing.qywx.service.model.corp.CorpTokenVO;

import javax.annotation.Nullable;

public interface StaffService {
    /**
     * 从远程获取用户列表
     * @param corpTokenVO
     * @param corpDeptVO
     */
    void fetchAndSaveStaffList(CorpTokenVO corpTokenVO, @Nullable CorpDeptVO corpDeptVO) throws HttpException, UnirestException;
}
