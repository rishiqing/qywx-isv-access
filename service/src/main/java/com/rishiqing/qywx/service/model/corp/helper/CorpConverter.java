package com.rishiqing.qywx.service.model.corp.helper;

import com.rishiqing.qywx.dao.model.corp.CorpDO;
import com.rishiqing.qywx.service.model.corp.CorpVO;

public class CorpConverter {
    public static CorpVO corpDO2CorpVO(CorpDO corpDO){
        if(corpDO == null){
            return null;
        }
        CorpVO corpVO = new CorpVO();
        corpVO.setId(corpDO.getId());
        corpVO.setCorpId(corpDO.getCorpId());
        corpVO.setCorpName(corpDO.getCorpName());
        corpVO.setCorpType(corpDO.getCorpType());
        corpVO.setCorpFullName(corpDO.getCorpFullName());
        corpVO.setCorpAgentMax(corpDO.getCorpAgentMax());
        corpVO.setCorpSquareLogoUrl(corpDO.getCorpSquareLogoUrl());
        corpVO.setCorpUserMax(corpDO.getCorpUserMax());
        corpVO.setCorpWxqrcode(corpDO.getCorpWxqrcode());
        corpVO.setAuthName(corpDO.getAuthName());
        corpVO.setAuthUserId(corpDO.getAuthUserId());
        corpVO.setAuthMobile(corpDO.getAuthMobile());
        corpVO.setAuthEmail(corpDO.getAuthEmail());
        corpVO.setAuthAvatar(corpDO.getAuthAvatar());
        corpVO.setSubjectType(corpDO.getSubjectType());
        corpVO.setVerifiedEndTime(corpDO.getVerifiedEndTime());
        corpVO.setAuthCanceled(corpDO.getAuthCanceled());
        corpVO.setRsqId(corpDO.getRsqId());

        return corpVO;
    }
    public static CorpDO corpVO2CorpDO(CorpVO corpVO){
        if(corpVO == null){
            return null;
        }
        CorpDO corpDO = new CorpDO();
        corpDO.setId(corpVO.getId());
        corpDO.setCorpId(corpVO.getCorpId());
        corpDO.setCorpName(corpVO.getCorpName());
        corpDO.setCorpType(corpVO.getCorpType());
        corpDO.setCorpFullName(corpVO.getCorpFullName());
        corpDO.setCorpAgentMax(corpVO.getCorpAgentMax());
        corpDO.setCorpSquareLogoUrl(corpVO.getCorpSquareLogoUrl());
        corpDO.setCorpUserMax(corpVO.getCorpUserMax());
        corpDO.setCorpWxqrcode(corpVO.getCorpWxqrcode());
        corpDO.setAuthName(corpVO.getAuthName());
        corpDO.setAuthUserId(corpVO.getAuthUserId());
        corpDO.setAuthMobile(corpVO.getAuthMobile());
        corpDO.setAuthEmail(corpVO.getAuthEmail());
        corpDO.setAuthAvatar(corpVO.getAuthAvatar());
        corpDO.setSubjectType(corpVO.getSubjectType());
        corpDO.setVerifiedEndTime(corpVO.getVerifiedEndTime());
        corpDO.setAuthCanceled(corpVO.getAuthCanceled());
        corpDO.setRsqId(corpVO.getRsqId());

        return corpDO;
    }
}
