package com.rishiqing.qywx.web.service;

import com.rishiqing.qywx.web.exception.CallbackException;

import java.util.Map;

/**
 * 回调方法中需要使用的方法
 */
public interface CallbackService {
    /**
     *  开启接受消息通知验证时使用
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    String verifyUrl(String signature, String timestamp, String nonce, String echoString) throws CallbackException;

    /**
     * 接收普通消息
     * @param signature
     * @param timestamp
     * @param nonce
     * @param body
     * @return
     * @throws CallbackException
     */
    String receiveMessage(String signature, String timestamp, String nonce, String body) throws CallbackException;
}
