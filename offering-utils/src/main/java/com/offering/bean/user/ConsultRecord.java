package com.offering.bean.user;

import com.offering.annotation.Column;

/**
 * 咨询记录
 * @author surfacepro3
 *
 */
public class ConsultRecord {

	@Column
	private String id;
	@Column
	private String greaterId;
	@Column
	private String topicId;
	@Column
	private String description;
	@Column
	private String createTime;
	@Column
	private String creater;
	@Column
	private String status;
	@Column
	private String chatId;
	
	private String createrName;
	private String createrUrl;
	private String greaterName;
	private String greaterUrl;
	private String title;
	
	public String getCreaterName() {
		return createrName;
	}
	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}
	public String getCreaterUrl() {
		return createrUrl;
	}
	public void setCreaterUrl(String createrUrl) {
		this.createrUrl = createrUrl;
	}
	public String getGreaterName() {
		return greaterName;
	}
	public void setGreaterName(String greaterName) {
		this.greaterName = greaterName;
	}
	public String getGreaterUrl() {
		return greaterUrl;
	}
	public void setGreaterUrl(String greaterUrl) {
		this.greaterUrl = greaterUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getChatId() {
		return chatId;
	}
	public void setChatId(String chatId) {
		this.chatId = chatId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGreaterId() {
		return greaterId;
	}
	public void setGreaterId(String greaterId) {
		this.greaterId = greaterId;
	}
	public String getTopicId() {
		return topicId;
	}
	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
