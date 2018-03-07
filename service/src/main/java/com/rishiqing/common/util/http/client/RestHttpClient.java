package com.rishiqing.common.util.http.client;

import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.rishiqing.common.exception.HttpException;

import java.util.Map;

public interface RestHttpClient {
    /**
     * post方法
     * @param path
     * @param queryMap
     * @param fieldMap
     * @param body
     * @param options
     * @return
     * @throws UnirestException
     * @throws HttpException
     */
    JSONObject post(String path, Map<String, Object> queryMap, Map<String, Object> fieldMap, String body, Map<String, Object> options);

    /**
     * get方法
     * @param path
     * @param queryMap
     * @param options
     * @return
     * @throws UnirestException
     * @throws HttpException
     */
    JSONObject get(String path, Map<String, Object> queryMap, Map<String, Object> options);
}
