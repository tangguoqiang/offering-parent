package com.offering.core.dao;

import java.util.List;

import com.offering.bean.sys.PageInfo;
import com.offering.bean.trade.Account;

/**
 * 交易账户dao
 * @author surfacepro3
 *
 */
public interface TradeAccountDao extends BaseDao<Account>{

	/**
	 * 查询账户信息
	 * @param account
	 * @param page
	 * @return
	 */
	List<Account> listAccounts(Account account,PageInfo page);
	long getAccountCount(Account account);
	
}
