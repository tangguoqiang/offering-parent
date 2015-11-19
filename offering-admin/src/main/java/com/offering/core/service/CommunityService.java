package com.offering.core.service;

import java.util.List;

import com.offering.bean.community.CommunityTopic;
import com.offering.bean.community.CommunityTopicComment;
import com.offering.bean.community.CommunityTopicPraise;

/**
 * 社区service
 * @author surfacepro3
 *
 */
public interface CommunityService {

	/**
	 * 获取最新话题列表
	 * @param type
	 * @param time
	 * @return
	 */
	List<CommunityTopic> listTopics_new(String userId,String type,String time);
	
	/**
	 * 获取热门话题列表
	 * @param type
	 * @param praiseNum
	 * @return
	 */
	List<CommunityTopic> listTopics_hot(String userId,String type,String praiseNum,String time);
	
	/**
	 * 发布话题
	 * @param topic
	 * @param images
	 */
	CommunityTopic publishTopic(CommunityTopic topic,String images);
	
	/**
	 * 话题点赞
	 * @param praise
	 * @return
	 */
	long praise(CommunityTopicPraise praise,String topic_createrId);
	
	/**
	 * 获取未读消息列表
	 * @param userId
	 * @return
	 */
	List<CommunityTopicComment> listComments_unread(String userId);
	
	/**
	 * 删除未读消息
	 * @param id
	 * @param type
	 */
	void delComment_unread(String id,String type);
	
	/**
	 * 清空未读消息
	 * @param userId
	 */
	void clearComments_unread(String userId);
	
	/**
	 * 根据话题id获取话题信息
	 * @param id
	 * @return
	 */
	CommunityTopic getTopicInfoById(String userId,String id);
	
	/**
	 * 根据话题id加载评论列表
	 * @param topicId
	 * @return
	 */
	List<CommunityTopicComment> listComments(String topicId,String time);
	
	/**
	 * 发表评论
	 * @param comment
	 * @param topic_createrId
	 */
	CommunityTopicComment addComment(CommunityTopicComment comment,String topic_createrId);
}
