package com.rishiqing.qywx.dao.mapper.order;

import com.rishiqing.qywx.dao.model.order.QywxOrderDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("qywxOrderDao")
public interface QywxOrderDao {

	/**
	 * 保存qywxOrderDO
	 * @param qywxOrderDO
	 */
	void saveOrUpdateQywxOrder(QywxOrderDO qywxOrderDO);

	/**
	 * 根据orderId获取qywxOrderDO
	 * @param orderId
	 * @return
	 */
	QywxOrderDO getQywxOrderByOrderid(@Param("orderid") String orderId);

    List<QywxOrderDO> listQywxOrderByPaidCorpidAndChargeStatusWithLimit(
    		@Param("paidCorpid") String corpId,
			@Param("chargeStatus") String chargeStatus,
			@Param("limit") Long limit);

	List<QywxOrderDO> listQywxOrderByPaidCorpidAndChargeStatusAndIdNotEqualsWithLimit(
			@Param("paidCorpid") String corpId,
			@Param("chargeStatus") String chargeStatus,
			@Param("excludeId") Long excludeId,
			@Param("limit") Long limit);
}

