package com.rishiqing.qywx.service.common.isv.impl;

import com.rishiqing.qywx.dao.mapper.isv.SuiteTicketDao;
import com.rishiqing.qywx.service.common.isv.SuiteTicketManageService;
import com.rishiqing.qywx.service.model.isv.SuiteTicketVO;
import com.rishiqing.qywx.service.model.isv.helper.SuiteTicketConverter;
import org.springframework.beans.factory.annotation.Autowired;

public class SuiteTicketManageServiceImpl implements SuiteTicketManageService {
    @Autowired
    private SuiteTicketDao suiteTicketDao;

    public SuiteTicketVO getSuiteTicket(String suiteKey) {
        return SuiteTicketConverter.suiteTicketDO2SuiteTicketVO(
                suiteTicketDao.getSuiteTicketBySuiteKey(suiteKey)
        );
    }

    public void saveSuiteTicket(SuiteTicketVO suiteTicketVO) {
        suiteTicketDao.saveOrUpdateSuiteTicket(
                SuiteTicketConverter.suiteTicketVO2SuiteTicketDO(suiteTicketVO)
        );
    }
}
