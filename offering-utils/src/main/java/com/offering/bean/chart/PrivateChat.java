package com.offering.bean.chart;

import com.offering.annotation.Column;

/**
 * 私聊信息
 * @author surfacepro3
 *
 */
public class PrivateChat {

	@Column
	private String id;
	@Column
	private String sender;
	@Column
	private String receiver;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
}
