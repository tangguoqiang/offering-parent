package com.offering.core.service;

import java.math.BigDecimal;
import java.util.List;

import com.offering.bean.sys.PageInfo;
import com.offering.bean.trade.TradeHistory;

/**
 * 交易service
 * @author surfacepro3
 *
 */
public interface TradeService {

	/**
	 * 打赏历史纪录
	 * @param userId
	 * @param type
	 * @return
	 */
	List<TradeHistory> rewardHistory(String userId,String type,PageInfo pageInfo);
	
	/**
	 * 获取总金额
	 * @param userId
	 * @param type
	 * @return
	 */
	BigDecimal totalAmount(String userId,String type);
}
