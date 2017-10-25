package com.rishiqing.qywx.biz.corp.impl;

import com.rishiqing.qywx.biz.corp.CorpManageService;
import com.rishiqing.qywx.model.CorpDO;

public class CorpManageServiceImpl implements CorpManageService {
    public CorpDO getCorpInfo() {
        //  demo
        CorpDO corpDO = new CorpDO();
        corpDO.setId(1L);
        corpDO.setCorpName("demo CorpDO");
        return corpDO;
    }
}
