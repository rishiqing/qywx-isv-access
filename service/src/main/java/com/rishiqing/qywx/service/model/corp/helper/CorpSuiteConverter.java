package com.rishiqing.qywx.service.model.corp.helper;

import com.rishiqing.qywx.dao.model.corp.CorpSuiteDO;
import com.rishiqing.qywx.service.model.corp.CorpSuiteVO;

public class CorpSuiteConverter {
    public static CorpSuiteVO corpSuiteDO2CorpSuiteVO(CorpSuiteDO corpSuiteDO){
        if(corpSuiteDO == null){
            return null;
        }
        CorpSuiteVO corpSuiteVO = new CorpSuiteVO();
        corpSuiteVO.setId(corpSuiteDO.getId());
        corpSuiteVO.setSuiteKey(corpSuiteDO.getSuiteKey());
        corpSuiteVO.setCorpId(corpSuiteDO.getCorpId());
        corpSuiteVO.setPermanentCode(corpSuiteDO.getPermanentCode());
        corpSuiteVO.setAuthUserId(corpSuiteDO.getAuthUserId());
        corpSuiteVO.setAuthUserName(corpSuiteDO.getAuthUserName());
        corpSuiteVO.setAuthUserAvatar(corpSuiteDO.getAuthUserAvatar());
        return corpSuiteVO;
    }

    public static CorpSuiteDO corpSuiteVO2CorpSuiteDO(CorpSuiteVO corpSuiteVO){
        if(corpSuiteVO == null){
            return null;
        }
        CorpSuiteDO corpSuiteDO = new CorpSuiteDO();
        corpSuiteDO.setId(corpSuiteVO.getId());
        corpSuiteDO.setSuiteKey(corpSuiteVO.getSuiteKey());
        corpSuiteDO.setCorpId(corpSuiteVO.getCorpId());
        corpSuiteDO.setPermanentCode(corpSuiteVO.getPermanentCode());
        corpSuiteDO.setAuthUserId(corpSuiteVO.getAuthUserId());
        corpSuiteDO.setAuthUserName(corpSuiteVO.getAuthUserName());
        corpSuiteDO.setAuthUserAvatar(corpSuiteVO.getAuthUserAvatar());
        return corpSuiteDO;
    }
}
