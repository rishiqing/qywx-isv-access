package com.rishiqing.qywx.service.common.corp;


import com.rishiqing.qywx.service.model.corp.CorpStaffVO;

public interface CorpStaffManageService {
    /**
     * 保存corp信息
     * @param corpStaffVO
     */
    void saveOrUpdateCorpStaff(CorpStaffVO corpStaffVO);
}
