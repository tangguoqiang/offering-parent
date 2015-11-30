package com.offering.core.dao;

import java.util.List;

import com.offering.bean.community.CommunityTopicPraise;

/**
 * 话题点赞dao
 * @author surfacepro3
 *
 */
public interface CommunityTopicPraiseDao extends BaseDao<CommunityTopicPraise>{

	/**
	 * 判断用户是否为该话题点过赞，点过则返回true,否则返回false
	 * @param createrId
	 * @param topicId
	 * @return
	 */
	boolean isExistsPraise(String createrId,String topicId);
	
	/**
	 * 根据话题id获取点赞数
	 * @param topicId
	 * @return
	 */
	long getPraiseCount(String topicId);
	
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
	 * 删除赞
	 * @param createrId
	 * @param topicId
	 */
	void delPraise(String createrId,String topicId);
	
	/**
	 * 根据话题id删除点赞信息
	 * @param topidId
	 */
	void delRecByTopicId(String topidId);
}
