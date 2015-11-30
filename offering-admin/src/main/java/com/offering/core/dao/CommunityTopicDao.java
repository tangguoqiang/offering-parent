package com.offering.core.dao;

import java.util.List;

import com.offering.bean.community.CommunityTopic;
import com.offering.bean.sys.PageInfo;

/**
 * 社区话题dao
 * @author surfacepro3
 *
 */
public interface CommunityTopicDao extends BaseDao<CommunityTopic>{

	/**
	 * 获取话题列表
	 * @param topic
	 * @param page
	 * @return
	 */
	List<CommunityTopic> listTopics(CommunityTopic topic,PageInfo page);
	long getTopicCount(CommunityTopic topic);
	
	/**
	 * 根据话题id获取话题信息
	 * @param id
	 * @return
	 */
	CommunityTopic getTopicInfo(String id);
	
	/**
	 * 根据id删除话题
	 * @param id
	 */
	void delRecById(String id);
	
	/**
	 * 话题不置顶
	 * @param id
	 * @return
	 */
	void notTop();
	
	/**
	 * 话题置顶
	 * @param id
	 * @return
	 */
	void top(String id);
}
