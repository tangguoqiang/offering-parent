package com.offering.bean.sys;

import java.io.Serializable;

import com.offering.annotation.Column;

public class School implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6767828762920792901L;
	
	@Column
	private String id;
	@Column
	private String name;
	@Column
	private String province;
//	private String city;
//	private String orderNum;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
}
