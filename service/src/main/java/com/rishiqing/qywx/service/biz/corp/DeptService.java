package com.rishiqing.qywx.service.biz.corp;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.qywx.service.exception.HttpException;
import com.rishiqing.qywx.service.model.corp.CorpDeptVO;
import com.rishiqing.qywx.service.model.corp.CorpTokenVO;

public interface DeptService {
    /**
     * 获取department list列表
     */
    void fetchAndSaveDeptInfo(CorpTokenVO corpTokenVO, CorpDeptVO corpDeptVO) throws HttpException, UnirestException;
}
