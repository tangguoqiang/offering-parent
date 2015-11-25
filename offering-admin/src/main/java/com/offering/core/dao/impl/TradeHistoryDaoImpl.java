package com.offering.core.dao.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.offering.bean.sys.PageInfo;
import com.offering.bean.sys.ParamInfo;
import com.offering.bean.trade.TradeHistory;
import com.offering.constant.GloabConstant;
import com.offering.core.dao.TradeHistoryDao;

/**
 * 交易历史dao实现
 * @author surfacepro3
 *
 */
@Repository
public class TradeHistoryDaoImpl extends BaseDaoImpl<TradeHistory> implements TradeHistoryDao{

	/**
	 * 打赏历史纪录
	 * @param userId
	 * @param type
	 * @return
	 */
	public List<TradeHistory> rewardHistory(String userId,String type,PageInfo pageInfo){
		StringBuilder sql = new StringBuilder(512);
		sql.append("SELECT T1.tradeTime,T1.amount,T2.nickname payerName,T2.url payerUrl,")
		   .append("T3.nickname payeeName,T3.url payeeUrl ")
		   .append("FROM TRADE_HISTORY T1 ")
		   .append("INNER JOIN USER_INFO T2 ON T2.ID=T1.payer ")
		   .append("INNER JOIN USER_INFO T3 ON T3.ID=T1.payee ")
		   .append("WHERE T1.type=? ");
		if(GloabConstant.USER_TYPE_GREATER.equals(type)){
			sql.append("AND T1.payee=? ");
		}else{
			sql.append("AND T1.payer=? ");
		}
		sql.append("ORDER BY T1.tradeTime DESC ");
		ParamInfo paramInfo = new ParamInfo(2);
		paramInfo.setTypeAndData(Types.CHAR, GloabConstant.TRADE_TYPE_1);
		paramInfo.setTypeAndData(Types.BIGINT, userId);
		return getRecords(sql.toString(), paramInfo,pageInfo, TradeHistory.class);
	}
	
	/**
	 * 获取总金额
	 * @param userId
	 * @param type
	 * @return
	 */
	public String totalAmount(String userId,String type){
		StringBuilder sql = new StringBuilder(512);
		sql.append("SELECT SUM(T1.amount) amount ")
		   .append("FROM TRADE_HISTORY T1 ")
		   .append("WHERE T1.type=? ");
		if(GloabConstant.USER_TYPE_GREATER.equals(type)){
			sql.append("AND T1.payee=? ")
			   .append("GROUP BY T1.payee ");
		}else{
			sql.append("AND T1.payer=? ")
			   .append("GROUP BY T1.payer ");
		}
		ParamInfo paramInfo = new ParamInfo(2);
		paramInfo.setTypeAndData(Types.CHAR, GloabConstant.TRADE_TYPE_1);
		paramInfo.setTypeAndData(Types.BIGINT, userId);
		TradeHistory th = getRecord(sql.toString(), paramInfo, TradeHistory.class);
		if(th != null)
			return th.getAmount();
		return "0";
	}
}
