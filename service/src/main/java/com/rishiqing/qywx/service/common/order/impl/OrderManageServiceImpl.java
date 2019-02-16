package com.rishiqing.qywx.service.common.order.impl;

import com.rishiqing.qywx.dao.mapper.corp.CorpChargeStatusDao;
import com.rishiqing.qywx.dao.mapper.order.OrderRsqPushEventDao;
import com.rishiqing.qywx.dao.mapper.order.OrderSpecItemDao;
import com.rishiqing.qywx.dao.mapper.order.QywxOrderDao;
import com.rishiqing.qywx.dao.model.corp.CorpChargeStatusDO;
import com.rishiqing.qywx.dao.model.order.OrderRsqPushEventDO;
import com.rishiqing.qywx.dao.model.order.OrderSpecItemDO;
import com.rishiqing.qywx.dao.model.order.QywxOrderDO;
import com.rishiqing.qywx.service.common.order.OrderManageService;
import com.rishiqing.qywx.service.constant.OrderConstant;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Wallace Mao
 * Date: 2018-11-08 0:03
 */
public class OrderManageServiceImpl implements OrderManageService {
    @Autowired
    private OrderRsqPushEventDao orderRsqPushEventDao;
    @Autowired
    private QywxOrderDao qywxOrderDao;
    @Autowired
    private OrderSpecItemDao orderSpecItemDao;
    @Autowired
    private CorpChargeStatusDao corpChargeStatusDao;

    @Override
    public QywxOrderDO getQywxOrderByOrderId(String orderId) {
        return qywxOrderDao.getQywxOrderByOrderid(orderId);
    }

    @Override
    public QywxOrderDO getLatestPendingQywxOrderByCorpId(String corpId) {
        List<QywxOrderDO> list = qywxOrderDao.listQywxOrderByPaidCorpidAndChargeStatusWithLimit(
                corpId, OrderConstant.ORDER_PUSH_STATUS_PENDING, 1L);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public QywxOrderDO getLastChargedQywxOrderByCorpIdAndExcludeId(String corpId, Long excludeId) {
        List<QywxOrderDO> list = qywxOrderDao.listQywxOrderByPaidCorpidAndChargeStatusAndIdNotEqualsWithLimit(
                corpId, OrderConstant.ORDER_PUSH_STATUS_SUCCESS, excludeId, 1L);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public OrderSpecItemDO getOrderSpecItemByGoodsCodeAndItemCode(String goodsCode, String itemCode){
        return orderSpecItemDao.getOrderSpecItemByGoodsCodeAndItemCode(goodsCode, itemCode);
    }

    @Override
    public OrderSpecItemDO getOrderSpecItemByItemCode(String itemCode) {
        return orderSpecItemDao.getOrderSpecItemByItemCode(itemCode);
    }

    @Override
    public void saveOrUpdateQywxOrder(QywxOrderDO qywxOrder) {
        qywxOrderDao.saveOrUpdateQywxOrder(qywxOrder);
    }

    @Override
    public void saveOrUpdateCorpChargeStatus(CorpChargeStatusDO corpChargeStatusDO) {
        corpChargeStatusDao.saveOrUpdateCorpChargeStatus(corpChargeStatusDO);
    }

    @Override
    public void saveOrUpdateOrderRsqPushEvent(OrderRsqPushEventDO rsqPushEvent) {
        orderRsqPushEventDao.saveOrUpdateOrderRsqPushEvent(rsqPushEvent);
    }
}
