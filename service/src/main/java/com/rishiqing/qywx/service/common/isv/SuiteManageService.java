package com.rishiqing.qywx.service.common.isv;

import com.rishiqing.qywx.service.model.isv.SuiteVO;

public interface SuiteManageService {
    /**
     * 获取suite的基本信息
     * @param key
     * @return
     */
    SuiteVO getSuiteInfoByKey(String key);
}
