package com.offering.core.dao.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.offering.bean.CommunityTopic;
import com.offering.bean.ParamInfo;
import com.offering.constant.GloabConstant;
import com.offering.core.dao.CommunityTopicDao;
import com.offering.utils.Utils;

/**
 * 社区话题dao实现
 * @author surfacepro3
 *
 */
@Repository
public class CommunityTopicDaoImpl extends BaseDaoImpl<CommunityTopic> implements CommunityTopicDao{


	/**
	 * 获取最新话题列表
	 * @param type
	 * @param time
	 * @return
	 */
	public List<CommunityTopic> listTopics_new(String type,String time){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT T1.id,T1.content,T1.createrId,T1.createTime,")
		   .append("T2.nickname name,T2.url,T2.type,T3.name school,T4.company,T4.post,")
		   .append("(SELECT count(1) FROM COMMUNITY_TOPIC_PRAISE T WHERE T.topicId=T1.id) praiseNum,")
		   .append("(SELECT count(1) FROM COMMUNITY_TOPIC_COMMENT T WHERE T.topicId=T1.id) commentNum ")
		   .append("FROM COMMUNITY_TOPIC T1 ")
		   .append("INNER JOIN USER_INFO T2 ON T2.id=T1.createrId ")
		   .append("LEFT JOIN SYS_SCHOOL T3 ON T3.id=T2.schoolId ")
		   .append("LEFT JOIN USER_GREATER T4 ON T4.id=T1.createrId ")
		   .append("WHERE T1.isTop=? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.CHAR, GloabConstant.YESNO_NO);
		if(!Utils.isEmpty(time)){
			sql.append("AND T1.createTime < ? ");
			paramInfo.setTypeAndData(Types.BIGINT, time);
		}
		sql.append("ORDER BY T1.createTime DESC ");
		sql.append("LIMIT 15 ");
		return getRecords(sql.toString(), paramInfo, CommunityTopic.class);
	}
	
	/**
	 * 获取热门话题列表
	 * @param type
	 * @param praiseNum
	 * @return
	 */
	public List<CommunityTopic> listTopics_hot(String type,String praiseNum){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id,content,createrId,createTime,name,url,type,")
		   .append("school,company,post,praiseNum,commentNum FROM (");
		sql.append("SELECT T1.id,T1.content,T1.createrId,T1.createTime,")
		   .append("T2.nickname name,T2.url,T2.type,T3.name school,T4.company,T4.post,")
		   .append("(SELECT count(1) FROM COMMUNITY_TOPIC_PRAISE T WHERE T.topicId=T1.id) praiseNum,")
		   .append("(SELECT count(1) FROM COMMUNITY_TOPIC_COMMENT T WHERE T.topicId=T1.id) commentNum ")
		   .append("FROM COMMUNITY_TOPIC T1 ")
		   .append("INNER JOIN USER_INFO T2 ON T2.id=T1.createrId ")
		   .append("LEFT JOIN SYS_SCHOOL T3 ON T3.id=T2.schoolId ")
		   .append("LEFT JOIN USER_GREATER T4 ON T4.id=T1.createrId ")
		   .append("WHERE T1.isTop=? ) TMP ");
		
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.CHAR, GloabConstant.YESNO_NO);
		if(!Utils.isEmpty(praiseNum)){
			sql.append("WHERE praiseNum < ? ");
			paramInfo.setTypeAndData(Types.BIGINT, praiseNum);
		}
		sql.append("ORDER BY praiseNum DESC ");
		sql.append("LIMIT 15 ");
		return getRecords(sql.toString(), paramInfo, CommunityTopic.class);
	}
	
	/**
	 * 获取置顶帖
	 * @return
	 */
	public CommunityTopic getTopTopic(){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT T1.id,T1.content,T1.createrId,T1.createTime,")
		   .append("T2.nickname name,T2.url,T2.type,T3.name school,T4.company,T4.post,")
		   .append("(SELECT count(1) FROM COMMUNITY_TOPIC_PRAISE T WHERE T.topicId=T1.id) praiseNum,")
		   .append("(SELECT count(1) FROM COMMUNITY_TOPIC_COMMENT T WHERE T.topicId=T1.id) commentNum ")
		   .append("FROM COMMUNITY_TOPIC T1 ")
		   .append("INNER JOIN USER_INFO T2 ON T2.id=T1.createrId ")
		   .append("LEFT JOIN SYS_SCHOOL T3 ON T3.id=T2.schoolId ")
		   .append("LEFT JOIN USER_GREATER T4 ON T4.id=T1.createrId ")
		   .append("WHERE T1.isTop=? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.CHAR, GloabConstant.YESNO_YES);
		
		return getRecord(sql.toString(), paramInfo, CommunityTopic.class);
	}
	
	/**
	 * 根据id获取话题信息
	 * @param id
	 * @return
	 */
	public CommunityTopic getTopicInfoById(String id){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT T1.id,T1.content,T1.createrId,T1.createTime,")
		   .append("T2.nickname name,T2.url,T2.type,T3.name school,T4.company,T4.post,")
		   .append("(SELECT count(1) FROM COMMUNITY_TOPIC_PRAISE T WHERE T.topicId=T1.id) praiseNum,")
		   .append("(SELECT count(1) FROM COMMUNITY_TOPIC_COMMENT T WHERE T.topicId=T1.id) commentNum ")
		   .append("FROM COMMUNITY_TOPIC T1 ")
		   .append("INNER JOIN USER_INFO T2 ON T2.id=T1.createrId ")
		   .append("LEFT JOIN SYS_SCHOOL T3 ON T3.id=T2.schoolId ")
		   .append("LEFT JOIN USER_GREATER T4 ON T4.id=T1.createrId ")
		   .append("WHERE T1.id=? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.BIGINT, id);
		
		return getRecord(sql.toString(), paramInfo, CommunityTopic.class);
	}
}
