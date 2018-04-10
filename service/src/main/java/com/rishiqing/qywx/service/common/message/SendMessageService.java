package com.rishiqing.qywx.service.common.message;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-04-10 10:50
 */
public interface SendMessageService {
    public void sendCorpMessage(String corpId, Map map);
}
