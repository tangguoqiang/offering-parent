package com.offering.bean;

import java.io.Serializable;

/**
 * 验证码
 * @author surfacepro3
 *
 */
public class IDCode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1974359061394235910L;
	private String code;
	private String createTime;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
