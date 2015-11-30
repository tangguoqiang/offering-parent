package com.offering.core.dao;

import com.offering.bean.trade.TradeRecord;

/**
 * 交易记录dao
 * @author surfacepro3
 *
 */
public interface TradeRecordDao extends BaseDao<TradeRecord>{

	/**
	 * 根据订单号获取订单记录
	 * @param tradeNo
	 * @return
	 */
	TradeRecord getRecByTradeNo(String tradeNo);
	
	/**
	 * 更新交易记录状态
	 * @param tradeNo
	 */
	void updateStatus(String tradeNo);
}
