package com.rishiqing.qywx.service.event.listener.mq;

import com.rishiqing.common.exception.NotSupportedException;
import com.rishiqing.qywx.service.biz.rsq.RsqDeptService;
import com.rishiqing.qywx.service.biz.rsq.RsqStaffService;
import com.rishiqing.qywx.service.common.corp.CorpDeptManageService;
import com.rishiqing.qywx.service.common.corp.CorpManageService;
import com.rishiqing.qywx.service.common.corp.CorpStaffManageService;
import com.rishiqing.qywx.service.constant.CallbackChangeType;
import com.rishiqing.qywx.service.model.corp.CorpDeptVO;
import com.rishiqing.qywx.service.model.corp.CorpStaffVO;
import com.rishiqing.qywx.service.model.corp.CorpVO;
import com.rishiqing.qywx.service.util.http.converter.Xml2BeanConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.List;
import java.util.Map;

/**
 * 处理企业微信接收到的回调消息的mq listener
 * @author Wallace Mao
 * Date: 2018-02-10 14:20
 */
public class PushCorpCallbackMqListener implements MessageListener {
    private static final Logger logger = LoggerFactory.getLogger("SERVICE_EVENT_LISTENER_LOGGER");

    @Autowired
    private CorpManageService corpManageService;
    @Autowired
    private CorpDeptManageService corpDeptManageService;
    @Autowired
    private CorpStaffManageService corpStaffManageService;
    @Autowired
    private RsqDeptService rsqDeptService;
    @Autowired
    private RsqStaffService rsqStaffService;

    @Override
    public void onMessage(Message message) {
        MapMessage mapMessage = (MapMessage)message;
        try {
            String corpId = mapMessage.getString("corpId");
            CallbackChangeType type = CallbackChangeType.valueOf(mapMessage.getString("type"));
            Map contentMap = (Map)mapMessage.getObject("content");
            CorpVO corpVO = corpManageService.getCorpByCorpId(corpId);

            switch (type){
                case CREATE_PARTY:
                    handleCreateDept(corpVO, contentMap);
                    break;
                case UPDATE_PARTY:
                    handleUpdateDept(corpVO, contentMap);
                    break;
                case DELETE_PARTY:
                    handleDeleteDept(corpVO, contentMap);
                    break;
                case CREATE_USER:
                    handleCreateUser(corpVO, contentMap);
                    break;
                case UPDATE_USER:
                    handleUpdateUser(corpVO, contentMap);
                    break;
                case DELETE_USER:
                    handleDeleteUser(corpVO, contentMap);
                    break;
                case UPDATE_TAG:
                    throw new NotSupportedException("change type UPDATE_TAG not supported now");
                default:
                    break;
            }
        } catch (Exception e) {
            logger.error("error in push corpAll: ", e);
        }
    }

    /**
     * 处理创建部门
     * 1  获取需要创建的新部门的参数
     * 2  获取父部门的参数
     * 3  push创建
     * @param corpVO
     * @param contentMap
     */
    private void handleCreateDept(CorpVO corpVO, Map contentMap){
        CorpDeptVO changedDeptVO = Xml2BeanConverter.generateCorpDept(contentMap);
        CorpDeptVO parentDeptVO = corpDeptManageService
                .getCorpDeptByCorpIdAndDeptId(corpVO.getCorpId(), changedDeptVO.getParentId());
        rsqDeptService.pushAndCreateDept(corpVO, parentDeptVO, changedDeptVO);
    }

    /**
     * 1  获取有改变的部门属性
     * 2  获取数据库中的部门，并设置rsqId，推送时将根据rsqId进行识别
     * 3  如果改变的属性包括父部门，那么就设置父部门
     * 4  push更新
     * @param corpVO
     * @param contentMap
     */
    private void handleUpdateDept(CorpVO corpVO, Map contentMap){
        String corpId = corpVO.getCorpId();
        CorpDeptVO changedDeptVO = Xml2BeanConverter.generateCorpDept(contentMap);
        CorpDeptVO dbDeptVO = corpDeptManageService
                .getCorpDeptByCorpIdAndDeptId(corpId, changedDeptVO.getDeptId());
        changedDeptVO.setRsqId(dbDeptVO.getRsqId());
        CorpDeptVO parentDeptVO = null;
        if(null != changedDeptVO.getParentId()){
            parentDeptVO = corpDeptManageService
                    .getCorpDeptByCorpIdAndDeptId(corpId, dbDeptVO.getParentId());
        }
        rsqDeptService.pushAndUpdateDept(corpVO, parentDeptVO, changedDeptVO);
    }

    /**
     * 1  获取需要删除的部门的deptId
     * 2  根据deptId去数据库查rsqId
     * 3  push删除
     * @param corpVO
     * @param contentMap
     */
    private void handleDeleteDept(CorpVO corpVO, Map contentMap){
        CorpDeptVO changedDeptVO = Xml2BeanConverter.generateCorpDept(contentMap);
        CorpDeptVO dbDeptVO = corpDeptManageService
                .getCorpDeptByCorpIdAndDeptId(corpVO.getCorpId(), changedDeptVO.getDeptId());
        changedDeptVO.setRsqId(dbDeptVO.getRsqId());
        rsqDeptService.pushAndDeleteDept(corpVO, changedDeptVO);
    }

    /**
     * 1  获取需要新增的成员的参数
     * 2  查询获取成员的部门列表
     * 3  push新增成员
     * @param corpVO
     * @param contentMap
     */
    private void handleCreateUser(CorpVO corpVO, Map contentMap){
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
     * @param corpVO
     * @param contentMap
     */
    private void handleUpdateUser(CorpVO corpVO, Map contentMap){
        CorpStaffVO changedStaffVO = Xml2BeanConverter.generateCorpStaff(contentMap);
        CorpStaffVO dbStaffVO = corpStaffManageService
                .getCorpStaffByCorpIdAndUserId(corpVO.getCorpId(), changedStaffVO.getUserId());
        changedStaffVO.setRsqUserId(dbStaffVO.getRsqUserId());
        List<CorpDeptVO> corpDeptVOList = null;
        if(null != changedStaffVO.getDepartment()){
            corpDeptVOList = corpDeptManageService
                    .listCorpDeptListByCorpIdAndDeptIdString(corpVO.getCorpId(), dbStaffVO.getDepartment());
        }
        rsqStaffService.pushAndUpdateStaff(corpVO, corpDeptVOList, changedStaffVO);
    }

    /**
     * 1  获取将要删除的用户userId
     * 2  查询数据库获取该用户的rsqId
     * 3  根据rsqId push删除
     * @param corpVO
     * @param contentMap
     */
    private void handleDeleteUser(CorpVO corpVO, Map contentMap){
        CorpStaffVO changedStaffVO = Xml2BeanConverter.generateCorpStaff(contentMap);
        CorpStaffVO dbStaffVO = corpStaffManageService
                .getCorpStaffByCorpIdAndUserId(corpVO.getCorpId(), changedStaffVO.getUserId());
        changedStaffVO.setRsqUserId(dbStaffVO.getRsqUserId());
        rsqStaffService.pushAndDeleteStaffFromTeam(corpVO, changedStaffVO);
    }
}
