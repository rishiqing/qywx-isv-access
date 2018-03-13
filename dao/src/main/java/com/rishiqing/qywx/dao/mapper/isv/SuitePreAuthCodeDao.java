package com.rishiqing.qywx.dao.mapper.isv;

import com.rishiqing.qywx.dao.model.isv.SuitePreAuthCodeDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("suitePreAuthCodeDao")
public interface SuitePreAuthCodeDao {
    /**
     * 根据suiteKey获取suiteToken
     * @param suiteKey
     * @return
     */
    SuitePreAuthCodeDO getSuitePreAuthCodeBySuiteKey(@Param("suiteKey") String suiteKey);
    /**
     * 新增或者更新suiteToken
     * @param suitePreAuthCodeDO
     */
    void saveOrUpdateSuitePreAuthCode(SuitePreAuthCodeDO suitePreAuthCodeDO);
}
