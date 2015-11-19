package com.offering.bean.trade;

import com.offering.annotation.Column;

/**
 * 交易记录表
 * @author surfacepro3
 *
 */
public class TradeHistory {

	@Column
	private String tradeNo;
	@Column
	private String type;
	@Column
	private String tradeTime;
	@Column
	private String payer;
	@Column
	private String payee;
	@Column
	private String tradeInfo;
	
	public String getPayer() {
		return payer;
	}
	public void setPayer(String payer) {
		this.payer = payer;
	}
	public String getPayee() {
		return payee;
	}
	public void setPayee(String payee) {
		this.payee = payee;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
	public String getTradeInfo() {
		return tradeInfo;
	}
	public void setTradeInfo(String tradeInfo) {
		this.tradeInfo = tradeInfo;
	}
}
