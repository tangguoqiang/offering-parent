package com.offering.core.dao.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.offering.bean.sys.PageInfo;
import com.offering.bean.sys.ParamInfo;
import com.offering.bean.trade.TradeHistory;
import com.offering.core.dao.TradeHistoryDao;
import com.offering.utils.Utils;

/**
 * 交易历史dao实现
 * @author surfacepro3
 *
 */
@Repository
public class TradeHistoryDaoImpl extends BaseDaoImpl<TradeHistory> implements TradeHistoryDao{

	/**
	 * 交易历史纪录
	 * @param th
	 * @param pageInfo
	 * @return
	 */
	public List<TradeHistory> listTradeHistory(TradeHistory th,PageInfo pageInfo){
		StringBuilder sql = new StringBuilder(512);
		ParamInfo paramInfo = new ParamInfo();
		sql.append("SELECT T1.tradeNo,T1.tradeTime,FORMAT(T1.amount,2) amount,T2.nickname payerName,")
		   .append("T1.type,T3.nickname payeeName,T4.channel,T1.type ")
		   .append("FROM TRADE_HISTORY T1 ")
		   .append("INNER JOIN USER_INFO T2 ON T2.ID=T1.payer ")
		   .append("INNER JOIN USER_INFO T3 ON T3.ID=T1.payee ")
		   .append("INNER JOIN TRADE_RECORD T4 ON T4.tradeNo=T1.tradeNo ")
		   .append("WHERE 1=1 ");
		
		if(!Utils.isEmpty(th.getPayerName())){
			sql.append("AND T2.nickname LIKE ? ");
			paramInfo.setTypeAndData(Types.VARCHAR, "%" + th.getPayerName() + "%");
		}
		
		if(!Utils.isEmpty(th.getPayeeName())){
			sql.append("AND T3.nickname LIKE ? ");
			paramInfo.setTypeAndData(Types.VARCHAR, "%" + th.getPayeeName() + "%");
		}
		sql.append("ORDER BY T1.tradeTime DESC ");
		
		return getRecords(sql.toString(), paramInfo,pageInfo, TradeHistory.class);
	}
	public long getThCount(TradeHistory th){
		StringBuilder sql = new StringBuilder(512);
		ParamInfo paramInfo = new ParamInfo();
		sql.append("SELECT COUNT(1) ")
		   .append("FROM TRADE_HISTORY T1 ")
		   .append("INNER JOIN USER_INFO T2 ON T2.ID=T1.payer ")
		   .append("INNER JOIN USER_INFO T3 ON T3.ID=T1.payee ")
		   .append("WHERE 1=1 ");
		
		if(!Utils.isEmpty(th.getPayerName())){
			sql.append("AND T2.nickname LIKE ? ");
			paramInfo.setTypeAndData(Types.VARCHAR, "%" + th.getPayerName() + "%");
		}
		
		if(!Utils.isEmpty(th.getPayeeName())){
			sql.append("AND T3.nickname LIKE ? ");
			paramInfo.setTypeAndData(Types.VARCHAR, "%" + th.getPayeeName() + "%");
		}
		
		return getCount(sql.toString(), paramInfo);
	}
}
