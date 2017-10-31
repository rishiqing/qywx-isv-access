package com.rishiqing.qywx.service.util.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.qywx.service.exception.AccessTokenExpiredException;
import com.rishiqing.qywx.service.exception.HttpException;

import java.util.HashMap;
import java.util.Map;

public class HttpCorpUtil {
    public static JSONObject getDepartmentList(String accessToken, String deptId) throws UnirestException, HttpException, AccessTokenExpiredException {
        Map<String, Object> map = new HashMap<>();
        map.put("access_token", accessToken);
        if(deptId != null){
            map.put("id", deptId);
        }

        HttpResponse<String> resp =
                Unirest.post("https://qyapi.weixin.qq.com/cgi-bin/department/list")
                        .queryString(map)
                        .asString();
        if(resp.getStatus() > 200){
            throw new HttpException("http request getDepartmentList status error: " + resp.getStatus() + ", " + resp.getBody());
        }
        JSONObject jsonResponse = JSON.parseObject(resp.getBody());
        Long errcode = jsonResponse.getLong("errcode");
        if(errcode != null && !errcode.equals(0L)){
            //  如果遇到suiteAccessToken失效，那么抛出SuiteAccessTokenExpiredException异常
            if(errcode.equals(42001L) || errcode.equals(40014L)){
                throw new AccessTokenExpiredException(
                        "getDepartmentList accessToken expired" +
                                ", errcode is " + errcode);
            }else{
                throw new HttpException(errcode, jsonResponse.getString("errmsg"));
            }
        }
        return jsonResponse;
    }
}
