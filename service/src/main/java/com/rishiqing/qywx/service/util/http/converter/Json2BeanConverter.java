package com.rishiqing.qywx.service.util.http.converter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rishiqing.qywx.dao.model.corp.CorpJsapiTicketDO;
import com.rishiqing.qywx.dao.model.corp.CorpTokenDO;
import com.rishiqing.qywx.service.model.corp.*;
import com.rishiqing.qywx.service.model.isv.SuitePreAuthCodeVO;
import com.rishiqing.qywx.service.model.isv.SuiteTokenVO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Json2BeanConverter {
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
        if(null == corpId && json.containsKey("auth_corp_info")){
            corpId = json.getJSONObject("auth_corp_info").getString("corpid");
        }
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
        List<CorpAppVO> result = new ArrayList<CorpAppVO>();

        for (Object aJsonAgentArray : jsonAgentArray) {
            JSONObject jsonAgent = (JSONObject) aJsonAgentArray;
            CorpAppVO corpAppVO = new CorpAppVO();
            corpAppVO.setSuiteKey(suiteKey);
            corpAppVO.setCorpId(corpId);
            corpAppVO.setAgentId(jsonAgent.getLong("agentid"));
            corpAppVO.setName(jsonAgent.getString("name"));
            corpAppVO.setRoundLogoUrl(jsonAgent.getString("round_logo_url"));
            corpAppVO.setSquareLogoUrl(jsonAgent.getString("square_logo_url"));
            corpAppVO.setAppId(jsonAgent.getLong("appid"));

            CorpAuthPrivilegeVO privilegeVO = generateCorpAuthPrivilegeVO(jsonAgent.getJSONObject("privilege"));
            corpAppVO.setCorpAuthPrivilegeVO(privilegeVO);

            result.add(corpAppVO);
        }
        return result;
    }

    private static CorpAuthPrivilegeVO generateCorpAuthPrivilegeVO(JSONObject json){
        CorpAuthPrivilegeVO privilegeVO = new CorpAuthPrivilegeVO();
        privilegeVO.setLevel(json.getLong("level"));
        
        JSONArray allowPartyArray = json.getJSONArray("allow_party");
        JSONArray allowUserArray = json.getJSONArray("allow_user");
        JSONArray allowTagArray = json.getJSONArray("allow_tag");
        JSONArray extraPartyArray = json.getJSONArray("extra_party");
        JSONArray extraUserArray = json.getJSONArray("extra_user");
        JSONArray extraTagArray = json.getJSONArray("extra_tag");
        List<Long> allowPartyList = new ArrayList<>(allowPartyArray.size());
        List<String> allowUserList = new ArrayList<>(allowUserArray.size());
        List<Long> allowTagList = new ArrayList<>(allowTagArray.size());
        List<Long> extraPartyList = new ArrayList<>(extraPartyArray.size());
        List<String> extraUserList = new ArrayList<>(extraUserArray.size());
        List<Long> extraTagList = new ArrayList<>(extraTagArray.size());
        
        for(Object party : allowPartyArray){
            allowPartyList.add((Long)party);
        }
        for(Object user : allowUserArray){
            allowUserList.add((String)user);
        }
        for(Object tag : allowTagArray){
            allowTagList.add((Long)tag);
        }

        for(Object party : extraPartyArray){
            extraPartyList.add((Long)party);
        }
        for(Object user : extraUserArray){
            extraUserList.add((String)user);
        }
        for(Object tag : extraTagArray){
            extraTagList.add((Long)tag);
        }

        privilegeVO.setAllowParty(allowPartyList);
        privilegeVO.setAllowUser(allowUserList);
        privilegeVO.setAllowTag(allowTagList);

        privilegeVO.setExtraParty(extraPartyList);
        privilegeVO.setExtraUser(extraUserList);
        privilegeVO.setExtraTag(extraTagList);
        
        return privilegeVO;
    }

    public static CorpTokenVO generateCorpToken(String suiteKey, String corpId, JSONObject json){
        CorpTokenVO corpTokenVO = new CorpTokenVO();
        corpTokenVO.setSuiteKey(suiteKey);
        corpTokenVO.setCorpId(corpId);
        corpTokenVO.setCorpToken(json.getString("access_token"));
        corpTokenVO.setExpiresIn(json.getLong("expires_in"));
        return corpTokenVO;
    }

    public static SuiteTokenVO generateSuiteToken(String suiteKey, JSONObject json){
        SuiteTokenVO suiteTokenVO = new SuiteTokenVO();
        suiteTokenVO.setSuiteKey(suiteKey);
        suiteTokenVO.setSuiteToken(json.getString("suite_access_token"));
        suiteTokenVO.setExpiresIn(json.getLong("expires_in"));
        return suiteTokenVO;
    }

    public static List<CorpDeptVO> generateDepartmentList(String corpId, JSONObject json){
        if(!json.containsKey("department")){
            return null;
        }
        JSONArray jsonDeptList = json.getJSONArray("department");
        List<CorpDeptVO> list = new ArrayList<CorpDeptVO>(jsonDeptList.size());
        Iterator<Object> it = jsonDeptList.iterator();
        while (it.hasNext()){
            JSONObject jsonDept = (JSONObject)it.next();
            CorpDeptVO corpDeptVO = new CorpDeptVO();
            corpDeptVO.setCorpId(corpId);
            corpDeptVO.setDeptId(jsonDept.getLong("id"));
            corpDeptVO.setName(jsonDept.getString("name"));
            corpDeptVO.setParentId(jsonDept.getLong("parentid"));
            corpDeptVO.setOrder(jsonDept.getLong("order"));
            list.add(corpDeptVO);
        }
        return list;
    }
    public static List<CorpStaffVO> generateStaffList(String corpId, JSONObject json){
        if(!json.containsKey("userlist")){
            return null;
        }
        JSONArray jsonStaffList = json.getJSONArray("userlist");
        List<CorpStaffVO> list = new ArrayList<CorpStaffVO>(jsonStaffList.size());
        for (Object aJsonStaffList : jsonStaffList) {
            JSONObject jsonStaff = (JSONObject) aJsonStaffList;
            list.add(generateCorpStaff(corpId, jsonStaff));
        }
        return list;
    }

    public static CorpStaffVO generateCorpStaff(String corpId, JSONObject jsonStaff){
        CorpStaffVO staffVO = new CorpStaffVO();
        staffVO.setCorpId(corpId);
        staffVO.setUserId(jsonStaff.getString("userid"));
        staffVO.setName(jsonStaff.getString("name"));
        staffVO.setDepartment(jsonStaff.getJSONArray("department").toString());
        staffVO.setOrderInDepts(jsonStaff.getJSONArray("order").toString());
        staffVO.setPosition(jsonStaff.getString("position"));
        staffVO.setMobile(jsonStaff.getString("mobile"));
        staffVO.setGender(jsonStaff.getString("gender"));
        staffVO.setEmail(jsonStaff.getString("email"));
        staffVO.setIsLeaderInDepts(String.valueOf(jsonStaff.getLong("isleader")));
        staffVO.setAvatar(jsonStaff.getString("avatar"));
        staffVO.setTel(jsonStaff.getString("telephone"));
        staffVO.setEnglishName(jsonStaff.getString("english_name"));
        staffVO.setStatus(jsonStaff.getLong("status"));
        if(jsonStaff.containsKey("extattr")){
            staffVO.setExtattr(jsonStaff.getJSONObject("extattr").toString());
        }
        return staffVO;
    }

    public static CorpJsapiTicketVO generateCorpJsapiTicket(String suiteKey, String corpId, JSONObject json){
        CorpJsapiTicketVO corpJsapiTicketVO = new CorpJsapiTicketVO();
        corpJsapiTicketVO.setSuiteKey(suiteKey);
        corpJsapiTicketVO.setCorpId(corpId);
        corpJsapiTicketVO.setCorpJsapiTicket(json.getString("ticket"));
        corpJsapiTicketVO.setExpiresIn(json.getLong("expires_in"));
        return corpJsapiTicketVO;
    }

    public static LoginUserVO generateLoginUser(String corpId, JSONObject json){
        LoginUserVO loginUserVO = new LoginUserVO();
        loginUserVO.setCorpId(corpId);
        loginUserVO.setDeviceId(json.getString("DeviceId"));

        if(json.containsKey("UserId")){
            loginUserVO.setUserId(json.getString("UserId"));
        }
        if(json.containsKey("user_ticket")){
            loginUserVO.setUserTicket(json.getString("user_ticket"));
        }
        if(json.containsKey("expires_in")){
            loginUserVO.setExpiresIn(json.getLong("expires_in"));
        }
        if(json.containsKey("OpenId")){
            loginUserVO.setOpenId(json.getString("OpenId"));
        }
        return loginUserVO;
    }
    public static List<CorpStaffVO> generateCorpAdminList(String corpId, JSONObject json){
        if(!json.containsKey("admin")){
            return null;
        }
        JSONArray jsonStaffList = json.getJSONArray("admin");
        List<CorpStaffVO> list = new ArrayList<CorpStaffVO>(jsonStaffList.size());
        Iterator<Object> it = jsonStaffList.iterator();
        while (it.hasNext()){
            JSONObject jsonStaff = (JSONObject)it.next();
            CorpStaffVO staffVO = new CorpStaffVO();
            staffVO.setCorpId(corpId);
            staffVO.setUserId(jsonStaff.getString("userid"));
            staffVO.setAdminType(jsonStaff.getLong("auth_type"));
            list.add(staffVO);
        }
        return list;
    }

    public static SuitePreAuthCodeVO generateSuitePreAuthCode(JSONObject json){
        if(null == json){
            return null;
        }
        SuitePreAuthCodeVO suitePreAuthCodeVO = new SuitePreAuthCodeVO();
        suitePreAuthCodeVO.setSuitePreAuthCode(json.getString("pre_auth_code"));
        suitePreAuthCodeVO.setExpiresIn(json.getLong("expires_in"));
        return suitePreAuthCodeVO;
    }
}
