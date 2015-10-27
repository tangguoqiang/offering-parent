package com.offering.bean;

import java.util.List;

public class Greater {

	private String id;
	private String nickname;
	private String phone;
	private String company;
	private String post;
	private String tags;
	private String experience;
	private String specialty;
	private String job;
	private String url;
	private String answerTimes;
	private String backgroud_url;
	private String isshow;
	private String orderNo;
	
	private String school;
	private String industry;
	private String workYears;
	private String introduce;
	private String online_startTime;
	private String online_endTime;
	private String onlineTime;
	private List<Topic> topics;
	private List<Activity> activities;
	
	/**
	 * 参与人数最多的话题
	 */
	private Topic topic;
	
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getWorkYears() {
		return workYears;
	}
	public void setWorkYears(String workYears) {
		this.workYears = workYears;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getOnline_startTime() {
		return online_startTime;
	}
	public void setOnline_startTime(String online_startTime) {
		this.online_startTime = online_startTime;
	}
	public String getOnline_endTime() {
		return online_endTime;
	}
	public void setOnline_endTime(String online_endTime) {
		this.online_endTime = online_endTime;
	}
	public String getOnlineTime() {
		onlineTime = online_startTime + "-" + online_endTime;
		return onlineTime;
	}
	public void setOnlineTime(String onlineTime) {
		this.onlineTime = onlineTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
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
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAnswerTimes() {
		return answerTimes;
	}
	public void setAnswerTimes(String answerTimes) {
		this.answerTimes = answerTimes;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getBackgroud_url() {
		return backgroud_url;
	}
	public void setBackgroud_url(String backgroud_url) {
		this.backgroud_url = backgroud_url;
	}
	public String getIsshow() {
		return isshow;
	}
	public void setIsshow(String isshow) {
		this.isshow = isshow;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public List<Topic> getTopics() {
		return topics;
	}
	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}
	public Topic getTopic() {
		return topic;
	}
	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	public List<Activity> getActivities() {
		return activities;
	}
	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}
}
