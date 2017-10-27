package com.rishiqing.qywx.dao.mapper.isv;

import com.rishiqing.qywx.dao.model.isv.SuiteDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("suiteDao")
public interface SuiteDao {
    /**
     * 根据id获取suite
     * @return
     */
    SuiteDO getSuiteBySuiteKey(@Param("suiteKey") String suiteKey);

    /**
     * 保存或者更新suite，如果suite的key值不存在，那么走insert，如果存在，走update
     * @param suiteDO
     */
    void saveOrUpdateSuite(SuiteDO suiteDO);

    /**
     * 根据suite的key值删除suite
     * @param suiteDO
     */
    void removeSuiteBySuiteKey(SuiteDO suiteDO);
}
