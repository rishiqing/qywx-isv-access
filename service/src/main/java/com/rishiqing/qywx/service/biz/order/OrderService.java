package com.rishiqing.qywx.service.biz.order;

import com.rishiqing.qywx.dao.model.order.QywxOrderDO;

/**
 * @author Wallace Mao
 * Date: 2019-02-14 15:08
 */
public interface OrderService {
    QywxOrderDO fetchAndSaveQywxOrder(String orderId);

    QywxOrderDO changeOrder(String oldOrderId, String newOrderId);

    void postChargeEvent(String orderId);

    void postRefundEvent(String orderId);

    void doChargeByOrder(String orderId);

    void doRefundByOrder(String orderId);
}
