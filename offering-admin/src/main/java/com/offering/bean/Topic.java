package com.offering.bean;

import com.offering.annotation.Column;

/**
 * 话题
 * @author surfacepro3
 *
 */
public class Topic {

	@Column
	private String id;
	@Column
	private String greaterId;
	@Column
	private String title;
	@Column
	private String content;
	private String askNums;
	
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAskNums() {
		return askNums;
	}
	public void setAskNums(String askNums) {
		this.askNums = askNums;
	}
	
}
