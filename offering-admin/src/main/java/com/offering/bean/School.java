package com.offering.bean;

import java.io.Serializable;

public class School implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6767828762920792901L;
	private String id;
	private String name;
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
