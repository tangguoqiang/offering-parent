package com.offering.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.offering.bean.Greater;
import com.offering.bean.PageInfo;
import com.offering.bean.PrivateChat;
import com.offering.bean.Topic;
import com.offering.bean.TopicMember;
import com.offering.constant.GloabConstant;
import com.offering.core.dao.ActivityDao;
import com.offering.core.dao.GreaterDao;
import com.offering.core.dao.PrivateChatDao;
import com.offering.core.dao.TopicDao;
import com.offering.core.dao.TopicMemberDao;
import com.offering.core.service.AppService;
import com.offering.redis.RedisOp;

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
}
