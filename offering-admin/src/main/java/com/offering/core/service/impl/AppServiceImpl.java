package com.offering.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.offering.bean.CommunityTopic;
import com.offering.bean.CommunityTopicComment;
import com.offering.bean.CommunityTopicImage;
import com.offering.bean.CommunityTopicPraise;
import com.offering.bean.Greater;
import com.offering.bean.PageInfo;
import com.offering.bean.PrivateChat;
import com.offering.bean.Topic;
import com.offering.bean.TopicMember;
import com.offering.constant.GloabConstant;
import com.offering.core.dao.ActivityDao;
import com.offering.core.dao.CommunityTopicCommentDao;
import com.offering.core.dao.CommunityTopicDao;
import com.offering.core.dao.CommunityTopicImageDao;
import com.offering.core.dao.CommunityTopicPraiseDao;
import com.offering.core.dao.GreaterDao;
import com.offering.core.dao.PrivateChatDao;
import com.offering.core.dao.TopicDao;
import com.offering.core.dao.TopicMemberDao;
import com.offering.core.service.AppService;
import com.offering.redis.RedisOp;
import com.offering.utils.QiniuUtils;
import com.offering.utils.Utils;

@Service
public class AppServiceImpl implements AppService{

	@Autowired
	private GreaterDao greaterDao;
	
	@Autowired
	private ActivityDao activityDao;
	
	@Autowired
	private PrivateChatDao privateChatDao;
	
	@Autowired
	private TopicDao topicDao;
	
	@Autowired
	private TopicMemberDao topicMemberDao;
	
	@Autowired
	private CommunityTopicDao communityTopicDao;
	
	@Autowired
	private CommunityTopicImageDao communityTopicImageDao;
	
	@Autowired
	private CommunityTopicPraiseDao communityTopicPraiseDao;
	
	@Autowired
	private CommunityTopicCommentDao communityTopicCommentDao;
	
	@Autowired
	private RedisOp redisOp;
	
	/**
	 * 获取大拿列表
	 * @param page
	 * @return
	 */
	public List<Greater> listGreaters(PageInfo page,int v)
	{
		List<Greater> greaterList = greaterDao.listGreaters_app(page);
		if(greaterList != null && greaterList.size() > 0)
		{
			List<String> idList = new ArrayList<String>();
			for(Greater greater : greaterList)
				idList.add(greater.getId());
			
			List<Topic> topicList = topicDao.listTopicsByGreaterId(idList);
			Map<String, Topic> topicMap = new HashMap<String, Topic>();
			String id = null;
			if(topicList != null && topicList.size() > 0)
			{
				for(Topic topic : topicList)
				{
					id = topic.getGreaterId();
					if(topicMap.containsKey(id))
						continue;
					topicMap.put(id, topic);
				}
			}
			for(Greater greater : greaterList)
				greater.setTopic(topicMap.get(greater.getId()));
		}
		return greaterList;
	}
	
	/**
	 * 根据大拿id获取大拿信息
	 * @param id
	 * @param v
	 * @return
	 */
	public Greater getGreaterInfoById(String id,int v)
	{
		Greater greater = greaterDao.getGreaterInfoById_app(id);
		if(greater == null)
			return null;
		List<String> idList = new ArrayList<String>();
		idList.add(id);
		greater.setTopics(topicDao.listTopicsByGreaterId(idList));
		greater.setActivities(activityDao.listActivitysByGreaterId(id));
		return greater;
	}
	
	/**
	 * 创建私聊
	 * @param sender
	 * @param receiver
	 * @param type
	 * @param topicId
	 */
	@Transactional
	public void createPrivateChart(String sender,String receiver,String type,String topicId)
	{
		if(!privateChatDao.isExists(sender,receiver))
		{
			PrivateChat chat = new PrivateChat();
			chat.setSender(sender);
			chat.setReceiver(receiver);
			privateChatDao.insertRecord(chat, "RC_PRIVATE");
		}
		
		if(GloabConstant.PRIVATECHAT_1.equals(type))
		{
			if(!topicMemberDao.isExists(sender,topicId))
			{
				TopicMember member = new TopicMember();
				member.setTopicId(topicId);
				member.setMemberId(sender);
				topicMemberDao.insertRecord(member, "TOPIC_MEMBER");
			}
		}
	}
	
