package com.rishiqing.qywx.service.common.redis;

/**
 * @author Wallace Mao
 * Date: 2018-05-14 22:16
 */
public interface RedisUtil {
    public String getByKey(String key);

    public String setKey(String key, String value);

    public String setKey(String key, String value, Integer expireSeconds);
}
