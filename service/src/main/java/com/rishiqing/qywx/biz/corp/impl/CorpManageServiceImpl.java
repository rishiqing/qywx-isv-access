package com.rishiqing.qywx.biz.corp.impl;

import com.rishiqing.qywx.biz.corp.CorpManageService;
import com.rishiqing.qywx.dao.isv.AppDao;
import com.rishiqing.qywx.model.corp.CorpDO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

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
