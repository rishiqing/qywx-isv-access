package com.rishiqing.common.util.http.converter;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.common.model.RsqCommonUserVO;
import com.rishiqing.common.model.RsqDepartmentVO;
import com.rishiqing.common.model.RsqTeamVO;

/**
 * @author Wallace Mao
 * Date: 2018-02-08 15:42
 */
public class RsqResponseConverter {
    public static RsqTeamVO Json2RsqTeamVO(JSONObject json){
        if(json == null){
            return null;
        }
        RsqTeamVO rsqTeamVO = new RsqTeamVO();

        if(json.containsKey("id"))
            rsqTeamVO.setId(json.getLong("id"));
        if(json.containsKey("name"))
            rsqTeamVO.setName(json.getString("name"));
        if(json.containsKey("outerId"))
            rsqTeamVO.setOuterId(json.getString("outerId"));
        if(json.containsKey("note"))
            rsqTeamVO.setNote(json.getString("note"));
        if(json.containsKey("isExists"))
            rsqTeamVO.setExists(json.getBoolean("isExists"));
        if(json.containsKey("fromApp"))
            rsqTeamVO.setFromApp(json.getString("fromApp"));

        if(json.containsKey("creator")){
            RsqCommonUserVO creator = Json2RsqCommonUserVO(json.getJSONObject("creator"));
            rsqTeamVO.setCreator(creator);
        }

        return rsqTeamVO;
    }

    public static RsqDepartmentVO Json2RsqDepartmentVO(JSONObject json){
        if(json == null){
            return null;
        }
        RsqDepartmentVO rsqDepartmentVO = new RsqDepartmentVO();

        if(json.containsKey("id"))
            rsqDepartmentVO.setId(json.getLong("id"));
        if(json.containsKey("name"))
            rsqDepartmentVO.setName(json.getString("name"));
        if(json.containsKey("parentId"))
            rsqDepartmentVO.setParentId(json.getString("parentId"));
        if(json.containsKey("teamId"))
            rsqDepartmentVO.setTeamId(json.getString("teamId"));
        if(json.containsKey("orderNum"))
            rsqDepartmentVO.setOrderNum(json.getLong("orderNum"));
        if(json.containsKey("outerId"))
            rsqDepartmentVO.setOuterId(json.getString("outerId"));

        return rsqDepartmentVO;
    }

    public static RsqCommonUserVO Json2RsqCommonUserVO(JSONObject json){
        if(json == null){
            return null;
        }
        RsqCommonUserVO rsqCommonUserVO = new RsqCommonUserVO();

        if(json.containsKey("id"))
            rsqCommonUserVO.setId(json.getLong("id"));
        if(json.containsKey("username"))
            rsqCommonUserVO.setUsername(json.getString("username"));
        if(json.containsKey("password"))
            rsqCommonUserVO.setPassword(json.getString("password"));
        if(json.containsKey("realName"))
            rsqCommonUserVO.setRealName(json.getString("realName"));
        if(json.containsKey("teamId"))
            rsqCommonUserVO.setTeamId(json.getLong("teamId"));
        if(json.containsKey("department"))
            rsqCommonUserVO.setDepartment(json.getString("department"));
        if(json.containsKey("unionId"))
            rsqCommonUserVO.setUnionId(json.getString("unionId"));
        if(json.containsKey("outerId"))
            rsqCommonUserVO.setOuterId(json.getString("outerId"));
        if(json.containsKey("fromClient"))
            rsqCommonUserVO.setFromClient(json.getString("fromClient"));

        return rsqCommonUserVO;
    }
}
