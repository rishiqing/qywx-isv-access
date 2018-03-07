package com.rishiqing.qywx.service.callback;

import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-03-01 18:35
 */
public interface PushCallbackHandler {
    public void handleCreateCorp(String corpId);
    public void handleCreateDept(String corpId, Map contentMap);
    public void handleUpdateDept(String corpId, Map contentMap);
    public void handleDeleteDept(String corpId, Map contentMap);
    public void handleCreateUser(String corpId, Map contentMap);
    public void handleUpdateUser(String corpId, Map contentMap);
    public void handleDeleteUser(String corpId, Map contentMap);
}
