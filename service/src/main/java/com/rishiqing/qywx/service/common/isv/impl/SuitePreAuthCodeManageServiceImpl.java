package com.rishiqing.qywx.service.common.isv.impl;

import com.rishiqing.qywx.dao.mapper.isv.SuitePreAuthCodeDao;
import com.rishiqing.qywx.service.common.isv.SuitePreAuthCodeManageService;
import com.rishiqing.qywx.service.model.isv.SuitePreAuthCodeVO;
import com.rishiqing.qywx.service.model.isv.helper.SuitePreAuthCodeConverter;
import org.springframework.beans.factory.annotation.Autowired;

public class SuitePreAuthCodeManageServiceImpl implements SuitePreAuthCodeManageService {
    @Autowired
    private SuitePreAuthCodeDao suitePreAuthCodeDao;

    @Override
    public SuitePreAuthCodeVO getSuitePreAuthCode(String suiteKey) {
        return SuitePreAuthCodeConverter.suitePreAuthCodeDO2SuitePreAuthCodeVO(
                suitePreAuthCodeDao.getSuitePreAuthCodeBySuiteKey(suiteKey)
        );
    }

    @Override
    public void saveSuitePreAuthCode(SuitePreAuthCodeVO suitePreAuthCodeVO){
        suitePreAuthCodeDao.saveOrUpdateSuitePreAuthCode(
                SuitePreAuthCodeConverter.suitePreAuthCodeVO2SuitePreAuthCodeDO(suitePreAuthCodeVO)
        );
    }
}
