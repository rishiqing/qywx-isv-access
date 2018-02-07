package com.rishiqing.qywx.service.biz.corp;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.qywx.service.exception.HttpException;
import com.rishiqing.qywx.service.exception.ObjectNotExistException;
import com.rishiqing.qywx.service.model.corp.CorpDeptVO;
import com.rishiqing.qywx.service.model.corp.CorpTokenVO;

public interface DeptService {
    /**
     * 获取department list列表
     */
    void fetchAndSaveDeptInfo(CorpTokenVO corpTokenVO, CorpDeptVO corpDeptVO) throws HttpException, UnirestException;

    /**
     * 保存department到本地
     * @param corpDeptVO
     */
    void createDept(CorpDeptVO corpDeptVO);

    /**
     * 更新department
     * 注意:corpDeptVO传入的参数可能不全,可能只包括修改过的属性
     * @param corpDeptVO
     */
    void updateDept(CorpDeptVO corpDeptVO) throws ObjectNotExistException;

    /**
     * @param corpDeptVO
     */
    void deleteDept(CorpDeptVO corpDeptVO) throws ObjectNotExistException;
}
