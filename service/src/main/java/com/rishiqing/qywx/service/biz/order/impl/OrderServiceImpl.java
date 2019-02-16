package com.rishiqing.qywx.service.biz.order.impl;

import com.alibaba.fastjson.JSONObject;
import com.rishiqing.common.util.http.HttpUtilRsqSync;
import com.rishiqing.qywx.dao.model.corp.CorpChargeStatusDO;
import com.rishiqing.qywx.dao.model.order.OrderRsqPushEventDO;
import com.rishiqing.qywx.dao.model.order.OrderSpecItemDO;
import com.rishiqing.qywx.dao.model.order.QywxOrderDO;
import com.rishiqing.qywx.service.biz.order.OrderService;
import com.rishiqing.qywx.service.common.corp.CorpManageService;
import com.rishiqing.qywx.service.common.corp.CorpSuiteManageService;
import com.rishiqing.qywx.service.common.isv.GlobalSuite;
import com.rishiqing.qywx.service.common.isv.SuiteTokenManageService;
import com.rishiqing.qywx.service.common.order.OrderManageService;
import com.rishiqing.qywx.service.constant.OrderConstant;
import com.rishiqing.qywx.service.event.service.EventBusService;
import com.rishiqing.qywx.service.model.corp.CorpEditionVO;
import com.rishiqing.qywx.service.model.corp.CorpSuiteVO;
import com.rishiqing.qywx.service.model.corp.CorpVO;
import com.rishiqing.qywx.service.model.isv.SuiteTokenVO;
import com.rishiqing.qywx.service.util.http.HttpUtil;
import com.rishiqing.qywx.service.util.http.converter.Json2BeanConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author Wallace Mao
 * Date: 2019-02-14 15:08
 */
