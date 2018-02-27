package com.rishiqing.qywx.service.event.listener.mq;

import com.rishiqing.common.exception.RsqSyncException;
import com.rishiqing.qywx.service.biz.rsq.RsqCorpService;
import com.rishiqing.qywx.service.biz.rsq.RsqDeptService;
import com.rishiqing.qywx.service.biz.rsq.RsqStaffService;
import com.rishiqing.qywx.service.common.corp.CorpDeptManageService;
import com.rishiqing.qywx.service.common.corp.CorpManageService;
import com.rishiqing.qywx.service.common.corp.CorpStaffManageService;
import com.rishiqing.qywx.service.model.corp.CorpDeptVO;
import com.rishiqing.qywx.service.model.corp.CorpStaffVO;
import com.rishiqing.qywx.service.model.corp.CorpVO;
import com.rishiqing.qywx.service.util.http.converter.Xml2BeanConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-02-10 17:34
 */
public class PushCorpHandler {
    private static final Logger logger = LoggerFactory.getLogger("SERVICE_CORP_PUSH_RSQ_LOGGER");

    @Autowired
    private CorpManageService corpManageService;
    @Autowired
    private CorpDeptManageService corpDeptManageService;
    @Autowired
    private CorpStaffManageService corpStaffManageService;
    @Autowired
    private RsqCorpService rsqCorpService;
    @Autowired
    private RsqDeptService rsqDeptService;
    @Autowired
    private RsqStaffService rsqStaffService;
    /**
     * 处理创建部门
     * 1  获取需要创建的新部门的参数
     * 2  获取父部门的参数
     * 3  push创建
     * @param corpId
     * @param contentMap
     */
    public void handleCreateDept(String corpId, Map contentMap){
        CorpVO corpVO = corpManageService.getCorpByCorpId(corpId);
        CorpDeptVO changedDeptVO = Xml2BeanConverter.generateCorpDept(contentMap);
        CorpDeptVO parentDeptVO = corpDeptManageService
                .getCorpDeptByCorpIdAndDeptId(corpVO.getCorpId(), changedDeptVO.getParentId());
        try {
            rsqDeptService.pushAndCreateDept(corpVO, parentDeptVO, changedDeptVO);
        } catch (RsqSyncException e) {
            //TODO 进行重试

            logger.error("error in createDept handler", e);
        }
    }

    /**
     * 1  获取有改变的部门属性
     * 2  获取数据库中的部门，并设置rsqId，推送时将根据rsqId进行识别
     * 3  如果改变的属性包括父部门，那么就设置父部门
     * 4  push更新
     * @param corpId
     * @param contentMap
     */
    public void handleUpdateDept(String corpId, Map contentMap){
        CorpVO corpVO = corpManageService.getCorpByCorpId(corpId);
        CorpDeptVO changedDeptVO = Xml2BeanConverter.generateCorpDept(contentMap);
        CorpDeptVO dbDeptVO = corpDeptManageService
                .getCorpDeptByCorpIdAndDeptId(corpId, changedDeptVO.getDeptId());
        String rsqId = dbDeptVO.getRsqId();
        if(null == rsqId){
            return;
        }
        changedDeptVO.setRsqId(rsqId);
        CorpDeptVO parentDeptVO = null;
        if(null != changedDeptVO.getParentId()){
            parentDeptVO = corpDeptManageService
                    .getCorpDeptByCorpIdAndDeptId(corpId, changedDeptVO.getParentId());
        }
        rsqDeptService.pushAndUpdateDept(corpVO, parentDeptVO, changedDeptVO);
    }

    /**
     * 1  获取需要删除的部门的deptId
     * 2  从contentMap中获取到rsqId
     * 3  push删除
     * @param corpId
     * @param contentMap
     */
    public void handleDeleteDept(String corpId, Map contentMap){
        CorpVO corpVO = corpManageService.getCorpByCorpId(corpId);
        CorpDeptVO changedDeptVO = Xml2BeanConverter.generateCorpDept(contentMap);
        //  查找rsqId
        String rsqId = (String)contentMap.get("rsqId");
        if(null == rsqId){
            return;
        }
        changedDeptVO.setRsqId(rsqId);
        rsqDeptService.pushAndDeleteDept(corpVO, changedDeptVO);
    }

    /**
     * 1  获取需要新增的成员的参数
     * 2  查询获取成员的部门列表
     * 3  push新增成员
     * @param corpId
     * @param contentMap
     */
    public void handleCreateUser(String corpId, Map contentMap){
        CorpVO corpVO = corpManageService.getCorpByCorpId(corpId);
        CorpStaffVO changedStaffVO = Xml2BeanConverter.generateCorpStaff(contentMap);
        List<CorpDeptVO> corpDeptVOList = corpDeptManageService
                .listCorpDeptListByCorpIdAndDeptIdString(corpVO.getCorpId(), changedStaffVO.getDepartment());
        rsqStaffService.pushAndCreateStaff(corpVO, corpDeptVOList, changedStaffVO);
    }

    /**
     * 1  获取有改变的成员的属性
     * 2  根据corpId和userId从数据库读原对象，并设置rsqUserId
     * 3  如果department有更新，那么设置department列表
     * 4  push更新
     * @param corpId
     * @param contentMap
     */
    public void handleUpdateUser(String corpId, Map contentMap){
        CorpVO corpVO = corpManageService.getCorpByCorpId(corpId);
        CorpStaffVO changedStaffVO = Xml2BeanConverter.generateCorpStaff(contentMap);
        CorpStaffVO dbStaffVO = corpStaffManageService
                .getCorpStaffByCorpIdAndUserId(corpVO.getCorpId(), changedStaffVO.getUserId());
        //  查找rsqId
        String rsqUserId = dbStaffVO.getRsqUserId();
        if(null == rsqUserId){
            return;
        }
        changedStaffVO.setRsqUserId(rsqUserId);
        List<CorpDeptVO> corpDeptVOList = null;
        if(null != changedStaffVO.getDepartment()){
            corpDeptVOList = corpDeptManageService
                    .listCorpDeptListByCorpIdAndDeptIdString(corpVO.getCorpId(), dbStaffVO.getDepartment());
        }
        rsqStaffService.pushAndUpdateStaff(corpVO, corpDeptVOList, changedStaffVO);
    }

    /**
     * 1  获取将要删除的用户userId
     * 2  contentMap中读取到rsqUserId
     * 3  根据rsqId push删除
     * @param corpId
     * @param contentMap
     */
    public void handleDeleteUser(String corpId, Map contentMap){
        CorpVO corpVO = corpManageService.getCorpByCorpId(corpId);
        CorpStaffVO changedStaffVO = Xml2BeanConverter.generateCorpStaff(contentMap);
        //  查找rsqId
        String rsqUserId = (String)contentMap.get("rsqUserId");
        if(null == rsqUserId){
            return;
        }
        changedStaffVO.setRsqUserId(rsqUserId);
        rsqStaffService.pushAndDeleteStaffFromTeam(corpVO, changedStaffVO);
    }

    /**
     * 1  根据corpId获取corp
     * 2  同步所有的corp、dept、staff
     * @param corpId
     */
    public void handleCreateCorp(String corpId){
        CorpVO corpVO = corpManageService.getCorpByCorpId(corpId);
        rsqCorpService.pushAndCreateCorpAll(corpVO);
    }
}
