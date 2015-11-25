package com.offering.core.dao;

import java.util.List;

import com.offering.bean.sys.PageInfo;
import com.offering.bean.trade.TradeHistory;

/**
 * 交易劣势dao
 * @author surfacepro3
 *
 */
public interface TradeHistoryDao extends BaseDao<TradeHistory>{

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
	String totalAmount(String userId,String type);
}
