package com.offering.bean.trade;

import com.offering.annotation.Column;

/**
 * 用户账户表
 * @author surfacepro3
 *
 */
public class Account {

	@Column
	private String id;
	@Column
	private String userId;
	@Column
	private String code;
	@Column
	private String status;
	@Column
	private String balance;
	@Column
	private String createTime;
	@Column
	private String creater;
	@Column
	private String opTime;
	@Column
	private String opUser;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getOpTime() {
		return opTime;
	}
	public void setOpTime(String opTime) {
		this.opTime = opTime;
	}
	public String getOpUser() {
		return opUser;
	}
	public void setOpUser(String opUser) {
		this.opUser = opUser;
	}
}
