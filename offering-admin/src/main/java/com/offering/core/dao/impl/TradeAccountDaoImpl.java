package com.offering.core.dao.impl;

import java.math.BigDecimal;
import java.sql.Types;

import org.springframework.stereotype.Repository;

import com.offering.bean.sys.ParamInfo;
import com.offering.bean.trade.Account;
import com.offering.constant.GloabConstant;
import com.offering.core.dao.TradeAccountDao;

/**
 * 交易账户dao实现
 * @author surfacepro3
 *
 */
@Repository
public class TradeAccountDaoImpl extends BaseDaoImpl<Account> implements TradeAccountDao{

	/**
	 * 根据用户id获取用户账户
	 * @param userId
	 * @return
	 */
	public Account getRecByUserId(String userId){
		StringBuilder sql = new StringBuilder(256);
		sql.append("SELECT id,balance ")
		   .append("FROM TRADE_ACCOUNT ")
		   .append("WHERE userId=? AND status=? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.BIGINT, userId);
		paramInfo.setTypeAndData(Types.CHAR, GloabConstant.STATUS_EFFECT);
		return getRecord(sql.toString(), paramInfo, Account.class);
	}
	
	/**
	 * 更新账户余额
	 * @param userId
	 * @param balance
	 */
	public void updateBalance(String id,BigDecimal balance){
		StringBuilder sql = new StringBuilder(128);
		sql.append("UPDATE TRADE_ACCOUNT SET balance=?,opTime=? WHERE id=? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.VARCHAR, balance + "");
		paramInfo.setTypeAndData(Types.BIGINT, String.valueOf(System.currentTimeMillis()));
		paramInfo.setTypeAndData(Types.BIGINT, id);
		updateRecord(sql.toString(), paramInfo);
	}
}
