package com.rishiqing.qywx.service.biz.corp;

import com.rishiqing.qywx.service.model.corp.CorpAppVO;

public interface CorpAppManageService {
    /**
     * 根据appId和corpId获取corpApp
     * @param appId
     * @param corpId
     * @return
     */
    CorpAppVO getCorpApp(Long appId, String corpId);

    /**
     * 保存corpApp
     * @param corpAppVO
     */
    void saveCorpApp(CorpAppVO corpAppVO);
}
