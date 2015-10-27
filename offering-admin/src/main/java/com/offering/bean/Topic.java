package com.offering.bean;

/**
 * 话题
 * @author surfacepro3
 *
 */
public class Topic {

	private String id;
	private String greaterId;
	private String title;
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