	/**
	 * 获取最新话题列表
	 * @param type
	 * @param time
	 * @return
	 */
	public List<CommunityTopic> listTopics_new(String type,String time){
		List<CommunityTopic> l =  communityTopicDao.listTopics_new(type, time);
		if(Utils.isEmpty(type) || GloabConstant.OP_DOWN.equals(type)){
			CommunityTopic topTopic = communityTopicDao.getTopTopic();
			if(topTopic != null)
				l.add(2, topTopic);
		}
		
		List<String> idList = new ArrayList<String>();
		if(l != null && l.size() > 0){
			for(CommunityTopic topic : l)
				idList.add(topic.getId());
			
			List<CommunityTopicImage> imageList = communityTopicImageDao.listImagesByTopicId(idList);
			Map<String, List<String>> imageMap = new HashMap<String, List<String>>();
			List<String> tmpList = null;
			String topicId = null;
			if(imageList != null && imageList.size() > 0){
				for(CommunityTopicImage image : imageList){
					topicId = image.getTopicId();
					if(imageMap.containsKey(topicId))
						tmpList = imageMap.get(topicId);
					else
						tmpList = new ArrayList<String>();
					tmpList.add(QiniuUtils.getBaseUrl() +  image.getUrl());
					imageMap.put(topicId, tmpList);
				}
			}
			
			for(CommunityTopic topic : l)
				topic.setImages(imageMap.get(topic.getId()));
		}
		return l;
	}
	
	/**
	 * 获取热门话题列表
	 * @param type
	 * @param praiseNum
	 * @return
	 */
	public List<CommunityTopic> listTopics_hot(String type,String praiseNum){
		List<CommunityTopic> l =  communityTopicDao.listTopics_hot(type, praiseNum);
		if(Utils.isEmpty(type) || GloabConstant.OP_DOWN.equals(type)){
			CommunityTopic topTopic = communityTopicDao.getTopTopic();
			if(topTopic != null)
				l.add(2, topTopic);
		}
		
		List<String> idList = new ArrayList<String>();
		if(l != null && l.size() > 0){
			for(CommunityTopic topic : l)
				idList.add(topic.getId());
			
			List<CommunityTopicImage> imageList = communityTopicImageDao.listImagesByTopicId(idList);
			Map<String, List<String>> imageMap = new HashMap<String, List<String>>();
			List<String> tmpList = null;
			String topicId = null;
			if(imageList != null && imageList.size() > 0){
				for(CommunityTopicImage image : imageList){
					topicId = image.getTopicId();
					if(imageMap.containsKey(topicId))
						tmpList = imageMap.get(topicId);
					else
						tmpList = new ArrayList<String>();
					tmpList.add(QiniuUtils.getBaseUrl() +  image.getUrl());
					imageMap.put(topicId, tmpList);
				}
			}
			
			for(CommunityTopic topic : l)
				topic.setImages(imageMap.get(topic.getId()));
		}
		return l;
	}
	
	/**
	 * 发布话题
	 * @param topic
	 * @param images
	 */
	@Transactional
	public CommunityTopic publishTopic(CommunityTopic topic,String images){
		//插入话题表
		long id = communityTopicDao.insertRecord(topic, "COMMUNITY_TOPIC");
		if(!Utils.isEmpty(images)){
			communityTopicImageDao.insertRecords(id + "", images.split("[,，]"));
		}
		CommunityTopic returnTopic = communityTopicDao.getTopicInfoById(id + "");
		List<String> idList = new ArrayList<String>();
		idList.add(id + "");
		List<CommunityTopicImage> imageList = communityTopicImageDao.listImagesByTopicId(idList);
		if(imageList != null && imageList.size() > 0){
			List<String> tmpList = new ArrayList<String>();
			for(CommunityTopicImage image : imageList){
				tmpList.add(QiniuUtils.getBaseUrl() +  image.getUrl());
			}
			returnTopic.setImages(tmpList);
		}
		return returnTopic;
	}
	
	/**
	 * 话题点赞
	 * @param praise
	 * @return
	 */
	@Transactional
	public long praise(CommunityTopicPraise praise,String topic_createrId){
		if(!communityTopicPraiseDao.isExistsPraise(praise.getCreaterId(),praise.getTopicId())){
			communityTopicPraiseDao.insertRecord(praise, "COMMUNITY_TOPIC_PRAISE");
			//TODO 需要推送通知给话题创建人
		}
		
		return communityTopicPraiseDao.getPraiseCount(praise.getTopicId());
	}
	
