package com.rishiqing.qywx.service.common.corp;

import com.rishiqing.qywx.service.model.corp.CorpDeptVO;

/**
 * @author Wallace Mao
 */
public interface CorpDeptManageService {

    /**
     * 根据corpId和deptId获取到CorpDept bean
     * @param corpId
     * @param deptId
     * @return
     */
    CorpDeptVO getCorpDeptByCorpIdAndDeptId(String corpId, Long deptId);
    /**
     * 保存corp信息
     * @param corpDeptVO
     */
    void saveOrUpdateCorpDept(CorpDeptVO corpDeptVO);
    /**
     * 根据corpId和deptId删除CorpDept
     * @param corpId
     * @param deptId
     */
    void deleteCorpDeptByCorpIdAndDeptId(String corpId, Long deptId);
}
