package com.offering.core.dao.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.offering.bean.community.CommunityTopicComment;
import com.offering.bean.sys.PageInfo;
import com.offering.bean.sys.ParamInfo;
import com.offering.core.dao.CommunityTopicCommentDao;

/**
 * 话题评论dao实现
 * @author surfacepro3
 *
 */
@Repository
public class CommunityTopicCommentDaoImpl extends BaseDaoImpl<CommunityTopicComment> 
	implements CommunityTopicCommentDao{

	/**
	 * 根据话题id加载评论列表
	 * @param topicId
	 * @return
	 */
	public List<CommunityTopicComment> listComments(String topicId,PageInfo page){
		StringBuilder sql = new StringBuilder();
		ParamInfo paramInfo = new ParamInfo();
		sql.append("SELECT T1.id,T1.createrId,T1.comment,T1.createTime,")
		   .append("T2.nickname name,T2.url,")
		   .append("T6.type toType,T6.nickname toUserName ")
		   .append("FROM COMMUNITY_TOPIC_COMMENT T1 ")
		   .append("INNER JOIN USER_INFO T2 ON T2.id=T1.createrId ")
		   .append("LEFT JOIN COMMUNITY_TOPIC_COMMENT T5 ON T5.id=T1.parentId ")
		   .append("LEFT JOIN USER_INFO T6 ON T6.id=T5.createrId ")
		   .append("WHERE T1.topicId=? ");
		paramInfo.setTypeAndData(Types.BIGINT, topicId);
		sql.append("ORDER BY T1.createTime DESC ");
		return getRecords(sql.toString(), paramInfo, page, CommunityTopicComment.class);
	}
	
	public long getCommentsCount(String topicId){
		StringBuilder sql = new StringBuilder();
		ParamInfo paramInfo = new ParamInfo();
		sql.append("SELECT count(1) ")
		   .append("FROM COMMUNITY_TOPIC_COMMENT T1 ")
		   .append("INNER JOIN USER_INFO T2 ON T2.id=T1.createrId ")
		   .append("LEFT JOIN COMMUNITY_TOPIC_COMMENT T5 ON T5.id=T1.parentId ")
		   .append("LEFT JOIN USER_INFO T6 ON T6.id=T5.createrId ")
		   .append("WHERE T1.topicId=? ");
		paramInfo.setTypeAndData(Types.BIGINT, topicId);
		return getCount(sql.toString(), paramInfo, 0);
	}
	
	/**
	 * 根据话题id删除评论信息
	 * @param topidId
	 */
	public void delRecByTopicId(String topidId){
		StringBuilder sql = new StringBuilder();
		ParamInfo paramInfo = new ParamInfo();
		sql.append("DELETE FROM COMMUNITY_TOPIC_COMMENT WHERE topicId=? ");
		paramInfo.setTypeAndData(Types.BIGINT, topidId);
		delRecord(sql.toString(), paramInfo);
	}
}
