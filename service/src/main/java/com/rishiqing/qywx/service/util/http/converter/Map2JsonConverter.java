package com.rishiqing.qywx.service.util.http.converter;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-04-10 11:12
 */
public class Map2JsonConverter {
    public static JSONObject prepareSendMessage(Map<String, Object> map){
        String msgType = (String)map.get("msgtype");
        Long agentId = (Long)map.get("agentid");

        JSONObject params = new JSONObject();
        params.put("msgtype", msgType);
        params.put("agentid", agentId);
        if(map.containsKey("touser")){
            params.put("touser", (String)map.get("touser"));
        }
        if(map.containsKey("toparty")){
            params.put("toparty", (String)map.get("toparty"));
        }
        if(map.containsKey("totag")){
            params.put("totag", (String)map.get("totag"));
        }
        if(map.containsKey("safe")){
            params.put("safe", (Long)map.get("safe"));
        }
        params.put(msgType, (JSONObject)map.get(msgType));
        return params;
    }
}
