package com.rishiqing.qywx.service.biz.rsq.impl;

import com.rishiqing.common.exception.RsqSyncException;
import com.rishiqing.common.exception.RsqUpdateNotExistsException;
import com.rishiqing.common.model.RsqDepartmentVO;
import com.rishiqing.common.model.RsqTeamVO;
import com.rishiqing.common.util.http.HttpUtilRsqSync;
import com.rishiqing.qywx.service.biz.rsq.RsqDeptService;
import com.rishiqing.qywx.service.common.corp.CorpDeptManageService;
import com.rishiqing.qywx.service.common.isv.GlobalSuite;
import com.rishiqing.qywx.service.model.corp.CorpDeptVO;
import com.rishiqing.qywx.service.model.corp.CorpVO;
import com.rishiqing.qywx.service.model.corp.helper.CorpConverter;
import com.rishiqing.qywx.service.model.corp.helper.CorpDeptConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-02-08 11:55
 */
public class RsqDeptServiceImpl implements RsqDeptService {
    private static final Logger logger = LoggerFactory.getLogger("SERVICE_CORP_PUSH_RSQ_LOGGER");

    @Autowired
    private GlobalSuite suite;
    @Autowired
    private HttpUtilRsqSync httpUtilRsqSync;
    @Autowired
    private CorpDeptManageService corpDeptManageService;

    /**
     * 找到corpVO中顶层的部门，调用pushAndCreateRecursiveDept方法进行递归创建
     * @param corpVO
     */
    @Override
    public void pushAndCreateAllCorpDept(CorpVO corpVO){
        Long rootDeptId = 1L;
        CorpDeptVO rootDept = corpDeptManageService.getCorpDeptByCorpIdAndDeptId(corpVO.getCorpId(), rootDeptId);
        pushAndCreateRecursiveDept(corpVO, null, rootDept);
    }

    /**
     * 将corpVo下该corpDeptVO下的所有部门递归创建，用于在首次开通应用同步的时候同步所有部门
     * @param corpVO
     * @param corpDeptVO
     */
    private void pushAndCreateRecursiveDept(CorpVO corpVO, CorpDeptVO parentCorpDeptVO, CorpDeptVO corpDeptVO){

        pushAndCreateDept(corpVO, parentCorpDeptVO, corpDeptVO);

        List<CorpDeptVO> list =  corpDeptManageService.listCorpDeptListByCorpIdAndParentId(corpDeptVO.getCorpId(), corpDeptVO.getDeptId());
        for (CorpDeptVO subDept : list) {
            pushAndCreateRecursiveDept(corpVO, corpDeptVO, subDept);
        }
    }

    /**
     * 创建部门的逻辑顺序：
     * 1  先更新本地（有其他方法更新到本地）
     * 2  push到日事清部门（本方法完成，若失败则会重试）
     * 3  更新本地corp的rsqId（本方法完成，若失败则会重试）
     * @param corpVO
     * @param parentCorpDeptVO
     * @param corpDeptVO
     * @return
     */
    @Override
    public CorpDeptVO pushAndCreateDept(CorpVO corpVO, CorpDeptVO parentCorpDeptVO, CorpDeptVO corpDeptVO){
        //  如果rsqId存在，那么将不做任何处理
        if(null != corpDeptVO.getRsqId()){
            return corpDeptVO;
        }
        RsqTeamVO team = CorpConverter.corpVO2RsqTeamVO(corpVO);
        RsqDepartmentVO departmentVO = CorpDeptConverter.corpDeptVO2RsqDepartment(corpVO, parentCorpDeptVO, corpDeptVO);
        try {
//            corpDeptManageService.saveOrUpdateCorpDept(corpDeptVO);
            departmentVO = httpUtilRsqSync.createDepartment(suite.getRsqAppName(), suite.getRsqAppToken(), team, departmentVO);
            corpDeptVO.setRsqId(String.valueOf(departmentVO.getId()));
            corpDeptManageService.saveOrUpdateCorpDept(corpDeptVO);
        } catch (RsqSyncException e) {
            logger.error("push to create rishiqing department error: ", e);
            //TODO 加入队列做重试
        }
        return corpDeptVO;
    }

    /**
     * 更新部门的逻辑顺序：
     * 1. 更新到本地（由上层完成）
     * 2. 提交更新到日事清部门（本方法完成，若失败则会重试）
     * 注意：
     * 1. 如果parentCorpDeptVO未发生变化，那么传入null，如果发生了变化，那么传入新的parentCorpDeptVO
     * 2. corpDeptVO中只应该包括需要修改的数据. 如果只修改了name，那么应该只传入name
     * @param corpVO
     * @param parentCorpDeptVO
     * @param corpDeptVO
     * @return
     */
    @Override
    public CorpDeptVO pushAndUpdateDept(CorpVO corpVO, CorpDeptVO parentCorpDeptVO, CorpDeptVO corpDeptVO){
        RsqTeamVO team = CorpConverter.corpVO2RsqTeamVO(corpVO);
        RsqDepartmentVO departmentVO = CorpDeptConverter.corpDeptVO2RsqDepartment(corpVO, parentCorpDeptVO, corpDeptVO);
        try {
            //  如果rsqId不存在，那么抛出异常，下次进行重试
            if(null == corpDeptVO.getRsqId()){
                throw new RsqUpdateNotExistsException("corpDeptVO.getRsqId not exists: corpId: " + corpDeptVO.getCorpId() + ", deptId: " + corpDeptVO.getDeptId());
            }
//            corpDeptManageService.saveOrUpdateCorpDept(corpDeptVO);
            httpUtilRsqSync.updateDepartment(suite.getRsqAppName(), suite.getRsqAppToken(), team, departmentVO);
        } catch (RsqSyncException e) {
            logger.error("push to create rishiqing department error: ", e);
            //TODO 加入队列做重试
        } catch (RsqUpdateNotExistsException e) {
            logger.error("update department, but id not exists. ", e);
            //TODO 重新create，然后再做更新
        }
        return corpDeptVO;
    }

    /**
     * 删除部门的逻辑顺序
     * 1. push提交删除到日事清（本方法完成，如果失败则会重试）
     * 2. 删除本地日事清部门（本方法完成）
     * @param corpVO
     * @param corpDeptVO
     * @return
     */
    @Override
    public CorpDeptVO pushAndDeleteDept(CorpVO corpVO, CorpDeptVO corpDeptVO) {
        RsqTeamVO team = CorpConverter.corpVO2RsqTeamVO(corpVO);
        RsqDepartmentVO departmentVO = CorpDeptConverter.corpDeptVO2RsqDepartment(corpVO, null, corpDeptVO);
        try {
            //  如果rsqId不存在，那么抛出异常，下次进行重试
            if(null == corpDeptVO.getRsqId()){
                throw new RsqUpdateNotExistsException("corpDeptVO.getRsqId not exists: corpId: " + corpDeptVO.getCorpId() + ", deptId: " + corpDeptVO.getDeptId());
            }
            httpUtilRsqSync.deleteDepartment(suite.getRsqAppName(), suite.getRsqAppToken(), team, departmentVO);
            corpDeptManageService.deleteCorpDeptByCorpIdAndDeptId(corpVO.getCorpId(), corpDeptVO.getDeptId());
        } catch (RsqSyncException e) {
            logger.error("push to create rishiqing department error: ", e);
            //TODO 加入队列做重试
        } catch (RsqUpdateNotExistsException e) {
            logger.error("delete department, but id not exists. ", e);
            //TODO 重新create，然后再做删除
        }
        return corpDeptVO;
    }
}
