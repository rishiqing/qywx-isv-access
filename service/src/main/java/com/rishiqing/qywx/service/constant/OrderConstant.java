package com.rishiqing.qywx.service.constant;

/**
 * 订单相关的常量，与企业微信保持一致
 * @author Wallace Mao
 * Date: 2019-02-14 16:56
 */
public class OrderConstant {
    public static final Long USER_COUNT_UNLIMITED = 4294967295L;
    public static final Long ORDER_STATUS_NOT_PAID = 0L;
    public static final Long ORDER_STATUS_PAID = 1L;
    public static final Long ORDER_STATUS_CLOSED = 2L;
    public static final Long ORDER_STATUS_EXPIRED_NOT_PAID = 3L;
    public static final Long ORDER_STATUS_REFUNDING = 4L;
    public static final Long ORDER_STATUS_REFUND_DONE = 5L;

    public static final Long ORDER_TYPE_COMMON = 0L;
    public static final Long ORDER_TYPE_EXPANSION = 1L;
    public static final Long ORDER_TYPE_RENEWAL = 2L;
    public static final Long ORDER_TYPE_EDITION_CHANGE = 3L;

    public static final String ORDER_PUSH_STATUS_INIT= "init";
    public static final String ORDER_PUSH_STATUS_PENDING = "pending";
    public static final String ORDER_PUSH_STATUS_SUCCESS = "success";
    public static final String ORDER_PUSH_STATUS_PENDING_REFUND = "pending_refund";
    public static final String ORDER_PUSH_STATUS_SUCCESS_REFUND = "success_refund";

    public static final String ORDER_CORP_CHARGE_STATUS_OK = "ok";

    public static final String DEFAULT_CHARGE_RSQ_PRODUCT_NAME = "BASE_ENTERPRISE";
}
