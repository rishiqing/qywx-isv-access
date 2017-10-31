package com.rishiqing.qywx.dao.mapper.corp;

import com.rishiqing.qywx.dao.model.corp.CorpTokenDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("corpTokenDao")
public interface CorpTokenDao {
    /**
     * 根据suiteKey和corpId，获取CorpToken
     * @param suiteKey
     * @param corpId
     * @return
     */
    CorpTokenDO getCorpTokenBySuiteKeyAndCorpId(@Param("suiteKey") String suiteKey, @Param("corpId") String corpId);

    /**
     * 保存或更新corpToken
     * @param corpTokenDO
     */
    void saveOrUpdateCorpToken(CorpTokenDO corpTokenDO);

    /**
     * 删除
     * @param suiteKey
     * @param corpId
     */
    void removeCorpTokenBySuiteKeyAndCorpId(@Param("suiteKey") String suiteKey, @Param("corpId") String corpId);
}
