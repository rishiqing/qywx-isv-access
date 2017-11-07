package com.rishiqing.qywx.service.model.corp.helper;

import com.rishiqing.qywx.dao.model.corp.CorpJsapiTicketDO;
import com.rishiqing.qywx.service.model.corp.CorpJsapiTicketVO;

public class CorpJsapiTicketConverter {
    public static CorpJsapiTicketVO corpJsapiTicketDO2CorpJsapiTicketVO(CorpJsapiTicketDO corpJsapiTicketDO){
        if(corpJsapiTicketDO == null){
            return null;
        }
        CorpJsapiTicketVO corpJsapiTicketVO = new CorpJsapiTicketVO();
        corpJsapiTicketVO.setId(corpJsapiTicketDO.getId());
        corpJsapiTicketVO.setSuiteKey(corpJsapiTicketDO.getSuiteKey());
        corpJsapiTicketVO.setCorpId(corpJsapiTicketDO.getCorpId());
        corpJsapiTicketVO.setCorpJsapiTicket(corpJsapiTicketDO.getCorpJsapiTicket());
        corpJsapiTicketVO.setExpiresIn(corpJsapiTicketDO.getExpiresIn());
        return corpJsapiTicketVO;
    }
    
    public static CorpJsapiTicketDO corpJsapiTicketVO2CorpJsapiTicketDO(CorpJsapiTicketVO corpJsapiTicketVO){
        if(corpJsapiTicketVO == null){
            return null;
        }
        CorpJsapiTicketDO corpJsapiTicketDO = new CorpJsapiTicketDO();
        corpJsapiTicketDO.setId(corpJsapiTicketVO.getId());
        corpJsapiTicketDO.setSuiteKey(corpJsapiTicketVO.getSuiteKey());
        corpJsapiTicketDO.setCorpId(corpJsapiTicketVO.getCorpId());
        corpJsapiTicketDO.setCorpJsapiTicket(corpJsapiTicketVO.getCorpJsapiTicket());
        corpJsapiTicketDO.setExpiresIn(corpJsapiTicketVO.getExpiresIn());
        return corpJsapiTicketDO;
    }
}
