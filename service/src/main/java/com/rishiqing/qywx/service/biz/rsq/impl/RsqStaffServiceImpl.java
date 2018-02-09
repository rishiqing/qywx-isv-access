package com.rishiqing.qywx.service.biz.rsq.impl;

import com.rishiqing.common.exception.RsqSyncException;
import com.rishiqing.common.exception.RsqUpdateNotExistsException;
import com.rishiqing.common.model.RsqCommonUserVO;
import com.rishiqing.common.model.RsqDepartmentVO;
import com.rishiqing.common.model.RsqTeamVO;
import com.rishiqing.common.util.http.HttpUtilRsqSync;
import com.rishiqing.qywx.service.biz.rsq.RsqStaffService;
import com.rishiqing.qywx.service.common.corp.CorpDeptManageService;
import com.rishiqing.qywx.service.common.corp.CorpStaffManageService;
import com.rishiqing.qywx.service.common.isv.SuiteManageService;
import com.rishiqing.qywx.service.model.corp.CorpDeptVO;
import com.rishiqing.qywx.service.model.corp.CorpStaffVO;
import com.rishiqing.qywx.service.model.corp.CorpVO;
import com.rishiqing.qywx.service.model.corp.helper.CorpConverter;
import com.rishiqing.qywx.service.model.corp.helper.CorpDeptConverter;
import com.rishiqing.qywx.service.model.corp.helper.CorpStaffConverter;
import com.rishiqing.qywx.service.model.isv.SuiteVO;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author Wallace Mao
 * Date: 2018-02-08 11:55
 */
public class RsqStaffServiceImpl implements RsqStaffService {
    private static final Logger logger = LoggerFactory.getLogger("SERVICE_CORP_PUSH_RSQ_LOGGER");

    @Autowired
    private Map isvGlobal;
    @Autowired
    private SuiteManageService suiteManageService;
    @Autowired
    private HttpUtilRsqSync httpUtilRsqSync;
    @Autowired
    private CorpStaffManageService corpStaffManageService;
    @Autowired
    private CorpDeptManageService corpDeptManageService;

    private SuiteVO suite;

    @PostConstruct
    private void init(){
        //  读取套件基本信息
        String suiteKey = (String)isvGlobal.get("suiteKey");
        this.suite = suiteManageService.getSuiteInfoByKey(suiteKey);
    }

