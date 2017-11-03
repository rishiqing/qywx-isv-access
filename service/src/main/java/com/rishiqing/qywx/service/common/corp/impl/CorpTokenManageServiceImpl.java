package com.rishiqing.qywx.service.common.corp.impl;

import com.rishiqing.qywx.dao.mapper.corp.CorpTokenDao;
import com.rishiqing.qywx.service.common.corp.CorpTokenManageService;
import com.rishiqing.qywx.service.model.corp.CorpTokenVO;
import com.rishiqing.qywx.service.model.corp.helper.CorpTokenConverter;
import org.springframework.beans.factory.annotation.Autowired;

public class CorpTokenManageServiceImpl implements CorpTokenManageService {
    @Autowired
    private CorpTokenDao corpTokenDao;
    @Override
    public CorpTokenVO getCorpToken(String suiteKey, String corpId) {
        CorpTokenVO token =  CorpTokenConverter.corpTokenDO2CorpTokenVO(
                corpTokenDao.getCorpTokenBySuiteKeyAndCorpId(suiteKey, corpId)
        );
        return token;
    }

    @Override
    public void saveCorpToken(CorpTokenVO corpTokenVO) {
        corpTokenDao.saveOrUpdateCorpToken(
                CorpTokenConverter.corpTokenVO2CorpTokenDO(corpTokenVO)
        );
    }
}
