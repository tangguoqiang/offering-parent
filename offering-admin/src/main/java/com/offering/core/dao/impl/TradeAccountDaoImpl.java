package com.offering.core.dao.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.offering.bean.sys.PageInfo;
import com.offering.bean.sys.ParamInfo;
import com.offering.bean.trade.Account;
import com.offering.constant.GloabConstant;
import com.offering.core.dao.TradeAccountDao;
import com.offering.utils.Utils;

/**
 * 交易账户dao实现
 * @author surfacepro3
 *
 */
@Repository
public class TradeAccountDaoImpl extends BaseDaoImpl<Account> implements TradeAccountDao{

	/**
	 * 查询账户信息
	 * @param account
	 * @param page
	 * @return
	 */
	public List<Account> listAccounts(Account account,PageInfo page){
		StringBuilder sql = new StringBuilder(256);
		sql.append("SELECT T1.id,T1.code,FORMAT(T1.balance,2) balance,T2.nickname name ")
		   .append("FROM TRADE_ACCOUNT T1 ")
		   .append("INNER JOIN USER_INFO T2 ON T2.id=T1.userId ")
		   .append("WHERE T1.status=? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.CHAR, GloabConstant.STATUS_EFFECT);
		if(!Utils.isEmpty(account.getName())){
			sql.append("AND T2.nickname LIKE ? ");
			paramInfo.setTypeAndData(Types.VARCHAR, "%" + account.getName() + "%");
		}
		return getRecords(sql.toString(), paramInfo,page, Account.class);
	}
	
	public long getAccountCount(Account account){
		StringBuilder sql = new StringBuilder(256);
		sql.append("SELECT COUNT(1) ")
		   .append("FROM TRADE_ACCOUNT T1 ")
		   .append("INNER JOIN USER_INFO T2 ON T2.id=T1.userId ")
		   .append("WHERE T1.status=? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.CHAR, GloabConstant.STATUS_EFFECT);
		if(!Utils.isEmpty(account.getName())){
			sql.append("AND T2.nickname LIKE ? ");
			paramInfo.setTypeAndData(Types.VARCHAR, "%" + account.getName() + "%");
		}
		return getCount(sql.toString(), paramInfo);
	}
	
}
