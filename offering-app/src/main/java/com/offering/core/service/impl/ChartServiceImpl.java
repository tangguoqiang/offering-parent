package com.offering.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.offering.bean.chart.PrivateChat;
import com.offering.bean.user.TopicMember;
import com.offering.constant.GloabConstant;
import com.offering.core.dao.PrivateChatDao;
import com.offering.core.dao.TopicMemberDao;
import com.offering.core.service.ChartService;

/**
 * 聊天service实现
 * @author surfacepro3
 *
 */
@Service
public class ChartServiceImpl implements ChartService{

	@Autowired
	private PrivateChatDao privateChatDao;
	
	@Autowired
	private TopicMemberDao topicMemberDao;
	
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
