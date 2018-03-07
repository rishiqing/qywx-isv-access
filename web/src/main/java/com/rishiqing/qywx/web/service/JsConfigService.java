package com.rishiqing.qywx.web.service;

import com.rishiqing.qywx.web.exception.JsConfigException;

import java.util.Map;

public interface JsConfigService {
    /**
     * 获取jsapiSigature
     * @param url
     * @param corpId
     * @return
     */
    Map<String, Object> getJsapiSignature(String url, String corpId);

    void refreshJsapiTicket(String corpId);
}
