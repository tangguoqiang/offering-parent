package com.offering.core.dao;

import com.offering.bean.TopicMember;

/**
 * 话题成员dao
 * @author surfacepro3
 *
 */
public interface TopicMemberDao extends BaseDao<TopicMember>{

	/**
	 * 判断话题成员是否存在，如果存在则返回true
	 * @param memberId
	 * @param topicId
	 * @return
	 */
     boolean isExists(String memberId,String topicId);
}
