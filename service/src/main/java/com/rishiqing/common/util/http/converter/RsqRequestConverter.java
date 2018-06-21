package com.rishiqing.common.util.http.converter;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.common.model.RsqCommonUserVO;
import com.rishiqing.common.model.RsqTeamVO;

/**
 * @author Wallace Mao
 * Date: 2018-06-21 20:04
 */
public class RsqRequestConverter {
    public static JSONObject RsqTeamVO2Json(RsqTeamVO rsqTeamVO){
        JSONObject jsonReq = new JSONObject();
//        jsonReq.put("appName", appName);
        jsonReq.put("name", rsqTeamVO.getName());
        jsonReq.put("note", rsqTeamVO.getNote());

        jsonReq.put("outerId", rsqTeamVO.getCorpId());

        RsqCommonUserVO creator = rsqTeamVO.getCreator();
        if(creator != null){
            jsonReq.put("creator", RsqCommonUserVO2Json(creator));
        }

        return jsonReq;
    }

    private static JSONObject RsqCommonUserVO2Json(RsqCommonUserVO userVO){
        JSONObject jsonCreator = new JSONObject();
        jsonCreator.put("username", userVO.getUsername());
        jsonCreator.put("password", userVO.getPassword());
        jsonCreator.put("realName", userVO.getRealName());
        jsonCreator.put("outerId", userVO.getOuterId());
        jsonCreator.put("unionId", userVO.getUnionId());

        return jsonCreator;
    }
}
