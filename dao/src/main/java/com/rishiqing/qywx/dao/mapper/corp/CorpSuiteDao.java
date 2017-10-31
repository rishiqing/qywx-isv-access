package com.rishiqing.qywx.dao.mapper.corp;

import com.rishiqing.qywx.dao.model.corp.CorpSuiteDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("corpSuiteDao")
public interface CorpSuiteDao {
    /**
     * 根据suiteKey和corpId获取CorpSuite
     * @param suiteKey
     * @param corpId
     * @return
     */
    CorpSuiteDO getCorpSuiteBySuiteKeyAndCorpId(@Param("suiteKey") String suiteKey, @Param("corpId") String corpId);

    /**
     * 保存或更新corpSuite
     * @param corpSuiteDO
     */
    void saveOrUpdateCorpSuite(CorpSuiteDO corpSuiteDO);

    /**
     * 删除
     * @param suiteKey
     * @param corpId
     */
    void removeCorpSuiteBySuiteKeyAndCorpId(@Param("suiteKey") String suiteKey, @Param("corpId") String corpId);
}
