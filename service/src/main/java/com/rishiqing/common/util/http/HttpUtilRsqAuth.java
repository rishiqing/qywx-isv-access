package com.rishiqing.common.util.http;

import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-06-19 17:57
 */
public interface HttpUtilRsqAuth {
    Map<String, String> tokenLogin(String token);
}
