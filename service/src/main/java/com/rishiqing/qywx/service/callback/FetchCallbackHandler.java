package com.rishiqing.qywx.service.callback;

import java.util.Map;

/**
 * @author Wallace Mao
 * Date: 2018-03-01 18:35
 */
public interface FetchCallbackHandler {
    public void handleFetchCorp(String permanentCode);
    public void handleChangeContactCreateDept(Map map);
    public void handleChangeContactUpdateDept(Map map);
    public void handleChangeContactDeleteDept(Map map);
    public void handleChangeContactCreateUser(Map map);
    public void handleChangeContactUpdateUser(Map map);
    public void handleChangeContactDeleteUser(Map map);
}
