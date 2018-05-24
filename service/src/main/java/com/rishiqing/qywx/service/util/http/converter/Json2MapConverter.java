package com.rishiqing.qywx.service.util.http.converter;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-04-10 11:12
 */
public class Json2MapConverter {
    public static Map generateSendMessageMap(String msgType, JSONObject json){
        Map map = new HashMap<String, Object>();

        if(json.containsKey("touser")){
            map.put("touser", json.getString("touser"));
        }
        if(json.containsKey("toparty")){
            map.put("toparty", json.getString("toparty"));
        }
        if(json.containsKey("totag")){
            map.put("totag", json.getString("totag"));
        }
        if(json.containsKey("safe")){
            map.put("safe", json.getLong("safe"));
        }
        map.put("msgtype", msgType);
        map.put(msgType, json.getJSONObject(msgType));
        return map;
    }
}
