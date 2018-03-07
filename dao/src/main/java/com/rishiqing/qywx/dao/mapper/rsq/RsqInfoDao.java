package com.rishiqing.qywx.dao.mapper.rsq;

import com.rishiqing.qywx.dao.model.corp.CorpDO;
import com.rishiqing.qywx.dao.model.corp.CorpDeptDO;
import com.rishiqing.qywx.dao.model.corp.CorpStaffDO;
import org.springframework.stereotype.Repository;

@Repository("rsqInfoDao")
public interface RsqInfoDao {

    /**
     * 更新corp的rsqInfo日事清相关的字段，即rsqId
     * @param corpDO
     */
    void updateCorpRsqInfo(CorpDO corpDO);

    /**
     * 更新corpDept相关的日事清字段，即rsqId
     * @param corpDeptDO
     */
    void updateCorpDeptRsqInfo(CorpDeptDO corpDeptDO);

    /**
     * 更新corpStaff相关的日事清字段，即rsqUserId/rsqUsername/rsqPassword/rsqToken
     * @param corpStaffDO
     */
    void updateCorpStaffRsqInfo(CorpStaffDO corpStaffDO);
}
