package com.offering.core.dao;

import java.util.List;

import com.offering.bean.community.CommunityTopicComment;
import com.offering.bean.sys.PageInfo;

/**
 * 话题评论dao
 * @author surfacepro3
 *
 */
public interface CommunityTopicCommentDao extends BaseDao<CommunityTopicComment>{

	/**
	 * 获取评论列表
	 * @param topicId
	 * @param page
	 * @return
	 */
	List<CommunityTopicComment> listComments(String topicId,PageInfo page);
	long getCommentsCount(String topicId);
	
	/**
	 * 根据话题id删除评论信息
	 * @param topidId
	 */
	void delRecByTopicId(String topidId);
}
