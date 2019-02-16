package com.rishiqing.qywx.service.event.message;

/**
 * @author Wallace Mao
 * Date: 2019-02-14 16:38
 */
public class CorpRefundMessage {
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "CorpRefundMessage{" +
                "orderId='" + orderId + '\'' +
                '}';
    }
}
