package com.offering.bean;

/**
 * 话题成员
 * @author surfacepro3
 *
 */
public class TopicMember {

	private String id;
	private String topicId;
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
