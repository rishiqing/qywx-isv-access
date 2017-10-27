package com.rishiqing.qywx.web.service;

import com.rishiqing.qywx.web.exception.CallbackException;

import java.util.Map;

/**
 * 回调方法中需要使用的方法
 */
public interface CallbackService {
    /**
     * 验证url签名
     * @param signature
     * @param timestamp
     * @param nonce
     * @param content
     * @return
     */
    String verifyUrl(String signature, String timestamp, String nonce, String echoString, String content) throws CallbackException;

    /**
     * 将加密的信息进行解密，解密后的信息存储在map中
     * @param orgMessage
     * @return
     */
    Map decryptMessage(String orgMessage) throws CallbackException;
}
