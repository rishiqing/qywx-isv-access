package com.rishiqing.qywx.dao.mapper.corp;

import com.rishiqing.qywx.dao.model.corp.CorpDeptDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("corpDeptDao")
public interface CorpDeptDao {
    /**
     * 根据corpId和deptId获取corpDept
     * @param corpId
     * @param deptId
     * @return
     */
    CorpDeptDO getCorpDeptByCorpIdAndDeptId(@Param("corpId") String corpId, @Param("deptId") Long deptId);

    /**
     * 根据corpId获取所有的deptId
     * @param corpId
     * @return
     */
    List<CorpDeptDO> listCorpDeptByCorpId(@Param("corpId") String corpId);

    /**
     * 根据corpId和parentId获取dept
     * @param corpId
     * @param parentId
     * @return
     */
    List<CorpDeptDO> listCorpDeptByCorpIdAndParentId(@Param("corpId") String corpId, @Param("parentId") Long parentId);

    /**
     * 保存或者更新corpDept
     * @param corpDeptDO
     */
    void saveOrUpdateCorpDept(CorpDeptDO corpDeptDO);

    /**
     * 根据corpId和deptId删除corpDept
     * @param corpId
     * @param deptId
     */
    void removeCorpDeptByCorpIdAndDeptId(@Param("corpId") String corpId, @Param("deptId") Long deptId);
}
