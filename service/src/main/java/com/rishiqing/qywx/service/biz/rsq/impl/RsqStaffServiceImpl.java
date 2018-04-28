package com.rishiqing.qywx.service.biz.rsq.impl;

import com.rishiqing.common.exception.HttpException;
import com.rishiqing.common.exception.RsqSyncException;
import com.rishiqing.common.exception.RsqUpdateNotExistsException;
import com.rishiqing.common.model.RsqCommonUserVO;
import com.rishiqing.common.model.RsqTeamVO;
import com.rishiqing.common.util.http.HttpUtilRsqSync;
import com.rishiqing.qywx.service.biz.rsq.RsqStaffService;
import com.rishiqing.qywx.service.common.corp.CorpDeptManageService;
import com.rishiqing.qywx.service.common.corp.CorpStaffManageService;
import com.rishiqing.qywx.service.common.isv.GlobalSuite;
import com.rishiqing.qywx.service.common.rsq.RsqInfoManageService;
import com.rishiqing.qywx.service.model.corp.CorpDeptVO;
import com.rishiqing.qywx.service.model.corp.CorpStaffVO;
import com.rishiqing.qywx.service.model.corp.CorpVO;
import com.rishiqing.qywx.service.model.corp.helper.CorpConverter;
import com.rishiqing.qywx.service.model.corp.helper.CorpStaffConverter;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author Wallace Mao
 * Date: 2018-02-08 11:55
 */
public class RsqStaffServiceImpl implements RsqStaffService {

    @Autowired
    private GlobalSuite suite;
    @Autowired
    private HttpUtilRsqSync httpUtilRsqSync;
    @Autowired
    private CorpStaffManageService corpStaffManageService;
    @Autowired
    private CorpDeptManageService corpDeptManageService;
    @Autowired
    private RsqInfoManageService rsqInfoManageService;

    /**
     * 将企业corpVO中的成员同步到日事清
     * 由于用户在开通微应用的时候，选择的可见范围可能只有部分成员、部分部门，因此push的时候可能出现成员所属部门不存在的情况，这里采用这样的方案
     * 1  由于一个员工可能属于多个部门，遍历其所属的部门，如果存在就转换为rsqId，如果不存在就忽略。
     * 2  如果一个员工所属的部门的部门rsqId列表为空，那么就不设置该用户的所属部门，在日事清里表现为“未分配部门”；反之则设置相应的部门
     * @param corpVO
     */
    @Override
    public void pushAllCorpStaff(CorpVO corpVO) {
        List<CorpStaffVO> list = corpStaffManageService.listCorpStaffByCorpId(corpVO.getCorpId());
        List<CorpStaffVO> adminList = new ArrayList<CorpStaffVO>();

        for (CorpStaffVO corpStaffVO : list) {
            List<CorpDeptVO> deptList = corpDeptManageService.listCorpDeptListByCorpIdAndDeptIdString(corpVO.getCorpId(), corpStaffVO.getDepartment());
            pushStaff(corpVO, deptList, corpStaffVO);
            if(null != corpStaffVO.getAdminType() && corpStaffVO.getAdminType() != -1){
                adminList.add(corpStaffVO);
            }
        }

        //  提交管理员列表
        for(CorpStaffVO corpStaffVO : adminList){
            pushAndSetStaffAdmin(corpVO, corpStaffVO);
        }
    }

    private void pushStaff(CorpVO corpVO, List<CorpDeptVO> corpDeptVOList, CorpStaffVO corpStaffVO){
        if(null == corpStaffVO.getRsqUserId()){
            this.pushAndCreateStaff(corpVO, corpDeptVOList, corpStaffVO);
        }else{
            this.pushAndUpdateStaff(corpVO, corpDeptVOList, corpStaffVO);
        }
    }

    /**
     * 创建成员的逻辑顺序：
     * 1  先更新本地（有其他方法更新到本地）
     * 2  push到日事清成员（本方法完成，若失败则会重试）
     * 3  更新本地corp的rsqUserId/rsqUsername/rsqPassword（本方法完成，若失败则会重试）
     * @param corpVO
     * @param corpDeptVOList
     * @param corpStaffVO
     * @return
     */
    @Override
    public CorpStaffVO pushAndCreateStaff(CorpVO corpVO, List<CorpDeptVO> corpDeptVOList, CorpStaffVO corpStaffVO) {
        //  如果rsqId存在，那么将不做任何处理
        if(null != corpStaffVO.getRsqUserId()){
            return corpStaffVO;
        }
        RsqTeamVO team = CorpConverter.corpVO2RsqTeamVO(corpVO);
        RsqCommonUserVO user = CorpStaffConverter.corpStaffVO2RsqCommonUserVO(corpVO, corpDeptVOList, corpStaffVO);
        //  自动生成用户名和密码
        String password = generateRsqPassword(suite.getRsqAppName());
        user.setUsername(generateRsqUsername(suite.getRsqAppName()));
        user.setPassword(password);

        user = httpUtilRsqSync.createUser(suite.getRsqAppName(), suite.getRsqAppToken(), team, user);
        corpStaffVO.setRsqUserId(String.valueOf(user.getId()));
        corpStaffVO.setRsqUsername(user.getUsername());
        corpStaffVO.setRsqPassword(password);
        rsqInfoManageService.updateCorpStaffRsqInfo(corpStaffVO);
        return corpStaffVO;
    }

