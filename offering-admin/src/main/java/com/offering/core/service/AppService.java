package com.offering.core.service;

import java.util.List;

import com.offering.bean.CommunityTopic;
import com.offering.bean.CommunityTopicComment;
import com.offering.bean.CommunityTopicPraise;
import com.offering.bean.Greater;
import com.offering.bean.PageInfo;

public interface AppService {

	/**
	 * 获取大拿列表
	 * @param page
	 * @return
	 */
	List<Greater> listGreaters(PageInfo page,int v);
	
	/**
	 * 根据大拿id获取大拿信息
	 * @param id
	 * @param v
	 * @return
	 */
	Greater getGreaterInfoById(String id,int v);
	
	/**
	 * 创建私聊
	 * @param sender
	 * @param receiver
	 * @param type
	 * @param topicId
	 */
	void createPrivateChart(String sender,String receiver,String type,String topicId);
	
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
	CommunityTopic getTopicInfoById(String id);
	
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
	void addComment(CommunityTopicComment comment,String topic_createrId);
}
