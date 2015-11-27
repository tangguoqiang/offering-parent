package com.offering.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.offering.bean.community.CommunityTopic;
import com.offering.bean.community.CommunityTopicComment;
import com.offering.bean.community.CommunityTopicImage;
import com.offering.bean.community.CommunityTopicPraise;
import com.offering.bean.sys.PageInfo;
import com.offering.constant.GloabConstant;
import com.offering.core.dao.CommunityTopicCommentDao;
import com.offering.core.dao.CommunityTopicDao;
import com.offering.core.dao.CommunityTopicImageDao;
import com.offering.core.dao.CommunityTopicPraiseDao;
import com.offering.core.service.CommunityService;
import com.offering.utils.JpushUtils;
import com.offering.utils.JpushUtils.JpushType;
import com.offering.utils.QiniuUtils;
import com.offering.utils.Utils;

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
	 * 获取最新话题列表
	 * @param type
	 * @param time
	 * @return
	 */
	public List<CommunityTopic> listTopics_new(String userId,String type,String time){
		List<CommunityTopic> l =  communityTopicDao.listTopics_new(userId,type, time);
		if(Utils.isEmpty(type) || GloabConstant.OP_DOWN.equals(type)){
			CommunityTopic topTopic = communityTopicDao.getTopTopic(userId);
			if(topTopic != null && l.size() >= 2){
				topTopic.setPost("这里是置3的帖子!");
				l.add(2, topTopic);
			}
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
					tmpList.add(QiniuUtils.getFullUrl(image.getUrl(), QiniuUtils.STYLE_ZOOM));
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
	public List<CommunityTopic> listTopics_hot(String userId,String type,
			String praiseNum,String time){
		List<CommunityTopic> l =  communityTopicDao.listTopics_hot(userId,type, praiseNum,time);
		if(Utils.isEmpty(type) || GloabConstant.OP_DOWN.equals(type)){
			CommunityTopic topTopic = communityTopicDao.getTopTopic(userId);
			if(topTopic != null && l.size() >= 2){
				topTopic.setPost("这里是置3的帖子!");
				l.add(2, topTopic);
			}
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
					tmpList.add(QiniuUtils.getFullUrl(image.getUrl(), QiniuUtils.STYLE_ZOOM));
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
		
		//发送通知
		Map<String, String> extras = new HashMap<String, String>();
		extras.put("type", GloabConstant.NOTIFY_TYPE_1);
		JpushUtils.sendMessage("有新话题", null, extras,JpushType.MESSAGE);
		
		CommunityTopic returnTopic = communityTopicDao.getTopicInfoById(topic.getCreaterId(),id + "");
		List<String> idList = new ArrayList<String>();
		idList.add(id + "");
		List<CommunityTopicImage> imageList = communityTopicImageDao.listImagesByTopicId(idList);
		if(imageList != null && imageList.size() > 0){
			List<String> tmpList = new ArrayList<String>();
			for(CommunityTopicImage image : imageList){
				tmpList.add(QiniuUtils.getFullUrl(image.getUrl(), QiniuUtils.STYLE_ZOOM));
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
			
			if(!Utils.isEmpty(topic_createrId) && 
					(!topic_createrId.equals(praise.getCreaterId()))){
				Map<String, String> extras = new HashMap<String, String>();
				extras.put("topicId", praise.getTopicId());
				extras.put("type", GloabConstant.NOTIFY_TYPE_2);
				JpushUtils.sendMessage(GloabConstant.NOTIFY_TEXT_PRAISE, 
						new String[]{topic_createrId},extras,JpushType.NOTIFY);
			}
		}else{
			communityTopicPraiseDao.delPraise(praise.getCreaterId(),praise.getTopicId());
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
					tmpList.add(QiniuUtils.getFullUrl(image.getUrl(), QiniuUtils.STYLE_ZOOM));
					imageMap.put(topicId, tmpList);
				}
			}
			
			for(CommunityTopicComment comment : l){
				comment.setImages(imageMap.get(comment.getTopicId()));
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
	public CommunityTopic getTopicInfoById(String userId,String id){
		CommunityTopic topic = communityTopicDao.getTopicInfoById(userId,id);
		if(topic == null)
			return null;
		List<String> idList = new ArrayList<String>();
		idList.add(topic.getId());
		List<CommunityTopicImage> imageList = communityTopicImageDao.listImagesByTopicId(idList);
		if(imageList != null && imageList.size() > 0){
			List<String> tmpList = new ArrayList<String>();
			for(CommunityTopicImage image : imageList){
				tmpList.add(QiniuUtils.getFullUrl(image.getUrl(), QiniuUtils.STYLE_ZOOM));
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
	public CommunityTopicComment addComment(CommunityTopicComment comment,String topic_createrId){
		long id = communityTopicCommentDao.insertRecord(comment, "COMMUNITY_TOPIC_COMMENT");
		if(!Utils.isEmpty(topic_createrId) && 
				(!topic_createrId.equals(comment.getCreaterId()))){
			Map<String, String> extras = new HashMap<String, String>();
			extras.put("topicId", comment.getTopicId());
			extras.put("type", GloabConstant.NOTIFY_TYPE_2);
			JpushUtils.sendMessage(GloabConstant.NOTIFY_TEXT_COMMENT, 
					new String[]{topic_createrId},extras,JpushType.NOTIFY);
		}
		return communityTopicCommentDao.getCommentById(id + "");
	}
	
	/**
	 * 我的发布
	 * @param userId
	 * @param type
	 * @param pageInfo
	 * @return
	 */
	public List<CommunityTopic> communityTopicHistory(String userId,String type,PageInfo pageInfo){
		List<CommunityTopic> l =  communityTopicDao.communityTopicHistory(userId,type, pageInfo);
		
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
					tmpList.add(QiniuUtils.getFullUrl(image.getUrl(), QiniuUtils.STYLE_ZOOM));
					imageMap.put(topicId, tmpList);
				}
			}
			
			for(CommunityTopic topic : l)
				topic.setImages(imageMap.get(topic.getId()));
		}
		return l;
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
}
