package com.rishiqing.qywx.service.biz.corp.impl;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.qywx.service.biz.corp.DeptService;
import com.rishiqing.qywx.service.biz.corp.StaffService;
import com.rishiqing.qywx.service.biz.corp.TagService;
import com.rishiqing.qywx.service.common.corp.CorpTagManageService;
import com.rishiqing.qywx.service.model.corp.CorpDeptVO;
import com.rishiqing.qywx.service.model.corp.CorpStaffVO;
import com.rishiqing.qywx.service.model.corp.CorpTagDetailVO;
import com.rishiqing.qywx.service.model.corp.CorpTokenVO;
import com.rishiqing.qywx.service.util.http.HttpUtilCorp;
import com.rishiqing.qywx.service.util.http.converter.Json2BeanConverter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-04-27 21:56
 */
public class TagServiceImpl implements TagService {
    @Autowired
    private HttpUtilCorp httpUtilCorp;
    @Autowired
    private CorpTagManageService corpTagManageService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private DeptService deptService;

    @Override
    public void fetchAndSaveTagDetailList(CorpTokenVO corpTokenVO, Long tagId) {
        JSONObject json = httpUtilCorp.getTagDetail(corpTokenVO, tagId);
        String corpId = corpTokenVO.getCorpId();
        CorpTagDetailVO corpTagDetailVO =
                Json2BeanConverter.generateCorpTagDetail(corpId, tagId, json);

        //  保存标签相关的信息，包括标签、标签下的部门、标签下的成员
        corpTagManageService.saveOrUpdateCorpTag(corpTagDetailVO);
        corpTagManageService.saveOrUpdateCorpTagCorpStaff(corpTagDetailVO);
        corpTagManageService.saveOrUpdateCorpTagCorpDept(corpTagDetailVO);

        //  fetch标签下的成员和部门信息，同步到本地
        List<String> userList = corpTagDetailVO.getUserList();
        for(String userId : userList){
            staffService.fetchAndSaveStaff(corpTokenVO, userId);
        }

        List<Long> partyList = corpTagDetailVO.getPartyList();
        for(Long deptId : partyList){
            deptService.fetchAndSaveDeptStaffList(corpTokenVO, deptId);
        }
    }
}
