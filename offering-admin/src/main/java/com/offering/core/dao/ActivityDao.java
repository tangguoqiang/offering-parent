package com.offering.core.dao;

import java.util.List;

import com.offering.bean.activity.Activity;

/**
 * 活动dao
 * @author surfacepro3
 *
 */
public interface ActivityDao extends BaseDao<Activity>{

	/**
	 * 根据大拿id获取活动
	 * @param greaterId
	 * @return
	 */
	List<Activity> listActivitysByGreaterId(String greaterId);
	
	/**
	 * 根据用户id查询参加过的活动
	 * @param userId
	 * @param type
	 * @return
	 */
	List<Activity> activityHistory(String userId,String type);
}
