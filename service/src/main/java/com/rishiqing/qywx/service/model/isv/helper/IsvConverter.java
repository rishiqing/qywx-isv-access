package com.rishiqing.qywx.service.model.isv.helper;

import com.rishiqing.qywx.dao.model.isv.IsvDO;
import com.rishiqing.qywx.service.model.isv.IsvVO;

public class IsvConverter {
    public static IsvVO isvDO2IsvVO(IsvDO isvDO){
        if(isvDO == null){
            return null;
        }
        IsvVO isvVO = new IsvVO();
        isvVO.setId(isvDO.getId());
        isvVO.setProviderSecret(isvDO.getProviderSecret());
        isvVO.setEncodingAesKey(isvDO.getEncodingAesKey());
        isvVO.setToken(isvDO.getToken());
        isvVO.setCorpId(isvDO.getCorpId());
        isvVO.setProviderAccessToken(isvDO.getProviderAccessToken());
        isvVO.setExpiresIn(isvDO.getExpiresIn());
        isvVO.setProviderTokenUpdateTime(isvDO.getProviderTokenUpdateTime());

        return isvVO;
    }

    public static IsvDO isvVO2IsvDO(IsvVO isvVO){
        if(isvVO == null){
            return null;
        }
        IsvDO isvDO = new IsvDO();
        isvDO.setId(isvVO.getId());
        isvDO.setProviderSecret(isvVO.getProviderSecret());
        isvDO.setEncodingAesKey(isvVO.getEncodingAesKey());
        isvDO.setToken(isvVO.getToken());
        isvDO.setCorpId(isvVO.getCorpId());
        isvDO.setProviderAccessToken(isvVO.getProviderAccessToken());
        isvDO.setExpiresIn(isvVO.getExpiresIn());
        isvDO.setProviderTokenUpdateTime(isvVO.getProviderTokenUpdateTime());

        return isvDO;
    }
}