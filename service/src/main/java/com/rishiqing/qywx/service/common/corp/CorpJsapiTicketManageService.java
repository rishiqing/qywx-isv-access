package com.rishiqing.qywx.service.common.corp;

import com.rishiqing.qywx.service.model.corp.CorpJsapiTicketVO;

public interface CorpJsapiTicketManageService {
    /**
     * 根据suiteKey和corpId获取corpJsapiTicket
     * @param suiteKey
     * @param corpId
     * @return
     */
    CorpJsapiTicketVO getCorpJsapiTicket(String suiteKey, String corpId);

    /**
     * 保存corpSuite
     * @param corpJsapiTicketVO
     */
    void saveCorpJsapiTicket(CorpJsapiTicketVO corpJsapiTicketVO);
}
