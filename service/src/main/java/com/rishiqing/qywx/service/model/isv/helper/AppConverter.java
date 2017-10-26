package com.rishiqing.qywx.service.model.isv.helper;

import com.rishiqing.qywx.service.model.isv.AppVO;
import com.rishiqing.qywx.dao.model.isv.AppDO;

public class AppConverter {
    public static AppVO appDO2AppVO(AppDO appDO){
        if(null == appDO){
            return null;
        }
        AppVO appVO = new AppVO();
        appVO.setId(appDO.getId());
        appVO.setAppId(appDO.getAppId());
        appVO.setSuiteKey(appDO.getSuiteKey());
        return appVO;
    }
}
