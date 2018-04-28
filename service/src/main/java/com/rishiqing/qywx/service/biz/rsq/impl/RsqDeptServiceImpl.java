package com.rishiqing.qywx.service.biz.rsq.impl;

import com.rishiqing.common.exception.RsqSyncException;
import com.rishiqing.common.exception.RsqUpdateNotExistsException;
import com.rishiqing.common.model.RsqDepartmentVO;
import com.rishiqing.common.model.RsqTeamVO;
import com.rishiqing.common.util.http.HttpUtilRsqSync;
import com.rishiqing.qywx.service.biz.rsq.RsqDeptService;
import com.rishiqing.qywx.service.common.corp.CorpDeptManageService;
import com.rishiqing.qywx.service.common.isv.GlobalSuite;
import com.rishiqing.qywx.service.common.rsq.RsqInfoManageService;
import com.rishiqing.qywx.service.model.corp.CorpDeptVO;
import com.rishiqing.qywx.service.model.corp.CorpVO;
import com.rishiqing.qywx.service.model.corp.helper.CorpConverter;
import com.rishiqing.qywx.service.model.corp.helper.CorpDeptConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-02-08 11:55
 */
public class RsqDeptServiceImpl implements RsqDeptService {

    @Autowired
    private GlobalSuite suite;
    @Autowired
    private HttpUtilRsqSync httpUtilRsqSync;
    @Autowired
    private CorpDeptManageService corpDeptManageService;
    @Autowired
    private RsqInfoManageService rsqInfoManageService;

    /**
     * 将corpVO企业中的所有部门同步到日事清。
     * 由于用户在开通微应用的时候，选择的可见范围可能只有部分部门，因此push的时候可能看不到企业完整的部门树，这里采用这样的方案
     * 1  读取corpVO下的所有部门
     * 2  将所有部门按照父子关系重新建立组织结构，有父部门的按照父子关系建立；父部门没有获取到的统一放到根部门下面
     * 3  针对新建立的树形结构，对其中的每个部门，递归同步：有rsqId的走更新，无rsqId的走新建
     * @param corpVO
     */
    @Override
    public void pushAllCorpDept(CorpVO corpVO) {
        List<CorpDeptVO> treeList = this.makeDeptTree(corpVO);
        for(CorpDeptVO deptVO : treeList){
            pushAndCreateRecursiveDept(corpVO, null, deptVO);
        }

//        Long rootDeptId = 1L;
//        CorpDeptVO rootDept = corpDeptManageService.getCorpDeptByCorpIdAndDeptId(corpVO.getCorpId(), rootDeptId);
//        pushAndCreateRecursiveDept(corpVO, null, rootDept);
    }

    /**
     * 将corpVO下的所有部门组合成一个列表，规则如下：
     * 1  父部门不存在的，那么就直接放到list中
     * 2  父部门存在的，放到父部门的childDept的list中
     * @param corpVO
     * @return
     */
    private List<CorpDeptVO> makeDeptTree(CorpVO corpVO){
        List<CorpDeptVO> orgList = corpDeptManageService.listCorpDeptListByCorpId(corpVO.getCorpId());
        List<CorpDeptVO> resultList = new ArrayList<>(orgList.size());

        for(CorpDeptVO corpDeptVO : orgList){
            CorpDeptVO parent = findByDeptId(orgList, corpDeptVO.getParentId());
            if(parent == null){
                resultList.add(corpDeptVO);
            }else{
                List<CorpDeptVO> childList = parent.getChildDept();
                if(childList == null){
                    childList = new ArrayList<>();
                }
                childList.add(corpDeptVO);
                parent.setChildDept(childList);
            }
        }

        return resultList;
    }

    /**
     * 从list中返回deptId为deptId的CorpDeptVO对象
     * @param list
     * @return
     */
    private CorpDeptVO findByDeptId(List<CorpDeptVO> list, Long deptId){
        if (deptId == null){
            return null;
        }
        for(CorpDeptVO deptVO : list){
            if(deptId.equals(deptVO.getDeptId())){
                return deptVO;
            }
        }
        return null;
    }

