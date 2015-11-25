package com.offering.core.service;

import java.util.List;
import java.util.Map;

import com.offering.bean.activity.Activity;
import com.offering.bean.activity.Speaker;
import com.offering.bean.chart.ChartGroup;
import com.offering.bean.chart.Member;
import com.offering.bean.chart.Message;
import com.offering.bean.sys.PageInfo;

public interface ActivityService {

	/**
	 * 获取活动列表(web端)
	 * @param act
	 * @param page
	 * @return
	 */
	List<Activity> listActivities(Activity act,PageInfo page);
	
	/**
	 * 获取活动列表(app端)
	 * @param startTime
	 * @param endTime
	 * @param limit
	 * @return
	 */
	List<Activity> listActivities_app(String startTime,String endTime,int limit);
	
	/**
	 * 获取活动数量
	 * @param act
	 * @return
	 */
	long getActivityCount(Activity act);
	
	/**
	 * 根据ID获取活动信息
	 * @param id
	 * @return
	 */
	Activity getActivityById(String id);
	
	/**
	 * 根据活动ID获取主讲人列表
	 * @param activityId
	 * @return
	 */
	List<Speaker> listSpeakers(String activityId);
	
	/**
	 * 根据活动ID获取主讲人列表(批量)
	 * @param activityId
	 * @return
	 */
	Map<String, Speaker> listSpeakers(List<String> ids);
	
	/**
	 * 根据群聊id获取群聊信息
	 * @param groupId
	 * @return
	 */
	ChartGroup getGroupById(String groupId);
	
	/**
	 * 新增群组成员
	 * @param userId
	 * @param groupId
	 */
	void addGroupMember(String userId,String groupId);
	
	/**
	 * 删除群组成员
	 * @param userId
	 * @param groupId
	 */
	void delGroupMember(String userId,String groupId);
	
	/**
	 * 根据用户id查询消息列表
	 * @param userId
	 * @return
	 */
	List<Message> listMessages(String userId);
	
	/**
	 * 根据群组id获取群成员
	 * @param groupId
	 * @return
	 */
	List<Member> listMembers(String groupId);
	
	/**
	 * 根据群组id获取群成员
	 * @param groupId
	 * @return
	 */
	Map<String, List<Member>> listMembers(List<String> ids);
	
	/**
	 * 创建私聊
	 * @param sender
	 * @param receiver
	 */
	void createPrivateChart(String sender,String receiver);
	
	/**
	 * 删除私聊
	 * @param sender
	 * @param receiver
	 */
	void delPrivateChart(String sender,String receiver);
	
	/**
	 * 判断用户是否已经加入活动，已加入返回true
	 * @param activityId
	 * @param userId
	 * @return
	 */
	boolean isJoin(String activityId,String userId);
	
	/**
	 * 新增活动
	 * @param act
	 */
	void insertActivity(Activity act,ChartGroup group);
	
	/**
	 * 更新活动
	 * @param act
	 */
	void updateActivity(Activity act,ChartGroup group);
	
	/**
	 * 删除活动
	 * @param id
	 */
	void delActivity(String id);
	
	/**
	 * 活动发布
	 * @param id
	 */
	String publishActivity(String id);
	
	/**
	 * 更新活动状态
	 * @param id
	 * @param status
	 */
	void updateActivityStatus(String id,String status);
	
	/**
	 * 新增主讲人
	 * @param speaker
	 */
	void insertSpeaker(Speaker speaker);
	
	/**
	 * 更新主讲人
	 * @param speaker
	 */
	void updateSpeaker(Speaker speaker);
	
	/**
	 * 根据id获取主讲人信息
	 * @param id
	 * @return
	 */
	Speaker getSpeakerInfo(String id);
	
	/**
	 * 删除主讲人
	 * @param id
	 */
	void delSpeaker(String id);
	
	/**
	 * 上传图片
	 * @param id
	 * @param url
	 */
	void uploadActivityImage(String id,String url,String uploadType);
	
	/**
	 * 根据用户ID获取其参加活动的次数
	 * @param userId
	 * @return
	 */
	long getJoinActivityNum(String userId);
	
	/**
	 * 根据用户ID获取其咨询大拿的次数
	 * @param userId
	 * @return
	 */
	long getAskGreaterNum(String userId);
	
	/**
	 * 根据用户id查询参加过的活动
	 * @param userId
	 * @param type
	 * @return
	 */
	List<Activity> activityHistory(String userId,String type,PageInfo pageInfo);
}
