package com.rishiqing.qywx.service.biz.corp.impl;

import com.rishiqing.qywx.dao.mapper.corp.CorpAppDao;
import com.rishiqing.qywx.service.biz.corp.CorpAppManageService;
import com.rishiqing.qywx.service.model.corp.CorpAppVO;
import com.rishiqing.qywx.service.model.corp.helper.CorpAppConverter;
import org.springframework.beans.factory.annotation.Autowired;

public class CorpAppManageServiceImpl implements CorpAppManageService {
    @Autowired
    private CorpAppDao corpAppDao;
    @Override
    public CorpAppVO getCorpApp(Long appId, String corpId) {
        return CorpAppConverter.corpAppDO2CorpAppVO(
                corpAppDao.getCorpAppByAppIdAndCorpId(appId, corpId)
        );
    }

    @Override
    public void saveCorpApp(CorpAppVO corpAppVO) {
        corpAppDao.saveOrUpdateCorpApp(
                CorpAppConverter.corpAppVO2CorpAppDO(corpAppVO)
        );
    }
}
