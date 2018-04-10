package com.rishiqing.qywx.service.biz.alert.model.converter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rishiqing.qywx.service.biz.alert.model.TodoAlertVO;

import java.util.*;

/**
 * @author Wallace Mao
 * Date: 2018-04-10 15:53
 */
public class TodoAlertConverter {
    public static TodoAlertVO Json2TodoAlertVO(JSONObject json){
        TodoAlertVO todoAlertVO = new TodoAlertVO();
        todoAlertVO.setTodoId(json.getString("todo_id"));

        JSONArray array = json.getJSONArray("mills_array");
        List<Long> milliList = new ArrayList<Long>(array.size());
        for (Object element : array) {
            Long mills = (Long) element;
            milliList.add(mills);
        }
        todoAlertVO.setMillsList(milliList);

//        array = json.getJSONArray("remind_array");
//        List<String> alertList = new ArrayList<String>(array.size());
//        for (Object element: array) {
//            String alert = (String) element;
//            alertList.add(alert);
//        }
//        todoAlertVO.setRuleList(alertList);

        todoAlertVO.setUserIdListString(json.getString("userid_list"));
        todoAlertVO.setMsgType(json.getString("msgtype"));
        todoAlertVO.setMsgContent(json.getJSONObject("msgcontent"));
        return todoAlertVO;
    }

    public static Map json2SendMessageMap(String userIdList, String msgType, JSONObject json){
        Map map = new HashMap<String, Object>();
        map.put("touser", userIdList);
        map.put("msgtype", msgType);
        map.put(msgType, json);
        return map;
    }
}
