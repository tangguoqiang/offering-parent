package com.offering.bean.community;

import com.offering.annotation.Column;

/**
 * 话题点赞
 * @author surfacepro3
 *
 */
public class CommunityTopicPraise {

	@Column
	private String id;
	@Column
	private String topicId;
	@Column
	private String createrId;
	@Column
	private String createTime;
	@Column
	private String isRead;
	
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
	public String getCreaterId() {
		return createrId;
	}
	public void setCreaterId(String createrId) {
		this.createrId = createrId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getIsRead() {
		return isRead;
	}
	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
}
