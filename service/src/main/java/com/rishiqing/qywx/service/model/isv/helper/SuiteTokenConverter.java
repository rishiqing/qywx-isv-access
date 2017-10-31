package com.rishiqing.qywx.service.model.isv.helper;

import com.rishiqing.qywx.dao.model.isv.SuiteTokenDO;
import com.rishiqing.qywx.service.model.isv.SuiteTokenVO;

public class SuiteTokenConverter {
    public static SuiteTokenVO suiteTokenDO2SuiteTokenVO(SuiteTokenDO suiteTokenDO){
        if(suiteTokenDO == null){
            return null;
        }
        SuiteTokenVO suiteTokenVO = new SuiteTokenVO();
        suiteTokenVO.setId(suiteTokenDO.getId());
        suiteTokenVO.setSuiteKey(suiteTokenDO.getSuiteKey());
        suiteTokenVO.setSuiteToken(suiteTokenDO.getSuiteToken());
        suiteTokenVO.setExpiresIn(suiteTokenDO.getExpiresIn());
        suiteTokenVO.setTokenUpdateTime(suiteTokenDO.getTokenUpdateTime());
        return suiteTokenVO;
    }

    public static SuiteTokenDO suiteTokenVO2SuiteTokenDO(SuiteTokenVO suiteTokenVO){
        if(suiteTokenVO == null){
            return null;
        }
        SuiteTokenDO suiteTokenDO = new SuiteTokenDO();
        suiteTokenDO.setId(suiteTokenVO.getId());
        suiteTokenDO.setSuiteKey(suiteTokenVO.getSuiteKey());
        suiteTokenDO.setSuiteToken(suiteTokenVO.getSuiteToken());
        suiteTokenDO.setExpiresIn(suiteTokenVO.getExpiresIn());
        suiteTokenDO.setTokenUpdateTime(suiteTokenVO.getTokenUpdateTime());
        return suiteTokenDO;
    }
}
