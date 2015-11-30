package com.offering.core.service;

import java.util.List;

import com.offering.bean.community.CommunityTopic;
import com.offering.bean.community.CommunityTopicComment;
import com.offering.bean.sys.PageInfo;

/**
 * 社区service
 * @author surfacepro3
 *
 */
public interface CommunityService {

	/**
	 * 获取话题列表
	 * @param type
	 * @param time
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
	 * 删除社区话题
	 * @param topicId
	 */
	void deleteCommunityTopic(String topicId);
	
	/**
	 * 话题置顶
	 * @param id
	 * @return
	 */
	void top(String id);
	
	/**
	 * 获取评论列表
	 * @param topicId
	 * @param page
	 * @return
	 */
	List<CommunityTopicComment> listComments(String topicId,PageInfo page);
	long getCommentsCount(String topicId);
	
	/**
	 * 删除评论
	 * @param topicId
	 */
	void delComment(String id);
}
