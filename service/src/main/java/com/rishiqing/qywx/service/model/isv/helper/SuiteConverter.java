package com.rishiqing.qywx.service.model.isv.helper;

import com.rishiqing.qywx.dao.model.isv.SuiteDO;
import com.rishiqing.qywx.service.model.isv.SuiteVO;

public class SuiteConverter {
    public static SuiteVO suiteDO2SuiteVO(SuiteDO suiteDO){
        if(suiteDO == null){
            return null;
        }
        SuiteVO suiteVO = new SuiteVO();
        suiteVO.setId(suiteDO.getId());
        suiteVO.setSuiteName(suiteDO.getSuiteName());
        suiteVO.setSuiteKey(suiteDO.getSuiteKey());
        suiteVO.setSuiteSecret(suiteDO.getSuiteSecret());
        suiteVO.setEncodingAesKey(suiteDO.getEncodingAesKey());
        suiteVO.setToken(suiteDO.getToken());
        suiteVO.setCorpId(suiteDO.getCorpId());
        suiteVO.setRsqAppName(suiteDO.getRsqAppName());
        suiteVO.setRsqAppToken(suiteDO.getRsqAppToken());

        return suiteVO;
    }
}