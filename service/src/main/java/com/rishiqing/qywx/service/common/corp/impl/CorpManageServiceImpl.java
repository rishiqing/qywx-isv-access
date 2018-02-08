package com.rishiqing.qywx.service.common.corp.impl;

import com.rishiqing.qywx.dao.mapper.corp.CorpDao;
import com.rishiqing.qywx.service.common.corp.CorpManageService;
import com.rishiqing.qywx.service.model.corp.CorpVO;
import com.rishiqing.qywx.service.model.corp.helper.CorpConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CorpManageServiceImpl implements CorpManageService {
    private static final Logger logger = LoggerFactory.getLogger("CORP_MANAGE_LOGGER");
    @Autowired
    private CorpDao corpDao;

    @Override
    public void saveOrUpdateCorp(CorpVO corpVO) {
        corpDao.saveOrUpdateCorp(
                CorpConverter.corpVO2CorpDO(corpVO)
        );
    }

    @Override
    public void markRemoveCorp(String corpId, Boolean authCanceled) {
        corpDao.updateCorpSetAuthCanceled(corpId, authCanceled);
    }
}
