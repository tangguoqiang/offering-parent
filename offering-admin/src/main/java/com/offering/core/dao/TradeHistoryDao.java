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
	 * 交易历史纪录
	 * @param th
	 * @param pageInfo
	 * @return
	 */
	List<TradeHistory> listTradeHistory(TradeHistory th,PageInfo pageInfo);
	long getThCount(TradeHistory th);
}
