package com.rishiqing.qywx.service.biz.alert;

import com.rishiqing.qywx.service.biz.alert.model.TodoAlertVO;

/**
 * @author Wallace Mao
 * Date: 2018-04-10 15:24
 */
public interface AlertService {
    public void setAlert(String corpId, TodoAlertVO todoAlertVO);
    public void removeAlert(String corpId, TodoAlertVO todoAlertVO);
}
