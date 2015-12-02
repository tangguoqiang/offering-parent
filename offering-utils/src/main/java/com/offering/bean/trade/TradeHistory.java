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
	private String amount;
	
	private String payerName;
	private String payeeName;
	private String payerUrl;
	private String payeeUrl;
	private String channel;
	
	public String getPayerName() {
		return payerName;
	}
	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}
	public String getPayeeName() {
		return payeeName;
	}
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}
	public String getPayerUrl() {
		return payerUrl;
	}
	public void setPayerUrl(String payerUrl) {
		this.payerUrl = payerUrl;
	}
	public String getPayeeUrl() {
		return payeeUrl;
	}
	public void setPayeeUrl(String payeeUrl) {
		this.payeeUrl = payeeUrl;
	}
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
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
}
