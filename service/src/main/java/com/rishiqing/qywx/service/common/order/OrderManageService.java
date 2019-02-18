package com.rishiqing.qywx.service.common.order;

import com.rishiqing.qywx.dao.model.corp.CorpChargeStatusDO;
import com.rishiqing.qywx.dao.model.order.OrderRsqPushEventDO;
import com.rishiqing.qywx.dao.model.order.OrderSpecItemDO;
import com.rishiqing.qywx.dao.model.order.QywxOrderDO;

/**
 * @author Wallace Mao
 * Date: 2019-02-14 15:01
 */
public interface OrderManageService {
    QywxOrderDO getQywxOrderByOrderId(String orderId);

    QywxOrderDO getLatestPendingQywxOrderByCorpId(String corpId);

    QywxOrderDO getLastChargedQywxOrderByCorpIdAndExcludeId(String corpId, Long excludeId);

    OrderSpecItemDO getOrderSpecItemByGoodsCodeAndItemCode(String goodsCode, String itemCode);

    OrderSpecItemDO getOrderSpecItemByItemCode(String itemCode);

    void saveOrUpdateQywxOrder(QywxOrderDO qywxOrder);

    void saveOrUpdateCorpChargeStatus(CorpChargeStatusDO corpChargeStatusDO);

    CorpChargeStatusDO getCorpChargeStatusByCorpId(String corpId);

    void saveOrUpdateOrderRsqPushEvent(OrderRsqPushEventDO rsqPushEvent);
}
