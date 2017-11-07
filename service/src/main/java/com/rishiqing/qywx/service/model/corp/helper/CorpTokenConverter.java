package com.rishiqing.qywx.service.model.corp.helper;

import com.rishiqing.qywx.dao.model.corp.CorpTokenDO;
import com.rishiqing.qywx.service.model.corp.CorpTokenVO;

public class CorpTokenConverter {
    public static CorpTokenVO corpTokenDO2CorpTokenVO(CorpTokenDO corpTokenDO){
        if(corpTokenDO == null){
            return null;
        }
        CorpTokenVO corpTokenVO = new CorpTokenVO();
        corpTokenVO.setId(corpTokenDO.getId());
        corpTokenVO.setSuiteKey(corpTokenDO.getSuiteKey());
        corpTokenVO.setCorpId(corpTokenDO.getCorpId());
        corpTokenVO.setCorpToken(corpTokenDO.getCorpToken());
        corpTokenVO.setExpiresIn(corpTokenDO.getExpiresIn());
        return corpTokenVO;
    }
    
    public static CorpTokenDO corpTokenVO2CorpTokenDO(CorpTokenVO corpTokenVO){
        if(corpTokenVO == null){
            return null;
        }
        CorpTokenDO corpTokenDO = new CorpTokenDO();
        corpTokenDO.setId(corpTokenVO.getId());
        corpTokenDO.setSuiteKey(corpTokenVO.getSuiteKey());
        corpTokenDO.setCorpId(corpTokenVO.getCorpId());
        corpTokenDO.setCorpToken(corpTokenVO.getCorpToken());
        corpTokenDO.setExpiresIn(corpTokenVO.getExpiresIn());
        return corpTokenDO;
    }
}
