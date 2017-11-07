package com.rishiqing.qywx.service.common.corp;


import com.rishiqing.qywx.service.model.corp.CorpStaffVO;

public interface CorpStaffManageService {
    /**
     * 保存corp信息
     * @param corpStaffVO
     */
    void saveOrUpdateCorpStaff(CorpStaffVO corpStaffVO);

    /**
     * 用户登录之后获取用户基本信息
     * @param corpId
     * @param userId
     * @return
     */
    CorpStaffVO getCorpLoginStaffInfo(String corpId, String userId);
}