    @Override
    public void pushAndCreateAllCorpStaff(CorpVO corpVO){
        List<CorpStaffVO> list = corpStaffManageService.listCorpStaffByCorpId(corpVO.getCorpId());
        List<CorpStaffVO> adminList = new ArrayList<>();

        for (CorpStaffVO corpStaffVO : list) {
            List<CorpDeptVO> deptList = corpDeptIds2CorpDeptVO(corpVO.getCorpId(), corpStaffVO.getDepartment());
            pushAndCreateStaff(corpVO, deptList, corpStaffVO);
            if(corpStaffVO.getAdmin()){
                adminList.add(corpStaffVO);
            }
        }

        //  提交管理员列表
        for(CorpStaffVO corpStaffVO : adminList){
            pushAndSetStaffAdmin(corpVO, corpStaffVO);
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
        try {
            //  自动生成用户名和密码
            user.setUsername(generateRsqUsername(this.suite.getRsqAppName()));
            user.setPassword(generateRsqPassword(this.suite.getRsqAppName()));

            user = httpUtilRsqSync.createUser(this.suite.getRsqAppName(), this.suite.getRsqAppToken(), team, user);
            corpStaffVO.setRsqUserId(String.valueOf(user.getId()));
            corpStaffVO.setRsqUsername(user.getUsername());
            corpStaffVO.setRsqPassword(user.getPassword());
            corpStaffManageService.saveOrUpdateCorpStaff(corpStaffVO);
        } catch (RsqSyncException e) {
            logger.error("push to create rishiqing department error: ", e);
            //TODO 加入队列做重试
        }
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
        try {
            //  如果rsqId不存在，那么抛出异常，下次进行重试
            if(null == corpStaffVO.getRsqUserId()){
                throw new RsqUpdateNotExistsException("corpStaffVO.getRsqUserId not exists: corpId: " + corpStaffVO.getCorpId() + ", deptId: " + corpStaffVO.getUserId());
            }
//            corpDeptManageService.saveOrUpdateCorpDept(corpDeptVO);
            httpUtilRsqSync.updateUser(this.suite.getRsqAppName(), this.suite.getRsqAppToken(), team, user);
        } catch (RsqSyncException e) {
            logger.error("push to create rishiqing department error: ", e);
            //TODO 加入队列做重试
        } catch (RsqUpdateNotExistsException e) {
            logger.error("update commonUser, but id not exists. ", e);
            //TODO 重新create，然后再做更新
        }
        return corpStaffVO;
    }

    @Override
    public CorpStaffVO pushAndDeleteStaffFromTeam(CorpVO corpVO, CorpStaffVO corpStaffVO) {
        RsqTeamVO team = CorpConverter.corpVO2RsqTeamVO(corpVO);
        RsqCommonUserVO user = CorpStaffConverter.corpStaffVO2RsqCommonUserVO(corpVO, null, corpStaffVO);
        try {
            //  如果rsqId不存在，那么抛出异常，下次进行重试
            if(null == corpStaffVO.getRsqUserId()){
                throw new RsqUpdateNotExistsException("corpStaffVO.getRsqUserId not exists: corpId: " + corpStaffVO.getCorpId() + ", deptId: " + corpStaffVO.getUserId());
            }
            httpUtilRsqSync.userLeaveTeam(this.suite.getRsqAppName(), this.suite.getRsqAppToken(), team, user);
            corpStaffManageService.deleteCorpStaffByCorpIdAndUserId(corpVO.getCorpId(), corpStaffVO.getUserId());
        } catch (RsqSyncException e) {
            logger.error("push to create rishiqing department error: ", e);
            //TODO 加入队列做重试
        } catch (RsqUpdateNotExistsException e) {
            logger.error("delete commonUser, but id not exists. ", e);
            //TODO 重新create，然后再做删除
        }
        return corpStaffVO;
    }

    @Override
    public CorpStaffVO pushAndSetStaffAdmin(CorpVO corpVO, CorpStaffVO corpStaffVO){
        RsqTeamVO team = CorpConverter.corpVO2RsqTeamVO(corpVO);
        RsqCommonUserVO user = CorpStaffConverter.corpStaffVO2RsqCommonUserVO(corpVO, null, corpStaffVO);
        try {
            //  如果rsqId不存在，那么抛出异常，下次进行重试
            if(null == corpStaffVO.getRsqUserId()){
                throw new RsqUpdateNotExistsException("corpStaffVO.getRsqUserId not exists: corpId: " + corpStaffVO.getCorpId() + ", deptId: " + corpStaffVO.getUserId());
            }
            httpUtilRsqSync.setUserAdmin(this.suite.getRsqAppName(), this.suite.getRsqAppToken(), team, user);
        } catch (RsqSyncException e) {
            logger.error("push to create rishiqing department error: ", e);
            //TODO 加入队列做重试
        } catch (RsqUpdateNotExistsException e) {
            logger.error("delete commonUser, but id not exists. ", e);
            //TODO 重新create，然后再做删除
        }
        return corpStaffVO;
    }

    private List<CorpDeptVO> corpDeptIds2CorpDeptVO(String corpId, String ids){
        String strIds = ids.replaceAll("\\[", "")
                .replaceAll("\\]", "");
        String[] arr = strIds.split("\\,");
        List<CorpDeptVO> list = new ArrayList<>(arr.length);
        for(String strId : arr){
            if(null != strId && !"".equals(strId)){
                CorpDeptVO corpDeptVO = corpDeptManageService.getCorpDeptByCorpIdAndDeptId(corpId, Long.valueOf(strId));
                list.add(corpDeptVO);
            }
        }
        return list;
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
