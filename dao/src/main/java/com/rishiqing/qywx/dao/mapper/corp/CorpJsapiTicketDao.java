package com.rishiqing.qywx.dao.mapper.corp;

import com.rishiqing.qywx.dao.model.corp.CorpJsapiTicketDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("corpJsapiTicketDao")
public interface CorpJsapiTicketDao {
    /**
     * 根据suiteKey和corpId，获取JsapiTicket
     * @param suiteKey
     * @param corpId
     * @return
     */
    CorpJsapiTicketDO getCorpJsapiTicketBySuiteKeyAndCorpId(@Param("suiteKey") String suiteKey, @Param("corpId") String corpId);

    /**
     * 保存或更新corpJsapiTicket
     * @param corpJsapiTicketDO
     */
    void saveOrUpdateCorpJsapiTicket(CorpJsapiTicketDO corpJsapiTicketDO);

    /**
     * 删除
     * @param suiteKey
     * @param corpId
     */
    void removeCorpJsapiTicketBySuiteKeyAndCorpId(@Param("suiteKey") String suiteKey, @Param("corpId") String corpId);
}
