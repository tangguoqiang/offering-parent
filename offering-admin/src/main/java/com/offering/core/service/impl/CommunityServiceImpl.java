package com.offering.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.offering.bean.community.CommunityTopic;
import com.offering.bean.community.CommunityTopicComment;
import com.offering.bean.sys.PageInfo;
import com.offering.constant.DBConstant;
import com.offering.core.dao.CommunityTopicCommentDao;
import com.offering.core.dao.CommunityTopicDao;
import com.offering.core.dao.CommunityTopicImageDao;
import com.offering.core.dao.CommunityTopicPraiseDao;
import com.offering.core.service.CommunityService;

/**
 * 社区service实现
 * @author surfacepro3
 *
 */
@Service
public class CommunityServiceImpl implements CommunityService{
	
	@Autowired
	private CommunityTopicDao communityTopicDao;
	
	@Autowired
	private CommunityTopicImageDao communityTopicImageDao;
	
	@Autowired
	private CommunityTopicPraiseDao communityTopicPraiseDao;
	
	@Autowired
	private CommunityTopicCommentDao communityTopicCommentDao;

	/**
	 * 获取话题列表
	 * @param userId
	 * @param type
	 * @param time
	 * @return
	 */
	public List<CommunityTopic> listTopics(CommunityTopic topic,PageInfo page){
		List<CommunityTopic> l =  communityTopicDao.listTopics(topic,page);
		return l;
	}
	
	public long getTopicCount(CommunityTopic topic){
		return communityTopicDao.getTopicCount(topic);
	}
	
	/**
	 * 根据话题id获取话题信息
	 * @param id
	 * @return
	 */
	public CommunityTopic getTopicInfo(String id){
		return communityTopicDao.getTopicInfo(id);
	}
	
	/**
	 * 删除社区话题
	 * @param topicId
	 */
	@Transactional
	public void deleteCommunityTopic(String topicId){
		communityTopicCommentDao.delRecByTopicId(topicId);
		communityTopicPraiseDao.delRecByTopicId(topicId);
		communityTopicImageDao.delRecByTopicId(topicId);
		communityTopicDao.delRecById(topicId);
	}
	
	/**
	 * 话题置顶
	 * @param id
	 * @return
	 */
	@Transactional
	public void top(String id){
		communityTopicDao.notTop();
		communityTopicDao.top(id);
	}
	
	/**
	 * 获取评论列表
	 * @param topicId
	 * @param page
	 * @return
	 */
	public List<CommunityTopicComment> listComments(String topicId,PageInfo page){
		return communityTopicCommentDao.listComments(topicId,page);
	}
	
	public long getCommentsCount(String topicId){
		return communityTopicCommentDao.getCommentsCount(topicId);
	}
	
	/**
	 * 删除评论
	 * @param topicId
	 */
	public void delComment(String id){
		communityTopicCommentDao.delRecordById(id, DBConstant.COMMUNITY_TOPIC_COMMENT);
	}
}
