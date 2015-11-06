package com.offering.core.dao;

import java.util.List;

import com.offering.bean.CommunityTopic;

/**
 * 社区话题dao
 * @author surfacepro3
 *
 */
public interface CommunityTopicDao extends BaseDao<CommunityTopic>{

	/**
	 * 获取最新话题列表
	 * @param type
	 * @param time
	 * @return
	 */
	List<CommunityTopic> listTopics_new(String type,String time);
	
	/**
	 * 获取热门话题列表
	 * @param type
	 * @param praiseNum
	 * @return
	 */
	List<CommunityTopic> listTopics_hot(String type,String praiseNum);
	
	/**
	 * 获取置顶帖
	 * @return
	 */
	CommunityTopic getTopTopic();
	
	/**
	 * 根据id获取话题信息
	 * @param id
	 * @return
	 */
	CommunityTopic getTopicInfoById(String id);
}
