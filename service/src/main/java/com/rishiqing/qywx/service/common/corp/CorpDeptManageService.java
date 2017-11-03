package com.rishiqing.qywx.service.common.corp;

import com.rishiqing.qywx.service.model.corp.CorpDeptVO;

public interface CorpDeptManageService {
    /**
     * 保存corp信息
     * @param corpDeptVO
     */
    void saveOrUpdateCorpDept(CorpDeptVO corpDeptVO);
}