	/**
	 * 获取未读消息列表
	 * @param userId
	 * @return
	 */
	public List<CommunityTopicComment> listComments_unread(String userId){
		List<CommunityTopicComment> l = communityTopicCommentDao.listComments_unread(userId);
		if(l != null && l.size() > 0){
			List<String> idList = new ArrayList<String>();
			for(CommunityTopicComment comment : l){
				if(Utils.isEmpty(comment.getContent())){
					if(!l.contains(comment.getTopicId()))
						idList.add(comment.getTopicId());
				}
			}
				
			
			List<CommunityTopicImage> imageList = communityTopicImageDao.listImagesByTopicId(idList);
			Map<String, List<String>> imageMap = new HashMap<String, List<String>>();
			List<String> tmpList = null;
			String topicId = null;
			if(imageList != null && imageList.size() > 0){
				for(CommunityTopicImage image : imageList){
					topicId = image.getTopicId();
					if(imageMap.containsKey(topicId))
						tmpList = imageMap.get(topicId);
					else
						tmpList = new ArrayList<String>();
					tmpList.add(QiniuUtils.getBaseUrl() +  image.getUrl());
					imageMap.put(topicId, tmpList);
				}
			}
			
			for(CommunityTopicComment comment : l){
				comment.setImages(tmpList);
			}
		}
		return l;
	}
	
	/**
	 * 删除未读消息
	 * @param id
	 * @param type
	 */
	public void delComment_unread(String id,String type){
		if(GloabConstant.COMMENT_TYPE_1.equals(type)){
			//评论
			communityTopicCommentDao.updateToRead(id);
		}else if(GloabConstant.COMMENT_TYPE_2.equals(type)){
			//点赞
			communityTopicPraiseDao.updateToRead(id);
		}
	}
	
	/**
	 * 清空未读消息
	 * @param userId
	 */
	@Transactional
	public void clearComments_unread(String userId){
		List<CommunityTopicComment> l = communityTopicCommentDao.listComments_unread(userId);
		if(l != null && l.size() > 0){
			List<String> praiseIdList = new ArrayList<String>();
			List<String> commentIdList = new ArrayList<String>();
			for(CommunityTopicComment comment : l){
				if(GloabConstant.COMMENT_TYPE_1.equals(comment.getCommentType())){
					//评论
					commentIdList.add(comment.getId());
				}else if(GloabConstant.COMMENT_TYPE_2.equals(comment.getCommentType())){
					//点赞
					praiseIdList.add(comment.getId());
				}
			}
			
			//点赞
			communityTopicPraiseDao.updateToRead(praiseIdList);
			//评论
			communityTopicCommentDao.updateToRead(commentIdList);
		}
	}
	
	/**
	 * 根据话题id获取话题信息
	 * @param id
	 * @return
	 */
	public CommunityTopic getTopicInfoById(String id){
		CommunityTopic topic = communityTopicDao.getTopicInfoById(id);
		if(topic == null)
			return null;
		List<String> idList = new ArrayList<String>();
		idList.add(topic.getId());
		List<CommunityTopicImage> imageList = communityTopicImageDao.listImagesByTopicId(idList);
		if(imageList != null && imageList.size() > 0){
			List<String> tmpList = new ArrayList<String>();
			for(CommunityTopicImage image : imageList){
				tmpList.add(QiniuUtils.getBaseUrl() +  image.getUrl());
			}
			topic.setImages(tmpList);
		}
		return topic;
	}
	
	/**
	 * 根据话题id加载评论列表
	 * @param topicId
	 * @return
	 */
	public List<CommunityTopicComment> listComments(String topicId,String time){
		return communityTopicCommentDao.listComments(topicId,time);
	}
	
	/**
	 * 发表评论
	 * @param comment
	 * @param topic_createrId
	 */
	public void addComment(CommunityTopicComment comment,String topic_createrId){
		communityTopicCommentDao.insertRecord(comment, "COMMUNITY_TOPIC_COMMENT");
		//TODO 推送消息通知
	}
}