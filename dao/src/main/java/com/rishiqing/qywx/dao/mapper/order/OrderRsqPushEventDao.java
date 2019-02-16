package com.rishiqing.qywx.dao.mapper.order;

import com.rishiqing.qywx.dao.model.order.OrderRsqPushEventDO;
import org.springframework.stereotype.Repository;

@Repository("orderRsqPushEventDao")
public interface OrderRsqPushEventDao {

	/**
	 * 保存orderRsqPushEventDO
	 * @param orderRsqPushEventDO
	 */
	public void saveOrUpdateOrderRsqPushEvent(OrderRsqPushEventDO orderRsqPushEventDO);
}

