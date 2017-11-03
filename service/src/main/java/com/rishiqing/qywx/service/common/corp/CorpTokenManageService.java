package com.rishiqing.qywx.service.common.corp;

import com.rishiqing.qywx.service.model.corp.CorpTokenVO;

public interface CorpTokenManageService {
    /**
     * 根据suiteKey获取corpSuite
     * @param suiteKey
     * @param corpId
     * @return
     */
    CorpTokenVO getCorpToken(String suiteKey, String corpId);

    /**
     * 保存corpSuite
     * @param corpTokenVO
     */
    void saveCorpToken(CorpTokenVO corpTokenVO);
}
