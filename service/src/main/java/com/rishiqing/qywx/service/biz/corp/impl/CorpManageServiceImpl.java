package com.rishiqing.qywx.service.biz.corp.impl;

import com.rishiqing.qywx.service.biz.corp.CorpManageService;
import com.rishiqing.qywx.dao.mapper.isv.AppDao;
import com.rishiqing.qywx.dao.model.corp.CorpDO;
import org.springframework.beans.factory.annotation.Autowired;

public class CorpManageServiceImpl implements CorpManageService {

    @Autowired
    private AppDao appDao;

    public CorpDO getCorpInfo() {
        //  demo
        CorpDO corpDO = new CorpDO();
        corpDO.setId(1L);
        corpDO.setCorpName("demo CorpDO");
        return corpDO;
    }
}
