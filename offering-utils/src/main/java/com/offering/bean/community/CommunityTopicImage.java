package com.offering.bean.community;

import com.offering.annotation.Column;

/**
 * 话题附件表
 * @author surfacepro3
 *
 */
public class CommunityTopicImage {

	@Column
	private String id;
	@Column
	private String topicId;
	@Column
	private String url;
	
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
