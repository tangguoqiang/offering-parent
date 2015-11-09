package com.offering.core.dao;

import java.util.List;

import com.offering.bean.CommunityTopicComment;

/**
 * 话题评论dao
 * @author surfacepro3
 *
 */
public interface CommunityTopicCommentDao extends BaseDao<CommunityTopicComment>{

	/**
	 * 获取未读消息列表
	 * @param userId
	 * @return
	 */
	List<CommunityTopicComment> listComments_unread(String userId);
	
	/**
	 * 更新状态到已读
	 * @param id
	 */
	void updateToRead(String id);
	
	/**
	 * 更新状态到已读(批量)
	 * @param userId
	 */
	void updateToRead(List<String> idList);
	
	/**
	 * 根据话题id加载评论列表
	 * @param topicId
	 * @return
	 */
	List<CommunityTopicComment> listComments(String topicId,String time);
	
	/**
	 * 根据id获取评论信息
	 * @param id
	 * @return
	 */
	CommunityTopicComment getCommentById(String id);
}
