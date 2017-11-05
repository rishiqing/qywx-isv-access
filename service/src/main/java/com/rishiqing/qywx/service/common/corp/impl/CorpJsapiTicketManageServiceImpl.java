package com.rishiqing.qywx.service.common.corp.impl;

import com.rishiqing.qywx.dao.mapper.corp.CorpJsapiTicketDao;
import com.rishiqing.qywx.service.common.corp.CorpJsapiTicketManageService;
import com.rishiqing.qywx.service.model.corp.CorpJsapiTicketVO;
import com.rishiqing.qywx.service.model.corp.helper.CorpJsapiTicketConverter;
import org.springframework.beans.factory.annotation.Autowired;

public class CorpJsapiTicketManageServiceImpl implements CorpJsapiTicketManageService {
    @Autowired
    private CorpJsapiTicketDao corpJsapiTicketDao;
    @Override
    public CorpJsapiTicketVO getCorpJsapiTicket(String suiteKey, String corpId) {
        CorpJsapiTicketVO token = CorpJsapiTicketConverter.corpJsapiTicketDO2CorpJsapiTicketVO(
                corpJsapiTicketDao.getCorpJsapiTicketBySuiteKeyAndCorpId(suiteKey, corpId)
        );
        return token;
    }

    @Override
    public void saveCorpJsapiTicket(CorpJsapiTicketVO corpJsapiTicketVO) {
        corpJsapiTicketDao.saveOrUpdateCorpJsapiTicket(
                CorpJsapiTicketConverter.corpJsapiTicketVO2CorpJsapiTicketDO(corpJsapiTicketVO)
        );
    }
}
