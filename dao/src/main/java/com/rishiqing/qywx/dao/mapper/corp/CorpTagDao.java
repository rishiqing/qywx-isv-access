package com.rishiqing.qywx.dao.mapper.corp;

import com.rishiqing.qywx.dao.model.corp.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("corpTagDao")
public interface CorpTagDao {
    /**
     * 根据corpId和tagId，获取tag
     * @param corpId
     * @param tagId
     * @return
     */
    CorpTagDO getCorpTagByCorpIdAndTagId(@Param("corpId") String corpId, @Param("tagId") String tagId);

    /**
     * 保存或更新corpTag
     * @param corpTagDO
     */
    void saveOrUpdateCorpTag(CorpTagDO corpTagDO);

    /**
     * 删除
     * @param corpId
     * @param tagId
     */
    void removeCorpTagByCorpIdAndTagId(@Param("corpId") String corpId, @Param("tagId") String tagId);

    /**
     * 获取corpId和tagId下的所有corpTagCorpStaff
     * @param corpId
     * @param tagId
     * @return
     */
    List<CorpTagCorpStaffDO> listCorpTagCorpStaffByCorpIdAndTagId(@Param("corpId") String corpId, @Param("tagId") Long tagId);

    /**
     * 获取corpId、tagId下指定userId的CorpTagCorpStaff
     * @param corpId
     * @param tagId
     * @param userId
     * @return
     */
    CorpTagCorpStaffDO getCorpTagCorpStaffByCorpIdAndTagIdAndUserId(
            @Param("corpId") String corpId,
            @Param("tagId") Long tagId,
            @Param("userId") String userId
    );

    /**
     * 保存CorpTagCorpStaff
     * @param corpTagCorpStaffDO
     */
    void saveOrUpdateCorpTagCorpStaff(CorpTagCorpStaffDO corpTagCorpStaffDO);

    /**
     * 删除corpId、tagId下指定userId的CorpTagCorpStaff
     * @param corpId
     * @param tagId
     * @param userId
     */
    void removeCorpTagCorpStaffByCorpIdAndTagIdAndUserId(
            @Param("corpId") String corpId,
            @Param("tagId") Long tagId,
            @Param("userId") String userId
    );

    /**
     * 获取corpId和tagId下的所有corpTagCorpDept
     * @param corpId
     * @param tagId
     * @return
     */
    List<CorpTagCorpDeptDO> listCorpTagCorpDeptByCorpIdAndTagId(@Param("corpId") String corpId, @Param("tagId") Long tagId);

    /**
     * 获取corpId、tagId下指定userId的CorpTagCorpStaff
     * @param corpId
     * @param tagId
     * @param deptId
     * @return
     */
    CorpTagCorpDeptDO getCorpTagCorpDeptByCorpIdAndTagIdAndDeptId(
            @Param("corpId") String corpId,
            @Param("tagId") Long tagId,
            @Param("deptId") String deptId
    );

    /**
     * 保存CorpTagCorpDept
     * @param corpTagCorpDeptDO
     */
    void saveOrUpdateCorpTagCorpDept(CorpTagCorpDeptDO corpTagCorpDeptDO);

    /**
     * 删除corpId、tagId下指定userId的CorpTagCorpDept
     * @param corpId
     * @param tagId
     * @param deptId
     */
    void removeCorpTagCorpDeptByCorpIdAndTagIdAndDeptId(
            @Param("corpId") String corpId,
            @Param("tagId") Long tagId,
            @Param("deptId") String deptId
    );
}
