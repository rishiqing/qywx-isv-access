package com.rishiqing.qywx.service.model.isv.helper;

import com.rishiqing.qywx.dao.model.isv.SuitePreAuthCodeDO;
import com.rishiqing.qywx.service.model.isv.SuitePreAuthCodeVO;

public class SuitePreAuthCodeConverter {
    public static SuitePreAuthCodeVO suitePreAuthCodeDO2SuitePreAuthCodeVO(SuitePreAuthCodeDO suitePreAuthCodeDO){
        if(suitePreAuthCodeDO == null){
            return null;
        }
        SuitePreAuthCodeVO suitePreAuthCodeVO = new SuitePreAuthCodeVO();
        suitePreAuthCodeVO.setId(suitePreAuthCodeDO.getId());
        suitePreAuthCodeVO.setSuiteKey(suitePreAuthCodeDO.getSuiteKey());
        suitePreAuthCodeVO.setSuitePreAuthCode(suitePreAuthCodeDO.getSuitePreAuthCode());
        suitePreAuthCodeVO.setExpiresIn(suitePreAuthCodeDO.getExpiresIn());
        suitePreAuthCodeVO.setCodeUpdateTime(suitePreAuthCodeDO.getCodeUpdateTime());
        return suitePreAuthCodeVO;
    }

    public static SuitePreAuthCodeDO suitePreAuthCodeVO2SuitePreAuthCodeDO(SuitePreAuthCodeVO suitePreAuthCodeVO){
        if(suitePreAuthCodeVO == null){
            return null;
        }
        SuitePreAuthCodeDO suitePreAuthCodeDO = new SuitePreAuthCodeDO();
        suitePreAuthCodeDO.setId(suitePreAuthCodeVO.getId());
        suitePreAuthCodeDO.setSuiteKey(suitePreAuthCodeVO.getSuiteKey());
        suitePreAuthCodeDO.setSuitePreAuthCode(suitePreAuthCodeVO.getSuitePreAuthCode());
        suitePreAuthCodeDO.setExpiresIn(suitePreAuthCodeVO.getExpiresIn());
        suitePreAuthCodeDO.setCodeUpdateTime(suitePreAuthCodeVO.getCodeUpdateTime());
        return suitePreAuthCodeDO;
    }
}
