package com.offering.core.dao;

import java.util.List;

import com.offering.bean.user.Topic;

/**
 * 话题dao
 * @author surfacepro3
 *
 */
public interface TopicDao extends BaseDao<Topic>{

	/**
	 * 根据大拿id获取话题数据
	 * @param greaterIds
	 * @return
	 */
	List<Topic> listTopicsByGreaterId(String greaterId);
	
	/**
	 * 根据话题id获取话题信息
	 * @param id
	 * @return
	 */
	Topic getTopicInfoById(String id);
	
	/**
	 * 根据大拿id删除话题数据
	 * @param greaterId
	 */
	void delRecsByGreaterId(String greaterId);
	
	/**
	 * 更新话题
	 * @param topic
	 */
	void updateTopic(Topic topic);
	
}
