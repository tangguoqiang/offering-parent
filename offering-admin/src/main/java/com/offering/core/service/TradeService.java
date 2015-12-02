package com.offering.core.service;

import java.util.List;

import com.offering.bean.sys.PageInfo;
import com.offering.bean.trade.Account;
import com.offering.bean.trade.TradeHistory;

/**
 * 交易service
 * @author surfacepro3
 *
 */
public interface TradeService {
	
	/**
	 * 查询账户信息
	 * @param account
	 * @param page
	 * @return
	 */
	List<Account> listAccounts(Account account,PageInfo page);
	long getAccountCount(Account account);

	/**
	 * 交易历史纪录
	 * @param th
	 * @param pageInfo
	 * @return
	 */
	List<TradeHistory> listTradeHistory(TradeHistory th,PageInfo pageInfo);
	long getThCount(TradeHistory th);
}
