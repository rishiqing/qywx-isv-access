package com.rishiqing.qywx.dao.mapper.isv;

import com.rishiqing.qywx.dao.model.isv.SuiteTokenDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("suiteTokenDao")
public interface SuiteTokenDao {
    /**
     * 根据suiteKey获取suiteToken
     * @param suiteKey
     * @return
     */
    SuiteTokenDO getSuiteTokenBySuiteKey(@Param("suiteKey") String suiteKey);
    /**
     * 新增或者更新suiteToken
     * @param suiteTokenDO
     */
    void saveOrUpdateSuiteToken(SuiteTokenDO suiteTokenDO);
}
