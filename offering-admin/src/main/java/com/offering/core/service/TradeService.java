package com.offering.core.service;

import org.json.JSONObject;

import com.offering.bean.trade.TradeRecord;
import com.pingplusplus.model.Charge;

/**
 * 交易service
 * @author surfacepro3
 *
 */
public interface TradeService {

	/**
	 * 支付
	 * @param trade
	 * @return
	 */
	Charge pay(TradeRecord trade);
	
	/**
	 * 交易成功处理
	 * @param jsonObj
	 */
	void trade_success(JSONObject jsonObj);
}
