package com.rishiqing.qywx.service.common.corp;


import com.rishiqing.qywx.service.model.corp.CorpStaffVO;

public interface CorpStaffManageService {
    /**
     * 根据corpId和userId获取到CorpStaff bean
     * @param corpId
     * @param userId
     * @return
     */
    CorpStaffVO getCorpStaffByCorpIdAndUserId(String corpId, String userId);
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
    /**
     * 根据corpId和userId删除CorpStaff
     * @param corpId
     * @param userId
     */
    void deleteCorpStaffByCorpIdAndUserId(String corpId, String userId);
}
