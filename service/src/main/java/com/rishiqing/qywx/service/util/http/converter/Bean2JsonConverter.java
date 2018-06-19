package com.rishiqing.qywx.service.util.http.converter;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.qywx.service.model.corp.CorpAppVO;
import com.rishiqing.qywx.service.model.corp.CorpSuiteVO;
import com.rishiqing.qywx.service.model.isv.*;
import com.rishiqing.qywx.service.model.website.RegisterInfoVO;

public class Bean2JsonConverter {
    /**
     * 获取SuiteAccessToken时的参数
     * @param suiteVO
     * @param suiteTicketVO
     * @return
     */
    public static JSONObject prepareSuiteTicket(SuiteVO suiteVO, SuiteTicketVO suiteTicketVO){
        JSONObject params = new JSONObject();
        params.put("suite_id", suiteVO.getSuiteKey());
        params.put("suite_secret", suiteVO.getSuiteSecret());
        params.put("suite_ticket", suiteTicketVO.getTicket());
        return params;
    }

    public static JSONObject preparePermanentCode(SuiteTokenVO suiteTokenVO, CorpSuiteVO corpSuiteVO){
        JSONObject params = new JSONObject();
        params.put("suite_id", suiteTokenVO.getSuiteKey());
        params.put("auth_corpid", corpSuiteVO.getCorpId());
        params.put("permanent_code", corpSuiteVO.getPermanentCode());
        return params;
    }

    public static JSONObject prepareAuthCode(SuiteTokenVO suiteToken, String authCode){
        JSONObject params = new JSONObject();
        params.put("suite_id", suiteToken.getSuiteKey());
        params.put("auth_code", authCode);
        return params;
    }

    public static JSONObject prepareGetAdminList(SuiteTokenVO suiteTokenVO, CorpAppVO corpAppVO){
        JSONObject params = new JSONObject();
        params.put("auth_corpid", corpAppVO.getCorpId());
        params.put("agentid", corpAppVO.getAgentId());
        return params;
    }

    public static JSONObject prepareProviderAccessToken(IsvVO isvVO){
        JSONObject params = new JSONObject();
        params.put("corpid", isvVO.getCorpId());
        params.put("provider_secret", isvVO.getProviderSecret());
        return params;
    }

    public static JSONObject prepareGetRegisterCode(RegisterInfoVO registerInfoVO){
        JSONObject params = new JSONObject();
        params.put("template_id", registerInfoVO.getTemplateId());
        if(null != registerInfoVO.getCorpName()){
            params.put("corp_name", registerInfoVO.getCorpName());
        }
        if(null != registerInfoVO.getAdminName()){
            params.put("admin_name", registerInfoVO.getAdminName());
        }
        if(null != registerInfoVO.getAdminMobile()){
            params.put("admin_mobile", registerInfoVO.getAdminMobile());
        }
        if(null != registerInfoVO.getState()){
            params.put("state", registerInfoVO.getState());
        }
        return params;
    }

    public static JSONObject prepareGetWebsiteLoginInfo(String authCode){
        JSONObject params = new JSONObject();
        params.put("auth_code", authCode);
        return params;
    }

    public static JSONObject preparePhoneCall(PhoneCallInfoVO phoneCallInfoVO){
        JSONObject params = new JSONObject();
        params.put("caller", phoneCallInfoVO.getCallerUserId());
        params.put("callee", phoneCallInfoVO.getCalleeUserId());
        params.put("auth_corpid", phoneCallInfoVO.getCalleeCorpId());
        return params;
    }
}
