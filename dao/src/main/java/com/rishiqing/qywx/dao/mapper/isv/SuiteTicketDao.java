package com.rishiqing.qywx.dao.mapper.isv;

import com.rishiqing.qywx.dao.model.isv.SuiteTicketDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository("suiteTicketDao")
public interface SuiteTicketDao {
    /**
     *
     * @param suiteKey
     * @return
     */
    SuiteTicketDO getSuiteTicketBySuiteKey(@Param("suiteKey") String suiteKey);
    /**
     * 新增或者更新suiteTicket
     * @param suiteTicketDO
     */
    void saveOrUpdateSuiteTicket(SuiteTicketDO suiteTicketDO);
}
