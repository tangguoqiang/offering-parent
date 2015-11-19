package com.offering.core.dao.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.offering.bean.community.CommunityTopicComment;
import com.offering.bean.sys.ParamInfo;
import com.offering.constant.GloabConstant;
import com.offering.core.dao.CommunityTopicCommentDao;
import com.offering.utils.Utils;

/**
 * 话题评论dao实现
 * @author surfacepro3
 *
 */
@Repository
public class CommunityTopicCommentDaoImpl extends BaseDaoImpl<CommunityTopicComment> 
	implements CommunityTopicCommentDao{

	/**
	 * 获取未读消息列表
	 * @param userId
	 * @return
	 */
	public List<CommunityTopicComment> listComments_unread(String userId){
		StringBuilder sql = new StringBuilder();
		ParamInfo paramInfo = new ParamInfo();
		sql.append("SELECT O1.id,O1.topicId,O1.commentType,O1.createrId,O1.content,O1.createTime,O1.comment,")
		   .append("O2.nickname name,O2.url,O2.type,O4.name school,O3.company,O3.post ")
		   .append("FROM (");
		sql.append("SELECT T1.id,T1.topicId,'1' commentType,T1.createrId,T2.content,T1.createTime,T1.comment ")
		   .append("FROM COMMUNITY_TOPIC_COMMENT T1 ")
		   .append("INNER JOIN COMMUNITY_TOPIC T2 ON T2.id=T1.topicId ")
		   .append("LEFT JOIN COMMUNITY_TOPIC_COMMENT T3 ON T3.id=T1.parentId ")
		   .append("WHERE T1.isRead=? ")
		   .append("AND ((T1.parentId is null AND T2.createrId=?) OR T3.createrId=? ) ");
		
		paramInfo.setTypeAndData(Types.CHAR, GloabConstant.YESNO_NO);
		paramInfo.setTypeAndData(Types.BIGINT, userId);
		paramInfo.setTypeAndData(Types.BIGINT, userId);
		
		sql.append("UNION ALL ");
		sql.append("SELECT P1.id,P1.topicId,'2' commentType,P1.createrId,P2.content,P1.createTime,")
		   .append("'给你点了个赞' comment ")
		   .append("FROM COMMUNITY_TOPIC_PRAISE P1 ")
		   .append("INNER JOIN COMMUNITY_TOPIC P2 ON P2.id=P1.topicId ")
		   .append("WHERE P1.isRead=? AND P2.createrId=? ");
		
		paramInfo.setTypeAndData(Types.CHAR, GloabConstant.YESNO_NO);
		paramInfo.setTypeAndData(Types.BIGINT, userId);
		
		sql.append(") O1 ")
		   .append("INNER JOIN USER_INFO O2 ON O2.id=O1.createrId ")
		   .append("LEFT JOIN USER_GREATER O3 ON O3.id=O2.id ")
		   .append("LEFT JOIN SYS_SCHOOL O4 ON O4.id=O2.schoolId ")
		   .append("ORDER BY createTime DESC ");
		
		return getRecords(sql.toString(), paramInfo, CommunityTopicComment.class);
	}
	
	/**
	 * 更新状态到已读
	 * @param id
	 */
	public void updateToRead(String id){
		StringBuilder sql = new StringBuilder();
		ParamInfo paramInfo = new ParamInfo();
		sql.append("UPDATE COMMUNITY_TOPIC_COMMENT SET isRead=? WHERE id=? ");
		paramInfo.setTypeAndData(Types.CHAR, GloabConstant.YESNO_YES);
		paramInfo.setTypeAndData(Types.BIGINT, id);
		updateRecord(sql.toString(), paramInfo);
	}
	
	/**
	 * 更新状态到已读(批量)
	 * @param userId
	 */
	public void updateToRead(List<String> idList){
		if(idList != null && idList.size() > 0){
			StringBuilder sql = new StringBuilder();
			ParamInfo paramInfo = new ParamInfo();
			sql.append("UPDATE COMMUNITY_TOPIC_COMMENT SET isRead=? WHERE id IN (");
			paramInfo.setTypeAndData(Types.CHAR, GloabConstant.YESNO_YES);
			for(String id : idList){
				sql.append("?,");
				paramInfo.setTypeAndData(Types.BIGINT, id);
			}
			sql.replace(sql.length() - 1, sql.length(), ")");
			updateRecord(sql.toString(), paramInfo);
		}
	}
	
	/**
	 * 根据话题id加载评论列表
	 * @param topicId
	 * @return
	 */
	public List<CommunityTopicComment> listComments(String topicId,String time){
		StringBuilder sql = new StringBuilder();
		ParamInfo paramInfo = new ParamInfo();
		sql.append("SELECT T1.id,T1.createrId,T1.comment,T1.createTime,")
		   .append("T2.nickname name,T2.url,T2.type,T3.company,T3.post,T4.name school,")
		   .append("T6.type toType,T6.nickname toUserName ")
		   .append("FROM COMMUNITY_TOPIC_COMMENT T1 ")
		   .append("INNER JOIN USER_INFO T2 ON T2.id=T1.createrId ")
		   .append("LEFT JOIN USER_GREATER T3 ON T3.id=T1.createrId ")
		   .append("LEFT JOIN SYS_SCHOOL T4 ON T4.id=T2.schoolId ")
		   .append("LEFT JOIN COMMUNITY_TOPIC_COMMENT T5 ON T5.id=T1.parentId ")
		   .append("LEFT JOIN USER_INFO T6 ON T6.id=T5.createrId ")
		   .append("WHERE T1.topicId=? ");
		paramInfo.setTypeAndData(Types.BIGINT, topicId);
		if(!Utils.isEmpty(time)){
			sql.append("AND T1.createTime <? ");
			paramInfo.setTypeAndData(Types.BIGINT, time);
		}
		sql.append("ORDER BY T1.createTime DESC LIMIT 15 ");
		return getRecords(sql.toString(), paramInfo, CommunityTopicComment.class);
	}
	
	/**
	 * 根据id获取评论信息
	 * @param id
	 * @return
	 */
	public CommunityTopicComment getCommentById(String id){
		StringBuilder sql = new StringBuilder();
		ParamInfo paramInfo = new ParamInfo();
		sql.append("SELECT T1.id,T1.createrId,T1.comment,T1.createTime,")
		   .append("T2.nickname name,T2.url,T2.type,T3.company,T3.post,T4.name school,")
		   .append("T6.type toType,T6.nickname toUserName ")
		   .append("FROM COMMUNITY_TOPIC_COMMENT T1 ")
		   .append("INNER JOIN USER_INFO T2 ON T2.id=T1.createrId ")
		   .append("LEFT JOIN USER_GREATER T3 ON T3.id=T1.createrId ")
		   .append("LEFT JOIN SYS_SCHOOL T4 ON T4.id=T2.schoolId ")
		   .append("LEFT JOIN COMMUNITY_TOPIC_COMMENT T5 ON T5.id=T1.parentId ")
		   .append("LEFT JOIN USER_INFO T6 ON T6.id=T5.createrId ")
		   .append("WHERE T1.id=? ");
		paramInfo.setTypeAndData(Types.BIGINT, id);
		return getRecord(sql.toString(), paramInfo, CommunityTopicComment.class);
	}
}
