package com.offering.core.dao;

import com.offering.bean.trade.Account;

/**
 * 交易账户dao
 * @author surfacepro3
 *
 */
public interface TradeAccountDao extends BaseDao<Account>{

	/**
	 * 根据用户id获取用户账户
	 * @param userId
	 * @return
	 */
	Account getRecByUserId(String userId);
	
	/**
	 * 更新账户余额
	 * @param userId
	 * @param balance
	 */
	void updateBalance(String userId,String balance);
}
