package com.offering.bean.user;

import java.util.List;

import com.offering.annotation.Column;
import com.offering.bean.activity.Activity;
import com.offering.utils.Utils;

public class Greater {
	@Column
	private String id;
	@Column
	private String company;
	@Column
	private String post;
	@Column
	private String tags;
	@Column
	private String backgroud_url;
	@Column
	private String isshow;
	@Column
	private String orderNo;
	@Column
	private String workYears;
	@Column
	private String introduce;
	@Column
	private String online_startTime;
	@Column
	private String online_endTime;
	
	/**
	 * 基本信息
	 */
	private String nickname;
	private String phone;
	private String url;
	private String industry;
	private String industryName;
	private String schoolId;
	private String schoolName;
	
	@Deprecated
	private String experience;
	@Deprecated
	private String specialty;
	@Deprecated
	private String job;
	@Deprecated
	private String answerTimes;
	
	private String onlineTime;
	private List<Topic> topics;
	private List<Activity> activities;
	/**
	 * 参与人数最多的话题
	 */
	private Topic topic;
	
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
		if(tags != null){
			String[] arr = tags.split("[,，]");
			String tmp = "";
			for(String s : arr){
				if(!Utils.isEmpty(s))
					tmp = tmp + s + ",";
			}
			if(tmp.endsWith(","))
				tmp = tmp.substring(0,tmp.length() - 1);
			tags = tmp;
		}
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
	public String getIndustryName() {
		return industryName;
	}
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
}
