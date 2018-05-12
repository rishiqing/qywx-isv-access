package com.rishiqing.qywx.web.service.website;

/**
 * @author Wallace Mao
 * Date: 2018-05-12 15:52
 */
public interface QywxRegisterEventService {
    /**
     *  开启接受消息通知验证时使用
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    String verifyUrl(String signature, String timestamp, String nonce, String echoString);

    /**
     * 接收普通消息
     * @param signature
     * @param timestamp
     * @param nonce
     * @param body
     * @return
     */
    String receiveMessage(String signature, String timestamp, String nonce, String body);

}
