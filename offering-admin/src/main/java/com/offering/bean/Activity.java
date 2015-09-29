package com.offering.bean;

/**
 * 活动
 * @author surfacepro3
 *
 */
public class Activity {

	private String id;
	private String title;
	private String startTime;
	private String endTime;
	private String type;
	private String status;
	private String url;
	private String summary;
	private String share_activity_image;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}	
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getShare_activity_image() {
		return share_activity_image;
	}
	public void setShare_activity_image(String share_activity_image) {
		this.share_activity_image = share_activity_image;
	}
}
