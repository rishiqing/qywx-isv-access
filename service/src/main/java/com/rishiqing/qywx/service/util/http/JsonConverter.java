package com.rishiqing.qywx.service.util.http;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rishiqing.qywx.dao.model.corp.CorpTokenDO;
import com.rishiqing.qywx.service.model.corp.CorpAppVO;
import com.rishiqing.qywx.service.model.corp.CorpSuiteVO;
import com.rishiqing.qywx.service.model.corp.CorpTokenVO;
import com.rishiqing.qywx.service.model.corp.CorpVO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonConverter {
    /**
     * 从如下的json格式中解析出来corp信息
     * "auth_corp_info":
     {
     "corpid": "xxxx",
     "corp_name": "name",
     "corp_type": "verified",
     "corp_square_logo_url": "yyyyy",
     "corp_user_max": 50,
     "corp_agent_max": 30,
     "corp_full_name":"full_name",
     "verified_end_time":1431775834,
     "subject_type": 1，
     "corp_wxqrcode": "zzzzz"
     }
     * @param json
     * @return
     */
    public static CorpVO generateCorp(JSONObject json){
        if(!json.containsKey("auth_corp_info")){
            return null;
        }
        JSONObject jsonCorp = json.getJSONObject("auth_corp_info");
        CorpVO corpVO = new CorpVO();
        corpVO.setCorpId(jsonCorp.getString("corpid"));
        corpVO.setCorpName(jsonCorp.getString("corp_name"));
        corpVO.setCorpType(jsonCorp.getString("corp_type"));
        corpVO.setCorpSquareLogoUrl(jsonCorp.getString("corp_square_logo_url"));
        corpVO.setCorpUserMax(jsonCorp.getLong("corp_user_max"));
        corpVO.setCorpAgentMax(jsonCorp.getLong("corp_agent_max"));
        corpVO.setCorpFullName(jsonCorp.getString("corp_full_name"));
        corpVO.setVerifiedEndTime(jsonCorp.getLong("verified_end_time"));
        corpVO.setSubjectType(jsonCorp.getLong("subject_type"));
        corpVO.setCorpWxqrcode(jsonCorp.getString("corp_wxqrcode"));

        //  如果有auth_user_info信息，即授权管理员信息，那么加上
        if(json.containsKey("auth_user_info")){
            JSONObject jsonUser = json.getJSONObject("auth_user_info");
            corpVO.setAuthEmail(jsonUser.getString("email"));
            corpVO.setAuthMobile(jsonUser.getString("mobile"));
            corpVO.setAuthUserId(jsonUser.getString("userid"));
            corpVO.setAuthName(jsonUser.getString("name"));
            corpVO.setAuthAvatar(jsonUser.getString("avatar"));
        }
        return corpVO;
    }

    public static CorpSuiteVO generateCorpSuite(String suiteKey, String corpId, JSONObject json){
        CorpSuiteVO corpSuiteVO = new CorpSuiteVO();
        corpSuiteVO.setCorpId(corpId);
        corpSuiteVO.setSuiteKey(suiteKey);
        corpSuiteVO.setPermanentCode(json.getString("permanent_code"));
        return corpSuiteVO;
    }

    public static List<CorpAppVO> generateCorpAppList(String suiteKey, String corpId, JSONObject json){
        if(!json.containsKey("auth_info")){
            return null;
        }
        JSONObject jsonAuth = json.getJSONObject("auth_info");
        if(!jsonAuth.containsKey("agent")){
            return null;
        }
        JSONArray jsonAgentArray = jsonAuth.getJSONArray("agent");
        List<CorpAppVO> result = new ArrayList<>();

        Iterator<Object> it = jsonAgentArray.iterator();
        while(it.hasNext()){
            JSONObject jsonAgent = (JSONObject) it.next();
            CorpAppVO corpAppVO = new CorpAppVO();
            corpAppVO.setSuiteKey(suiteKey);
            corpAppVO.setCorpId(corpId);
            corpAppVO.setAgentId(jsonAgent.getLong("agentid"));
            corpAppVO.setName(jsonAgent.getString("name"));
            corpAppVO.setRoundLogoUrl(jsonAgent.getString("round_logo_url"));
            corpAppVO.setSquareLogoUrl(jsonAgent.getString("square_logo_url"));
            corpAppVO.setAppId(jsonAgent.getLong("appid"));
            //  暂时忽略掉privilege
            //  jsonAgent.getJSONObject("privilege");
            result.add(corpAppVO);
        }
        return result;
    }

    public static CorpTokenVO generateCorpToken(String suiteKey, String corpId, JSONObject json){
        CorpTokenVO corpTokenVO = new CorpTokenVO();
        corpTokenVO.setSuiteKey(suiteKey);
        corpTokenVO.setCorpId(corpId);
        corpTokenVO.setCorpToken(json.getString("access_token"));
        corpTokenVO.setExpiresIn(json.getLong("expires_in"));
        return corpTokenVO;
    }
}
