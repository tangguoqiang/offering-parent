package com.offering.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.offering.bean.sys.PageInfo;
import com.offering.bean.trade.Account;
import com.offering.bean.trade.TradeHistory;
import com.offering.core.dao.TradeAccountDao;
import com.offering.core.dao.TradeHistoryDao;
import com.offering.core.dao.TradeRecordDao;
import com.offering.core.service.TradeService;

/**
 * 交易service实现
 * @author surfacepro3
 *
 */
@Service
public class TradeServiceImpl implements TradeService{

	@Autowired
	private TradeAccountDao accountDao;
	
	@Autowired
	private TradeRecordDao tradeDao;
	
	@Autowired
	private TradeHistoryDao tradeHistoryDao;
	
	/**
	 * 查询账户信息
	 * @param account
	 * @param page
	 * @return
	 */
	public List<Account> listAccounts(Account account,PageInfo page){
		return accountDao.listAccounts(account, page);
	}
	public long getAccountCount(Account account){
		return accountDao.getAccountCount(account);
	}
	

	/**
	 * 交易历史纪录
	 * @param th
	 * @param pageInfo
	 * @return
	 */
	public List<TradeHistory> listTradeHistory(TradeHistory th,PageInfo pageInfo){
		return tradeHistoryDao.listTradeHistory(th, pageInfo);
	}
	public long getThCount(TradeHistory th){
		return tradeHistoryDao.getThCount(th);
	}
}
