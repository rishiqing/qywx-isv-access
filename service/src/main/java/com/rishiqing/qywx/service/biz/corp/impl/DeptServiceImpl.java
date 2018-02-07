package com.rishiqing.qywx.service.biz.corp.impl;

import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.qywx.service.biz.corp.DeptService;
import com.rishiqing.qywx.service.common.corp.CorpDeptManageService;
import com.rishiqing.qywx.service.exception.HttpException;
import com.rishiqing.qywx.service.exception.ObjectNotExistException;
import com.rishiqing.qywx.service.model.corp.CorpDeptVO;
import com.rishiqing.qywx.service.model.corp.CorpTokenVO;
import com.rishiqing.qywx.service.model.corp.helper.CorpDeptConverter;
import com.rishiqing.qywx.service.util.http.HttpUtilCorp;
import com.rishiqing.qywx.service.util.http.converter.Json2BeanConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Wallace Mao
 * 部门服务的实现
 */
public class DeptServiceImpl implements DeptService {
    private static final Logger limitLogger = LoggerFactory.getLogger("SYS_LIMIT_WARN_LOGGER");
    private static final Logger logger = LoggerFactory.getLogger("SERVICE_CORP_TRANSFER_LOGGER");
    @Autowired
    private HttpUtilCorp httpUtilCorp;
    @Autowired
    private CorpDeptManageService corpDeptManageService;

    /**
     * 批量获取部门列表信息
     * @param corpTokenVO
     * @param corpDeptVO
     * @throws HttpException
     * @throws UnirestException
     */
    @Override
    public void fetchAndSaveDeptInfo(CorpTokenVO corpTokenVO, @Nullable CorpDeptVO corpDeptVO) throws HttpException, UnirestException {
        logger.info("====fetchAndSaveDeptInfo====");
        JSONObject json = httpUtilCorp.getDepartmentList(corpTokenVO, corpDeptVO);
        logger.info("------json----" + json);
        String corpId = corpTokenVO.getCorpId();
        List<CorpDeptVO> deptList =
                Json2BeanConverter.generateDepartmentList(corpId, json);
        if(deptList.size() > 100){
            limitLogger.warn(
                    "corp department list size is too long, corpId: {}, department number: {}, " +
                            "should user batch insert!!", corpId, deptList.size());
        }
        for(CorpDeptVO dept : deptList){
            corpDeptManageService.saveOrUpdateCorpDept(dept);
        }
    }

    @Override
    public void createDept(CorpDeptVO corpDeptVO){
        corpDeptManageService.saveOrUpdateCorpDept(corpDeptVO);
    }

    @Override
    public void updateDept(CorpDeptVO corpDeptVO) throws ObjectNotExistException {
        CorpDeptVO existsCorpDept = corpDeptManageService.getCorpDeptByCorpIdAndDeptId(
                corpDeptVO.getCorpId(),
                corpDeptVO.getDeptId());
        if(null == existsCorpDept){
            throw new ObjectNotExistException("updateDept exception, corp department with corpId and deptId not exist"
                    + corpDeptVO.getCorpId() + corpDeptVO.getDeptId());
        }
        CorpDeptConverter.mergeCorpDeptVO(corpDeptVO, existsCorpDept);
        corpDeptManageService.saveOrUpdateCorpDept(existsCorpDept);
    }

    @Override
    public void deleteDept(CorpDeptVO corpDeptVO) throws ObjectNotExistException {
        CorpDeptVO existsCorpDept = corpDeptManageService.getCorpDeptByCorpIdAndDeptId(
                corpDeptVO.getCorpId(),
                corpDeptVO.getDeptId());
        if(null == existsCorpDept){
            throw new ObjectNotExistException("deleteDept exception, corp department with corpId and deptId not exist"
                    + corpDeptVO.getCorpId() + corpDeptVO.getDeptId());
        }
        corpDeptManageService.deleteCorpDeptByCorpIdAndDeptId(corpDeptVO.getCorpId(), corpDeptVO.getDeptId());
    }
}
