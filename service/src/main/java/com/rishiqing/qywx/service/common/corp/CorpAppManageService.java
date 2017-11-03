package com.rishiqing.qywx.service.common.corp;

import com.rishiqing.qywx.service.model.corp.CorpAppVO;

import java.util.List;

public interface CorpAppManageService {
    /**
     * 根据appId和corpId获取corpApp
     * @param appId
     * @param corpId
     * @return
     */
    CorpAppVO getCorpApp(Long appId, String corpId);

    /**
     * 列出corp下的所有app
     * @param corpId
     * @return
     */
    List<CorpAppVO> listCorpApp(String corpId);

    /**
     * 保存corpApp
     * @param corpAppVO
     */
    void saveCorpApp(CorpAppVO corpAppVO);

    /**
     * 根据appId和corpId删除
     * @param appId
     * @param corpId
     */
    void removeCorpApp(Long appId, String corpId);
}
