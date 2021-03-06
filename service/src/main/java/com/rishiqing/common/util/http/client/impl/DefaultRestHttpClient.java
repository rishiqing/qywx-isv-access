package com.rishiqing.common.util.http.client.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.rishiqing.common.util.http.client.RestHttpClient;
import com.rishiqing.common.exception.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-02-08 12:35
 */
@SuppressWarnings(value = "unchecked")
public class DefaultRestHttpClient implements RestHttpClient {
    private static final Logger logger = LoggerFactory.getLogger("CONSOLE_LOGGER");

    private static final Map<String, String> HEADERS_DEFAULT = new HashMap<String, String>();
    static {
        HEADERS_DEFAULT.put("Content-Type", "application/json");
    }

    @Override
    public JSONObject post(String path, Map<String, Object> queryMap, Map<String, Object> fieldMap, String body, Map<String, Object> options) {
        logger.debug("http post, path: {}, query: {}, field: {}, body: {}, option: {}",
                path, queryMap, fieldMap, body, options);
        HttpRequestWithBody request = Unirest.post(path);
        //  默认header
        request.headers(HEADERS_DEFAULT);
        if(queryMap != null && !queryMap.isEmpty()){
            request.queryString(queryMap);
        }
        if(fieldMap != null && !fieldMap.isEmpty()){
            request.fields(fieldMap);
        }
        if(body != null){
            request.body(body);
        }
        if(options != null && !options.isEmpty()){
            if(options.containsKey("headers")){
                request.headers((Map<String, String>)options.get("headers"));
            }
        }
        HttpResponse<String> resp = null;
        try {
            resp = request.asString();
            logger.debug("http request status: {}, response body: {}", resp.getStatus(), resp.getBody());
        } catch (UnirestException e) {
            throw new HttpException("http request exception: ", e);
        }
        if(resp.getStatus() > 200){
            throw new HttpException("http post request status error: " + resp.getStatus() + ", " + resp.getBody());
        }
        return JSON.parseObject(resp.getBody());
    }

    @Override
    public JSONObject get(String path, Map<String, Object> queryMap, Map<String, Object> options) {
        logger.debug("http post, path: {}, query: {}, field: {}, body: {}, option: {}",
                path, queryMap, options);
        GetRequest request = Unirest.get(path);
        //  默认header
        request.headers(HEADERS_DEFAULT);
        if(queryMap != null && !queryMap.isEmpty()){
            request.queryString(queryMap);
        }
        if(options != null && !options.isEmpty()){
            if(options.containsKey("headers")){
                request.headers((Map<String, String>)options.get("headers"));
            }
        }
        HttpResponse<String> resp = null;
        try {
            resp = request.asString();
            logger.debug("http request status: {}, response body: {}", resp.getStatus(), resp.getBody());
        } catch (UnirestException e) {
            throw new HttpException("http request exception: ", e);
        }
        if(resp.getStatus() > 200){
            throw new HttpException("http get request status error: " + resp.getStatus() + ", " + resp.getBody());
        }
        return JSON.parseObject(resp.getBody());
    }
}
