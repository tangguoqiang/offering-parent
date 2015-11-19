package com.offering.bean.activity;

import com.offering.annotation.Column;

/**
 * 活动演讲人
 * @author surfacepro3
 *
 */
public class Speaker {

	@Column
	private String id;
	@Column
	private String speakerId;
	@Column
	private String activityId;
	@Column
	private String remark;
	
	private String name;
	private String url;
	private String company;
	private String post;
	private String tags;
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
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
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSpeakerId() {
		return speakerId;
	}
	public void setSpeakerId(String speakerId) {
		this.speakerId = speakerId;
	}
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
}
