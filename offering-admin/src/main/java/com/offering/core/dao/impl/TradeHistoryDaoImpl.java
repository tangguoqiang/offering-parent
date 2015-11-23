package com.offering.core.dao.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;

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
	public List<TradeHistory> rewardHistory(String userId,String type){
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
		ParamInfo paramInfo = new ParamInfo(2);
		paramInfo.setTypeAndData(Types.CHAR, GloabConstant.TRADE_TYPE_1);
		paramInfo.setTypeAndData(Types.BIGINT, userId);
		return getRecords(sql.toString(), paramInfo, TradeHistory.class);
	}
}
