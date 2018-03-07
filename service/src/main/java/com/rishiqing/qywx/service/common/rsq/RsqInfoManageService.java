package com.rishiqing.qywx.service.common.rsq;

import com.rishiqing.qywx.service.model.corp.CorpDeptVO;
import com.rishiqing.qywx.service.model.corp.CorpStaffVO;
import com.rishiqing.qywx.service.model.corp.CorpVO;

/**
 * @author Wallace Mao
 * Date: 2018-02-27 17:56
 */
public interface RsqInfoManageService {
    /**
     * 只更新日事清相关的字段rsqId
     * @param corpVO
     */
    void updateCorpRsqInfo(CorpVO corpVO);

    /**
     * 更新日事清相关的字段rsqId
     * @param corpDeptVO
     */
    void updateCorpDeptRsqInfo(CorpDeptVO corpDeptVO);

    /**
     * 更新日事清相关的字段，rsqUserId/rsqUsername/RsqPassword/RsqLoginToken
     * @param corpStaffVO
     */
    void updateCorpStaffRsqInfo(CorpStaffVO corpStaffVO);
}
