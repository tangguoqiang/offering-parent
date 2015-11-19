package com.offering.core.dao.impl;

import java.sql.Types;

import org.springframework.stereotype.Repository;

import com.offering.bean.sys.ParamInfo;
import com.offering.bean.trade.TradeRecord;
import com.offering.constant.GloabConstant;
import com.offering.core.dao.TradeRecordDao;

/**
 * 交易记录dao实现
 * @author surfacepro3
 *
 */
@Repository
public class TradeRecordDaoImpl extends BaseDaoImpl<TradeRecord> 
	implements TradeRecordDao{

	/**
	 * 根据订单号获取订单记录
	 * @param tradeNo
	 * @return
	 */
	public TradeRecord getRecByTradeNo(String tradeNo){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT T1.tradeNo,T1.payer,T1.payee,T1.amount,T1.channel,T1.type, ")
		   .append("T1.tradeTime,T2.nickName payerName,T3.nickName payeeName ")
		   .append("FROM TRADE_RECORD T1 ")
		   .append("INNER JOIN USER_INFO T2 ON T2.ID=T1.payer ")
		   .append("INNER JOIN USER_INFO T3 ON T3.ID=T1.payee ")
		   .append("WHERE T1.tradeNo=? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.VARCHAR, tradeNo);
		return getRecord(sql.toString(), paramInfo, TradeRecord.class);
		
	}
	
	/**
	 * 更新交易记录状态
	 * @param tradeNo
	 */
	public void updateStatus(String tradeNo){
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE TRADE_RECORD SET status=? WHERE tradeNo=? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.CHAR, GloabConstant.STATUS_EFFECT);
		paramInfo.setTypeAndData(Types.VARCHAR, tradeNo);
		updateRecord(sql.toString(), paramInfo);
	}
}
