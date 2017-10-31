package com.rishiqing.qywx.service.biz.corp.impl;

import com.rishiqing.qywx.dao.mapper.corp.CorpSuiteDao;
import com.rishiqing.qywx.service.biz.corp.CorpSuiteManageService;
import com.rishiqing.qywx.service.model.corp.CorpSuiteVO;
import com.rishiqing.qywx.service.model.corp.helper.CorpSuiteConverter;
import org.springframework.beans.factory.annotation.Autowired;

public class CorpSuiteManageServiceImpl implements CorpSuiteManageService {
    @Autowired
    private CorpSuiteDao corpSuiteDao;
    @Override
    public CorpSuiteVO getCorpSuite(String suiteKey, String corpId) {
        return CorpSuiteConverter.corpSuiteDO2CorpSuiteVO(
                corpSuiteDao.getCorpSuiteBySuiteKeyAndCorpId(suiteKey, corpId)
        );
    }

    @Override
    public void saveCorpSuite(CorpSuiteVO corpSuiteVO) {
        corpSuiteDao.saveOrUpdateCorpSuite(
                CorpSuiteConverter.corpSuiteVO2CorpSuiteDO(corpSuiteVO)
        );
    }
}
