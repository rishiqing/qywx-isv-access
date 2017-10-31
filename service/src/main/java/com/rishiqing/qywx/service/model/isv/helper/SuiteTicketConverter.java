package com.rishiqing.qywx.service.model.isv.helper;

import com.rishiqing.qywx.dao.model.isv.SuiteTicketDO;
import com.rishiqing.qywx.service.model.isv.SuiteTicketVO;

public class SuiteTicketConverter {
    public static SuiteTicketVO suiteTicketDO2SuiteTicketVO(SuiteTicketDO suiteTicketDO){
        if(suiteTicketDO == null){
            return null;
        }
        SuiteTicketVO suiteTicketVO = new SuiteTicketVO();
        suiteTicketVO.setId(suiteTicketDO.getId());
        suiteTicketVO.setSuiteKey(suiteTicketDO.getSuiteKey());
        suiteTicketVO.setTicket(suiteTicketDO.getTicket());
        suiteTicketVO.setTicketUpdateTime(suiteTicketDO.getTicketUpdateTime());
        return suiteTicketVO;
    }

    public static SuiteTicketDO suiteTicketVO2SuiteTicketDO(SuiteTicketVO suiteTicketVO){
        if(suiteTicketVO == null){
            return null;
        }
        SuiteTicketDO suiteTicketDO = new SuiteTicketDO();
        suiteTicketDO.setId(suiteTicketVO.getId());
        suiteTicketDO.setSuiteKey(suiteTicketVO.getSuiteKey());
        suiteTicketDO.setTicket(suiteTicketVO.getTicket());
        suiteTicketDO.setTicketUpdateTime(suiteTicketVO.getTicketUpdateTime());
        return suiteTicketDO;
    }
}
