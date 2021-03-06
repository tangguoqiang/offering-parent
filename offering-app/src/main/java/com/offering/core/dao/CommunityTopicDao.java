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
	 * 获取最新话题列表
	 * @param type
	 * @param time
	 * @return
	 */
	List<CommunityTopic> listTopics_new(String userId,String type,String time);
	
	/**
	 * 获取热门话题列表
	 * @param userId
	 * @param type
	 * @param praiseNum
	 * @param time
	 * @return
	 */
	List<CommunityTopic> listTopics_hot(String userId,String type,String praiseNum,String time);
	
	/**
	 * 获取置顶帖
	 * @return
	 */
	CommunityTopic getTopTopic(String userId);
	
	/**
	 * 根据id获取话题信息
	 * @param id
	 * @return
	 */
	CommunityTopic getTopicInfoById(String userId,String id);
	
	/**
	 * 我的发布
	 * @param userId
	 * @param type
	 * @param pageInfo
	 * @return
	 */
	List<CommunityTopic> communityTopicHistory(String userId,String type,PageInfo pageInfo);
	
	/**
	 * 根据id删除话题
	 * @param id
	 */
	void delRecById(String id);
}
