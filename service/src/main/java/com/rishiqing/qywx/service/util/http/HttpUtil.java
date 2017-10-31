package com.rishiqing.qywx.service.util.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.qywx.service.exception.HttpException;
import com.rishiqing.qywx.service.exception.SuiteAccessTokenExpiredException;

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
            throw new HttpException("http request getSuiteToken status error: " + resp.getStatus() + ", " + resp.getBody());
        }
        JSONObject jsonResponse = JSON.parseObject(resp.getBody());
        Long errcode = jsonResponse.getLong("errcode");
        if(errcode != null && !errcode.equals(0L)){
            throw new HttpException(errcode, jsonResponse.getString("errmsg"));
        }
        return jsonResponse;
    }

    public static JSONObject getPermanentCode(String suiteKey, String suiteAccessToken, String authCode) throws UnirestException, HttpException, SuiteAccessTokenExpiredException {
        JSONObject params = new JSONObject();
        params.put("suite_id", suiteKey);
        params.put("auth_code", authCode);
        HttpResponse<String> resp =
                Unirest.post("https://qyapi.weixin.qq.com/cgi-bin/service/get_permanent_code")
                        .queryString("suite_access_token", suiteAccessToken)
                        .body(params.toString())
                        .asString();
        if(resp.getStatus() > 200){
            throw new HttpException("http request getPermanentCode status error: " + resp.getStatus() + ", " + resp.getBody());
        }
        JSONObject jsonResponse = JSON.parseObject(resp.getBody());
        Long errcode = jsonResponse.getLong("errcode");
        if(errcode != null && !errcode.equals(0L)){
            //  如果遇到suiteAccessToken失效，那么抛出SuiteAccessTokenExpiredException异常
            if(errcode.equals(42009L) || errcode.equals(40082L)){
                throw new SuiteAccessTokenExpiredException(
                        "getPermanentCode suiteAccessToken expired, suiteKey is: " + suiteKey +
                        ", errcode is " + errcode);
            }else{
                throw new HttpException(errcode, jsonResponse.getString("errmsg"));
            }
        }
        return jsonResponse;
    }

    public static JSONObject getCorpAuthInfo(
            String suiteKey,
            String suiteAccessToken,
            String corpId,
            String permanentCode) throws UnirestException, HttpException, SuiteAccessTokenExpiredException {
        JSONObject params = new JSONObject();
        params.put("suite_id", suiteKey);
        params.put("auth_corpid", corpId);
        params.put("permanent_code", permanentCode);
        HttpResponse<String> resp =
                Unirest.post("https://qyapi.weixin.qq.com/cgi-bin/service/get_auth_info")
                        .queryString("suite_access_token", suiteAccessToken)
                        .body(params.toString())
                        .asString();
        if(resp.getStatus() > 200){
            throw new HttpException("http request getCorpAuthInfo status error: " + resp.getStatus() + ", " + resp.getBody());
        }
        JSONObject jsonResponse = JSON.parseObject(resp.getBody());
        Long errcode = jsonResponse.getLong("errcode");
        if(errcode != null && !errcode.equals(0L)){
            //  如果遇到suiteAccessToken失效，那么抛出SuiteAccessTokenExpiredException异常
            if(errcode.equals(42009L) || errcode.equals(40082L)){
                throw new SuiteAccessTokenExpiredException(
                        "getCorpAuthInfo suiteAccessToken expired, suiteKey is: " + suiteKey +
                                ", errcode is " + errcode);
            }else{
                throw new HttpException(errcode, jsonResponse.getString("errmsg"));
            }
        }
        return jsonResponse;
    }

    public static JSONObject getCorpAccessToken(
            String suiteKey,
            String suiteAccessToken,
            String corpId,
            String permanentCode) throws UnirestException, HttpException, SuiteAccessTokenExpiredException {
        JSONObject params = new JSONObject();
        params.put("suite_id", suiteKey);
        params.put("auth_corpid", corpId);
        params.put("permanent_code", permanentCode);
        HttpResponse<String> resp =
                Unirest.post("https://qyapi.weixin.qq.com/cgi-bin/service/get_corp_token")
                        .queryString("suite_access_token", suiteAccessToken)
                        .body(params.toString())
                        .asString();
        if(resp.getStatus() > 200){
            throw new HttpException("http request getCorpAccessToken status error: " + resp.getStatus() + ", " + resp.getBody());
        }
        JSONObject jsonResponse = JSON.parseObject(resp.getBody());
        Long errcode = jsonResponse.getLong("errcode");
        if(errcode != null && !errcode.equals(0L)){
            //  如果遇到suiteAccessToken失效，那么抛出SuiteAccessTokenExpiredException异常
            if(errcode.equals(42009L) || errcode.equals(40082L)){
                throw new SuiteAccessTokenExpiredException(
                        "getCorpAccessToken suiteAccessToken expired, suiteKey is: " + suiteKey +
                                ", errcode is " + errcode);
            }else{
                throw new HttpException(errcode, jsonResponse.getString("errmsg"));
            }
        }
        return jsonResponse;
    }
}
