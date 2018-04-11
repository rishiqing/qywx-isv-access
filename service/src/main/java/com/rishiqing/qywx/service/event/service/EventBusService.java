package com.rishiqing.qywx.service.event.service;

/**
 * @author Wallace Mao
 * Date: 2018-04-11 22:20
 */
public interface EventBusService {
    void sendToFetchCorpAll(String corpId, String permanentCode);
}
