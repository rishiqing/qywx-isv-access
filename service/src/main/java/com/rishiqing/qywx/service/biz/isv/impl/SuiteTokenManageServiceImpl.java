package com.rishiqing.qywx.service.biz.isv.impl;

import com.rishiqing.qywx.dao.mapper.isv.SuiteTokenDao;
import com.rishiqing.qywx.service.biz.isv.SuiteTokenManageService;
import com.rishiqing.qywx.service.model.isv.SuiteTokenVO;
import com.rishiqing.qywx.service.model.isv.helper.SuiteTokenConverter;
import org.springframework.beans.factory.annotation.Autowired;

public class SuiteTokenManageServiceImpl implements SuiteTokenManageService {
    @Autowired
    private SuiteTokenDao suiteTokenDao;

    public SuiteTokenVO getSuiteToken(String suiteKey) {
        return SuiteTokenConverter.suiteTokenDO2SuiteTokenVO(
                suiteTokenDao.getSuiteTokenBySuiteKey(suiteKey)
        );
    }

    public void saveSuiteToken(SuiteTokenVO suiteTokenVO) {
        suiteTokenDao.saveOrUpdateSuiteToken(
                SuiteTokenConverter.suiteTokenVO2SuiteTokenDO(suiteTokenVO)
        );
    }
}