    /**
     * 将corpVo下该corpDeptVO下的所有部门递归创建，用于在首次开通应用同步的时候同步所有部门
     * @param corpVO
     * @param corpDeptVO
     */
    private void pushAndCreateRecursiveDept(CorpVO corpVO, CorpDeptVO parentCorpDeptVO, CorpDeptVO corpDeptVO) {

        pushDept(corpVO, parentCorpDeptVO, corpDeptVO);

//        List<CorpDeptVO> list =  corpDeptManageService.listCorpDeptListByCorpIdAndParentId(corpDeptVO.getCorpId(), corpDeptVO.getDeptId());
        List<CorpDeptVO> list = corpDeptVO.getChildDept();
        if(null == list){
            return;
        }
        for (CorpDeptVO subDept : list) {
            pushAndCreateRecursiveDept(corpVO, corpDeptVO, subDept);
        }
    }

    private void pushDept(CorpVO corpVO, CorpDeptVO parentCorpDeptVO, CorpDeptVO corpDeptVO){
        if(null == corpDeptVO.getRsqId()){
            this.pushAndCreateDept(corpVO, parentCorpDeptVO, corpDeptVO);
        }else{
            this.pushAndUpdateDept(corpVO, parentCorpDeptVO, corpDeptVO);
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
    public CorpDeptVO pushAndCreateDept(CorpVO corpVO, CorpDeptVO parentCorpDeptVO, CorpDeptVO corpDeptVO) {
        //  如果rsqId存在，那么将不做任何处理
        if(null != corpDeptVO.getRsqId()){
            return corpDeptVO;
        }
        RsqTeamVO team = CorpConverter.corpVO2RsqTeamVO(corpVO);
        RsqDepartmentVO departmentVO = CorpDeptConverter.corpDeptVO2RsqDepartment(corpVO, parentCorpDeptVO, corpDeptVO);
        departmentVO = httpUtilRsqSync.createDepartment(suite.getRsqAppName(), suite.getRsqAppToken(), team, departmentVO);
        corpDeptVO.setRsqId(String.valueOf(departmentVO.getId()));
        rsqInfoManageService.updateCorpDeptRsqInfo(corpDeptVO);
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
    public CorpDeptVO pushAndUpdateDept(CorpVO corpVO, CorpDeptVO parentCorpDeptVO, CorpDeptVO corpDeptVO) {
        RsqTeamVO team = CorpConverter.corpVO2RsqTeamVO(corpVO);
        RsqDepartmentVO departmentVO = CorpDeptConverter.corpDeptVO2RsqDepartment(corpVO, parentCorpDeptVO, corpDeptVO);
        //  如果rsqId不存在，那么抛出异常，下次进行重试
        if(null == corpDeptVO.getRsqId()){
            throw new RsqUpdateNotExistsException("corpDeptVO.getRsqId not exists: corpId: " + corpDeptVO.getCorpId() + ", deptId: " + corpDeptVO.getDeptId());
        }
        httpUtilRsqSync.updateDepartment(suite.getRsqAppName(), suite.getRsqAppToken(), team, departmentVO);
        return corpDeptVO;
    }

    /**
     * 删除部门的逻辑顺序
     * 1. 删除本地日事清部门（上层方法完成），删除后仍然需要传入corpDeptVO对象，该对象包含rsqId
     * 2. push提交删除到日事清（本方法完成，如果失败则会重试）
     * @param corpVO
     * @param corpDeptVO
     * @return
     */
    @Override
    public CorpDeptVO pushAndDeleteDept(CorpVO corpVO, CorpDeptVO corpDeptVO) {
        RsqTeamVO team = CorpConverter.corpVO2RsqTeamVO(corpVO);
        RsqDepartmentVO departmentVO = CorpDeptConverter.corpDeptVO2RsqDepartment(corpVO, null, corpDeptVO);
        //  如果rsqId不存在，那么抛出异常，下次进行重试
        if(null == corpDeptVO.getRsqId()){
            throw new RsqUpdateNotExistsException("corpDeptVO.getRsqId not exists: corpId: " + corpDeptVO.getCorpId() + ", deptId: " + corpDeptVO.getDeptId());
        }
        httpUtilRsqSync.deleteDepartment(suite.getRsqAppName(), suite.getRsqAppToken(), team, departmentVO);
        return corpDeptVO;
    }
}