    /**
     * 更新用户的逻辑顺序：
     * 1. 更新到本地（由上层完成）
     * 2. 提交更新到日事清用户（本方法完成，若失败则会重试）
     * 注意：
     * 1. 如果department未发生变化，那么传入null，如果发生了变化，那么传入新的corpDeptVOList
     * 2. corpStaffVO中只应该包括需要修改的数据. 如果只修改了realName，那么应该只传入realName
     * @param corpVO
     * @param corpDeptVOList
     * @param corpStaffVO
     * @return
     */
    @Override
    public CorpStaffVO pushAndUpdateStaff(CorpVO corpVO, List<CorpDeptVO> corpDeptVOList, CorpStaffVO corpStaffVO) {
        RsqTeamVO team = CorpConverter.corpVO2RsqTeamVO(corpVO);
        RsqCommonUserVO user = CorpStaffConverter.corpStaffVO2RsqCommonUserVO(corpVO, corpDeptVOList, corpStaffVO);
        //  如果rsqId不存在，那么抛出异常，下次进行重试
        if(null == corpStaffVO.getRsqUserId()){
            throw new RsqUpdateNotExistsException("corpStaffVO.getRsqUserId not exists: corpId: " + corpStaffVO.getCorpId() + ", deptId: " + corpStaffVO.getUserId());
        }
        httpUtilRsqSync.updateUser(suite.getRsqAppName(), suite.getRsqAppToken(), team, user);
        return corpStaffVO;
    }

    /**
     * 删除部门的逻辑顺序
     * 1. 删除本地日事清部门（由上层方法完成）,删除后仍然需要传入corpStaffVO对象，该对象包含rsqUserId
     * 2. push提交删除到日事清（本方法完成，如果失败则会重试）
     * @param corpVO
     * @param corpStaffVO
     * @return
     */
    @Override
    public CorpStaffVO pushAndDeleteStaffFromTeam(CorpVO corpVO, CorpStaffVO corpStaffVO) {
        RsqTeamVO team = CorpConverter.corpVO2RsqTeamVO(corpVO);
        RsqCommonUserVO user = CorpStaffConverter.corpStaffVO2RsqCommonUserVO(corpVO, null, corpStaffVO);
        //  如果rsqId不存在，那么抛出异常，下次进行重试
        if(null == corpStaffVO.getRsqUserId()){
            throw new RsqUpdateNotExistsException("corpStaffVO.getRsqUserId not exists: corpId: " + corpStaffVO.getCorpId() + ", deptId: " + corpStaffVO.getUserId());
        }
        httpUtilRsqSync.userLeaveTeam(suite.getRsqAppName(), suite.getRsqAppToken(), team, user);
        return corpStaffVO;
    }

    @Override
    public CorpStaffVO pushAndSetStaffAdmin(CorpVO corpVO, CorpStaffVO corpStaffVO) {
        RsqTeamVO team = CorpConverter.corpVO2RsqTeamVO(corpVO);
        RsqCommonUserVO user = CorpStaffConverter.corpStaffVO2RsqCommonUserVO(corpVO, null, corpStaffVO);
        //  如果rsqId不存在，那么抛出异常，下次进行重试
        if(null == corpStaffVO.getRsqUserId()){
            throw new RsqUpdateNotExistsException("corpStaffVO.getRsqUserId not exists: corpId: " + corpStaffVO.getCorpId() + ", deptId: " + corpStaffVO.getUserId());
        }
        httpUtilRsqSync.setUserAdmin(suite.getRsqAppName(), suite.getRsqAppToken(), team, user);
        return corpStaffVO;
    }

    private String generateRsqUsername(String appName){
        StringBuffer sb = new StringBuffer();
        sb.append(RandomStringUtils.randomAlphabetic(5))
                .append("_")
                .append(new Date().getTime())
                .append("@")
                .append(appName)
                .append(".rishiqing.com");
        return  sb.toString();
    }

    private String generateRsqPassword(String username){
        return RandomStringUtils.randomAlphabetic(6);
    }
}
