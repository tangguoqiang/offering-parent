package com.offering.core.dao.impl;

import java.sql.Types;

import org.springframework.stereotype.Repository;

import com.offering.bean.ParamInfo;
import com.offering.bean.TopicMember;
import com.offering.core.dao.TopicMemberDao;

/**
 * 话题成员dao实现
 * @author surfacepro3
 *
 */
@Repository
public class TopicMemberDaoImpl extends BaseDaoImpl<TopicMember> implements TopicMemberDao{

	/**
	 * 判断话题成员是否存在，如果存在则返回true
	 * @param memberId
	 * @param topicId
	 * @return
	 */
     public boolean isExists(String memberId,String topicId)
     {
 		StringBuilder sql = new StringBuilder();
 		sql.append("SELECT id FROM TOPIC_MEMBER ")
 		   .append("WHERE memberId=? AND topicId=? ");
 		ParamInfo paramInfo = new ParamInfo();
 		paramInfo.setTypeAndData(Types.BIGINT, memberId);
 		paramInfo.setTypeAndData(Types.BIGINT, topicId);
 		if(getCount(sql.toString(), paramInfo) > 0)
 			return true;
 		return false;
 	}
}
