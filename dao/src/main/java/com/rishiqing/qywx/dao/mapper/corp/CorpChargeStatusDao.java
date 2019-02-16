package com.rishiqing.qywx.dao.mapper.corp;

import com.rishiqing.qywx.dao.model.corp.CorpChargeStatusDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Wallace Mao
 * Date: 2018-11-02 17:07
 */
@Repository("corpChargeStatusDao")
public interface CorpChargeStatusDao {

    /**
     * 保存corpChargeStatusDO
     * @param corpChargeStatusDO
     */
    void saveOrUpdateCorpChargeStatus(CorpChargeStatusDO corpChargeStatusDO);

    /**
     * 根据suiteKey和corpId获取唯一的CorpChargeStatus
     * @param corpId
     * @return
     */
    CorpChargeStatusDO getCorpChargeStatusByCorpId(@Param("corpId") String corpId);

}