public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LoggerFactory.getLogger("CONSOLE_LOGGER");

    @Autowired
    private GlobalSuite suite;
    @Autowired
    private SuiteTokenManageService suiteTokenManageService;
    @Autowired
    private CorpSuiteManageService corpSuiteManageService;
    @Autowired
    private HttpUtil httpUtil;
    @Autowired
    private HttpUtilRsqSync httpUtilRsqSync;
    @Autowired
    private OrderManageService orderManageService;
    @Autowired
    private EventBusService eventBusService;
    @Autowired
    private CorpManageService corpManageService;

    /**
     * 根据orderId读取订单，同时将订单保存到数据库中
     * @param orderId
     * @return
     */
    @Override
    public QywxOrderDO fetchAndSaveQywxOrder(String orderId) {
        String suiteKey = suite.getSuiteKey();
        SuiteTokenVO suiteTokenVO = suiteTokenManageService.getSuiteToken(suiteKey);
        JSONObject json = httpUtil.getOrder(suiteTokenVO, orderId);
        QywxOrderDO qywxOrder = Json2BeanConverter.generateQywxOrder(json);
        qywxOrder.setChargeStatus(OrderConstant.ORDER_PUSH_STATUS_INIT);
        orderManageService.saveOrUpdateQywxOrder(qywxOrder);
        return qywxOrder;
    }

    /**
     * 修改订单价格等属性后，会生成新订单，此时原订单号作废
     * @param oldOrderId
     * @return
     */
    @Override
    public QywxOrderDO changeOrder(String oldOrderId, String newOrderId) {
        QywxOrderDO oldOrder = orderManageService.getQywxOrderByOrderId(oldOrderId);
        if (oldOrder == null) {
            return null;
        }
        oldOrder.setChangedOrderid(newOrderId);
        orderManageService.saveOrUpdateQywxOrder(oldOrder);
        return oldOrder;
    }

    @Override
    public void postChargeEvent(String orderId) {
        eventBusService.sendToChargeCorp(orderId);
    }

    @Override
    public void postRefundEvent(String orderId) {
        eventBusService.sendToRefundCorp(orderId);
    }

    /**
     * 充值的主方法，流程如下：
     * 1. 检查qywxOrderDO的order_status，只有当status为“1-已支付”状态的时候，才走充值流程
     * 2. 保存OrderRsqPushEventDO，初始状态为pending
     * 3. 发送后台接口进行充值，充值成功后返回，失败后做报错处理，单独日志打印
     * 4. 更新OrderRsqPushEventDO的状态为success
     * 6. 保存CorpChargeStatusDO
     * @param orderId
     */
    @Override
    public void doChargeByOrder(String orderId) {
        QywxOrderDO qywxOrder = orderManageService.getQywxOrderByOrderId(orderId);
        if (qywxOrder == null) {
            logger.error("qywxOrder not found: orderId is " + orderId);
            return;
        }
        if(!String.valueOf(OrderConstant.ORDER_STATUS_PAID).equals(qywxOrder.getOrderStatus())) {
            logger.error("qywxOrder is not in ORDER_STATUS_PAID status: orderId is " + orderId
                    + ", qywxOrder: " + qywxOrder.getOrderStatus());
            return;
        }
        qywxOrder.setChargeStatus(OrderConstant.ORDER_PUSH_STATUS_PENDING);
        orderManageService.saveOrUpdateQywxOrder(qywxOrder);

        // 开始走充值流程
        Boolean isSuccess = chargeByQywxOrder(qywxOrder);

        if (isSuccess) {
            qywxOrder.setChargeStatus(OrderConstant.ORDER_PUSH_STATUS_SUCCESS);
            qywxOrder.setChargeTime(new Date());
            orderManageService.saveOrUpdateQywxOrder(qywxOrder);
        }
    }

    /**
     * 退款流程：
     * 找到除了退款订单外，上一条已付款的订单。
     * 1.  如果找到该订单，那么退回该订单
     * 2.  如果找不到该订单，那么就讲到期时间设置为当前时间
     * @param orderId
     */
    @Override
    public void doRefundByOrder(String orderId) {
        QywxOrderDO refundOrder = orderManageService.getQywxOrderByOrderId(orderId);
        if (refundOrder == null) {
            logger.error("refundOrder not found: orderId is " + orderId);
            return;
        }
        if(!String.valueOf(OrderConstant.ORDER_STATUS_REFUND_DONE).equals(refundOrder.getOrderStatus())) {
            logger.error("refundOrder is not in ORDER_STATUS_REFUND_DONE status: orderId is " + orderId
                    + ", refundOrder: " + refundOrder.getOrderStatus());
            return;
        }
        String corpId = refundOrder.getPaidCorpid();
        refundOrder.setChargeStatus(OrderConstant.ORDER_PUSH_STATUS_PENDING_REFUND);
        orderManageService.saveOrUpdateQywxOrder(refundOrder);

        // 查找新的服务到期时间，如果有上一条付款记录，那么就按照上一条付款记录的到期时间，否则到期时间就是现在时间
        QywxOrderDO lastOrder = orderManageService.getLastChargedQywxOrderByCorpIdAndExcludeId(corpId, refundOrder.getId());
        Boolean isSuccess;
        if (lastOrder != null) {
            isSuccess = chargeByQywxOrder(lastOrder);
        } else {
            isSuccess = rollbackToFreeVersion(refundOrder);
        }

        if (isSuccess) {
            refundOrder.setChargeStatus(OrderConstant.ORDER_PUSH_STATUS_SUCCESS_REFUND);
            refundOrder.setChargeTime(null);
            orderManageService.saveOrUpdateQywxOrder(refundOrder);
        }
    }

    private Boolean chargeByQywxOrder(QywxOrderDO order) {
        // 读取corp
        CorpVO corp = corpManageService.getCorpByCorpId(order.getPaidCorpid());
        //  如果corp不存在，或者corp的rsqId不存在，那么说明没有同步完成，这种情况下暂时先不充值，等corp同步成功之后再做补偿
        if(corp == null || corp.getRsqId() == null){
            return false;
        }
        // 从企业微信获取最新的订单消息，免去自己计算公司购买的人数和到期时间的麻烦
        CorpEditionVO corpEdition = fetchCurrentCorpEdition(corp);

        // 保存OrderRsqPushEventVO
        OrderRsqPushEventDO rsqPushEvent = qywxOrder2OrderRsqPushEvent(order, corpEdition);
        rsqPushEvent.setStatus(OrderConstant.ORDER_PUSH_STATUS_PENDING);
        rsqPushEvent.setRsqTeamId(Long.valueOf(corp.getRsqId()));
        orderManageService.saveOrUpdateOrderRsqPushEvent(rsqPushEvent);

        OrderSpecItemDO specItemVO = orderManageService.getOrderSpecItemByItemCode(
                order.getEditionId());
        String rsqProductName = OrderConstant.DEFAULT_CHARGE_RSQ_PRODUCT_NAME;
        if (specItemVO != null) {
            rsqProductName = specItemVO.getRsqProductName();
        }
        //发送后台接口进行充值
        httpUtilRsqSync.doCharge(
                suite.getRsqAppToken(), rsqProductName, rsqPushEvent);

        // 更新OrderRsqPushEventDO的状态为success
        rsqPushEvent.setStatus(OrderConstant.ORDER_PUSH_STATUS_SUCCESS);
        orderManageService.saveOrUpdateOrderRsqPushEvent(rsqPushEvent);

        // 保存CorpChargeStatusDO
        CorpChargeStatusDO corpChargeStatusDO = qywxOrder2CorpChargeStatus(rsqPushEvent);
        corpChargeStatusDO.setStatus(OrderConstant.ORDER_CORP_CHARGE_STATUS_OK);
        // 计算当前人数，先暂时指定公司总人数为1，保证不超员，以后改为每天查人数来更新这个字段
        corpChargeStatusDO.setTotalQuantity(1L);
        orderManageService.saveOrUpdateCorpChargeStatus(corpChargeStatusDO);

        return true;
    }

    private CorpEditionVO fetchCurrentCorpEdition(CorpVO corp) {
        SuiteTokenVO suiteToken = suiteTokenManageService.getSuiteToken(suite.getSuiteKey());
        CorpSuiteVO corpSuite = corpSuiteManageService.getCorpSuite(suite.getSuiteKey(), corp.getCorpId());
        JSONObject json = httpUtil.getCorpAuthInfo(suiteToken, corpSuite);
        return Json2BeanConverter.generateCorpEdition(json);
    }

    /**
     * 返回值表示是否充值成功
     * @param refundOrder
     * @return
     */
    private Boolean rollbackToFreeVersion(QywxOrderDO refundOrder) {
        CorpVO corp = corpManageService.getCorpByCorpId(refundOrder.getPaidCorpid());
        if(corp == null || corp.getRsqId() == null){
            return false;
        }

        Long serviceStopTime = new Date().getTime();
        Long userCount = OrderConstant.USER_COUNT_UNLIMITED;

        // 保存OrderRsqPushEventVO
        OrderRsqPushEventDO rsqPushEvent = new OrderRsqPushEventDO();
        rsqPushEvent.setStatus(OrderConstant.ORDER_PUSH_STATUS_PENDING_REFUND);
        rsqPushEvent.setSuiteKey(refundOrder.getSuiteid());
        rsqPushEvent.setCorpId(refundOrder.getPaidCorpid());
        rsqPushEvent.setQuantity(userCount);
        rsqPushEvent.setServiceStopTime(serviceStopTime);
        rsqPushEvent.setRsqTeamId(Long.valueOf(corp.getRsqId()));
        orderManageService.saveOrUpdateOrderRsqPushEvent(rsqPushEvent);

        //发送后台接口进行充值
        httpUtilRsqSync.doCharge(
                suite.getRsqAppToken(), OrderConstant.DEFAULT_CHARGE_RSQ_PRODUCT_NAME, rsqPushEvent);

        // 更新OrderRsqPushEventDO的状态为success
        rsqPushEvent.setStatus(OrderConstant.ORDER_PUSH_STATUS_SUCCESS);
        orderManageService.saveOrUpdateOrderRsqPushEvent(rsqPushEvent);

        CorpChargeStatusDO corpChargeStatusDO = getRefundCorpChargeStatus(
                refundOrder, serviceStopTime, userCount);
        corpChargeStatusDO.setStatus(OrderConstant.ORDER_CORP_CHARGE_STATUS_OK);
        orderManageService.saveOrUpdateCorpChargeStatus(corpChargeStatusDO);

        return true;
    }

    private OrderRsqPushEventDO qywxOrder2OrderRsqPushEvent(QywxOrderDO qywxOrderDO, CorpEditionVO corpEdition) {
        OrderRsqPushEventDO rsqPushEventDO = new OrderRsqPushEventDO();
        rsqPushEventDO.setCorpId(qywxOrderDO.getPaidCorpid());
        rsqPushEventDO.setOrderId(qywxOrderDO.getOrderid());
        rsqPushEventDO.setSuiteKey(qywxOrderDO.getSuiteid());
        rsqPushEventDO.setQuantity(corpEdition.getUserLimit());
        rsqPushEventDO.setServiceStopTime(getServiceStopTime(corpEdition.getExpiredTime()));
        return rsqPushEventDO;
    }
    private CorpChargeStatusDO qywxOrder2CorpChargeStatus(OrderRsqPushEventDO rsqPushEventDO) {
        CorpChargeStatusDO corpChargeStatus = new CorpChargeStatusDO();
        corpChargeStatus.setCorpId(rsqPushEventDO.getCorpId());
        corpChargeStatus.setSuiteKey(rsqPushEventDO.getSuiteKey());
        corpChargeStatus.setCurrentOrderId(rsqPushEventDO.getOrderId());
        corpChargeStatus.setCurrentSubQuantity(rsqPushEventDO.getQuantity());
        corpChargeStatus.setCurrentServiceStopTime(rsqPushEventDO.getServiceStopTime());
        return corpChargeStatus;
    }

    private CorpChargeStatusDO getRefundCorpChargeStatus(QywxOrderDO qywxOrderDO, Long serviceStopTime, Long userCount) {
        CorpChargeStatusDO corpChargeStatus = new CorpChargeStatusDO();
        corpChargeStatus.setCorpId(qywxOrderDO.getPaidCorpid());
        corpChargeStatus.setSuiteKey(qywxOrderDO.getSuiteid());
        corpChargeStatus.setCurrentSubQuantity(userCount);
        corpChargeStatus.setCurrentServiceStopTime(serviceStopTime);
        return corpChargeStatus;
    }

    private Long getServiceStopTime(Long seconds) {
        return 1000 * seconds;
    }
}
