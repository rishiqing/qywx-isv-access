package com.rishiqing.qywx.web.util.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.qywx.web.exception.HttpException;

public class HttpUtil {
    public static JSONObject getSuiteToken(String suiteKey, String suiteSecret, String suiteTicket) throws UnirestException, HttpException {
        JSONObject params = new JSONObject();
        params.put("suite_id", suiteKey);
        params.put("suite_secret", suiteSecret);
        params.put("suite_ticket", suiteTicket);
        HttpResponse<String> resp =
                Unirest.post("https://qyapi.weixin.qq.com/cgi-bin/service/get_suite_token")
                        .body(params.toString())
                        .asString();
        if(resp.getStatus() > 200){
            throw new HttpException("http request status error: " + resp.getStatus() + ", " + resp.getBody());
        }
        System.out.println("------body----" + resp.getBody());
        JSONObject jsonResponse = JSON.parseObject(resp.getBody());
        Long errcode = jsonResponse.getLong("errcode");
        if(errcode != null && !errcode.equals(0L)){
            throw new HttpException("http request call error, errcode: " +
                    errcode + ", errmsg: " + jsonResponse.getString("errmsg"));
        }
        return jsonResponse;
    }
}
