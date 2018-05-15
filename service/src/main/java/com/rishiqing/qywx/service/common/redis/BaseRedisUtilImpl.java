package com.rishiqing.qywx.service.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author Wallace Mao
 * Date: 2018-05-15 10:17
 */
public class BaseRedisUtilImpl implements RedisUtil {
    private JedisPool jedisPool;

    public BaseRedisUtilImpl(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public String getByKey(String key) {
        String result = null;
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            if (jedis.exists(key)) {
                result =  jedis.get(key);
            }
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    @Override
    public String setKey(String key, String value) {
        return this.setKey(key, value, 0);
    }

    @Override
    public String setKey(String key, String value, Integer expireSeconds) {
        String result = null;
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            result = jedis.set(key, value);
            if (!Integer.valueOf(0).equals(expireSeconds)) {
                jedis.expire(key, expireSeconds);
            }
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

        return result;
    }
}
