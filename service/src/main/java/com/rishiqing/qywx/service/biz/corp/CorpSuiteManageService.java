package com.rishiqing.qywx.service.biz.corp;

import com.rishiqing.qywx.service.model.corp.CorpSuiteVO;

public interface CorpSuiteManageService {
    /**
     * 根据suiteKey获取corpSuite
     * @param suiteKey
     * @param corpId
     * @return
     */
    CorpSuiteVO getCorpSuite(String suiteKey, String corpId);

    /**
     * 保存corpSuite
     * @param corpSuiteVO
     */
    void saveCorpSuite(CorpSuiteVO corpSuiteVO);
}
