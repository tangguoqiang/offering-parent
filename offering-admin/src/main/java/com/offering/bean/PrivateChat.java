package com.offering.bean;

/**
 * 私聊信息
 * @author surfacepro3
 *
 */
public class PrivateChat {

	private String id;
	private String sender;
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
