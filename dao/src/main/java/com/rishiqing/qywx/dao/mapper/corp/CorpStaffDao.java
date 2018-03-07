package com.rishiqing.qywx.dao.mapper.corp;

import com.rishiqing.qywx.dao.model.corp.CorpStaffDO;
import com.rishiqing.qywx.dao.model.corp.CorpStaffIdsDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("corpStaffDao")
public interface CorpStaffDao {
    /**
     * 根据corpId和userId获取corpStaff
     * @param corpId
     * @param userId
     * @return
     */
    CorpStaffDO getCorpStaffByCorpIdAndUserId(@Param("corpId") String corpId, @Param("userId") String userId);

    /**
     * 根据corpId获取所有的corpStaff
     * @param corpId
     * @return
     */
    List<CorpStaffDO> listCorpStaffByCorpId(@Param("corpId") String corpId);
    /**
     * 保存或者更新corpDept
     * @param corpStaffDO
     */
    void saveOrUpdateCorpStaff(CorpStaffDO corpStaffDO);

    /**
     * 根据corpId和deptId删除corpDept
     * @param corpId
     * @param userId
     */
    void removeCorpStaffByCorpIdAndUserId(@Param("corpId") String corpId, @Param("userId") String userId);

    /**
     * 根据staff的rsqId获取到userId
     * @param rsqIds
     * @return
     */
    public List<CorpStaffIdsDO> getUserIdFromRsqId(
            @Param("corpId") String corpId,
            @Param("rsqIds") String[] rsqIds);
    /**
     * 根据staff的rsqId获取到userId
     * @param userIds
     * @return
     */
    public List<CorpStaffIdsDO> getRsqIdFromUserId(
            @Param("corpId") String corpId,
            @Param("userIds") String[] userIds);
}
