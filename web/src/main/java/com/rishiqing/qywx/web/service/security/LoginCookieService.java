package com.rishiqing.qywx.web.service.security;

/**
 * @author Wallace Mao
 * Date: 2018-06-19 15:07
 */
public interface LoginCookieService {
    Boolean checkSignature(String signature, Long type, String code, String corpId, Long timestamp, String nonce);
}
