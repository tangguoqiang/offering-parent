package com.offering.bean.user;

import com.offering.annotation.Column;

/**
 * 大拿话题成员
 * @author surfacepro3
 *
 */
public class TopicMember {
	@Column
	private String id;
	@Column
	private String topicId;
	@Column
	private String memberId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTopicId() {
		return topicId;
	}
	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
}
