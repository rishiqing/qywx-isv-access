package com.rishiqing.qywx.service.model.corp.helper;

import com.rishiqing.qywx.dao.model.corp.CorpAppDO;
import com.rishiqing.qywx.service.model.corp.CorpAppVO;

public class CorpAppConverter {
    public static CorpAppVO corpAppDO2CorpAppVO(CorpAppDO corpAppDO){
        CorpAppVO corpAppVO = new CorpAppVO();
        corpAppVO.setId(corpAppDO.getId());
        corpAppVO.setSuiteKey(corpAppDO.getSuiteKey());
        corpAppVO.setAppId(corpAppDO.getAppId());
        corpAppVO.setCorpId(corpAppDO.getCorpId());
        corpAppVO.setAgentId(corpAppDO.getAgentId());
        corpAppVO.setName(corpAppDO.getName());
        corpAppVO.setSquareLogoUrl(corpAppDO.getSquareLogoUrl());
        corpAppVO.setRoundLogoUrl(corpAppDO.getRoundLogoUrl());
        return corpAppVO;
    }
    public static CorpAppDO corpAppVO2CorpAppDO(CorpAppVO corpAppVO){
        CorpAppDO corpAppDO = new CorpAppDO();
        corpAppDO.setId(corpAppVO.getId());
        corpAppDO.setSuiteKey(corpAppVO.getSuiteKey());
        corpAppDO.setAppId(corpAppVO.getAppId());
        corpAppDO.setCorpId(corpAppVO.getCorpId());
        corpAppDO.setAgentId(corpAppVO.getAgentId());
        corpAppDO.setName(corpAppVO.getName());
        corpAppDO.setSquareLogoUrl(corpAppVO.getSquareLogoUrl());
        corpAppDO.setRoundLogoUrl(corpAppVO.getRoundLogoUrl());
        return corpAppDO;
    }
}
