package com.rishiqing.qywx.service.common.isv;

import com.rishiqing.qywx.service.model.isv.SuiteTicketVO;

public interface SuiteTicketManageService {
    /**
     * 根据suiteKey获取suiteTicket
     * @param suiteKey
     * @return
     */
    SuiteTicketVO getSuiteTicket(String suiteKey);

    /**
     * 保存suiteTicket
     * @param suiteTicketVO
     */
    void saveSuiteTicket(SuiteTicketVO suiteTicketVO);
}
