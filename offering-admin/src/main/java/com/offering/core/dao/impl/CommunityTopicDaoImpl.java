package com.offering.core.dao.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.offering.bean.community.CommunityTopic;
import com.offering.bean.sys.PageInfo;
import com.offering.bean.sys.ParamInfo;
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
	 * 获取话题列表
	 * @param topic
	 * @param page
	 * @return
	 */
	public List<CommunityTopic> listTopics(CommunityTopic topic,PageInfo page){
		StringBuilder sql = new StringBuilder();
		ParamInfo paramInfo = new ParamInfo();
		sql.append("SELECT T1.id,T1.content,T1.createrId,T1.createTime,")
		   .append("T2.nickname name,T2.url,T1.isTop ")
		   .append("FROM COMMUNITY_TOPIC T1 ")
		   .append("INNER JOIN USER_INFO T2 ON T2.id=T1.createrId ")
		   .append("WHERE 1=1 ");
		
		if(!Utils.isEmpty(topic.getIsTop())){
			sql.append("AND T1.isTop = ? ");
			paramInfo.setTypeAndData(Types.CHAR, topic.getIsTop());
		}
		
		if(!Utils.isEmpty(topic.getContent())){
			sql.append("AND T1.content like ? ");
			paramInfo.setTypeAndData(Types.VARCHAR, "%" + topic.getContent() + "%");
		}
		sql.append("ORDER BY T1.isTop ASC,T1.createTime DESC ");
		return getRecords(sql.toString(), paramInfo, page,CommunityTopic.class);
	}
	
	public long getTopicCount(CommunityTopic topic){
		StringBuilder sql = new StringBuilder();
		ParamInfo paramInfo = new ParamInfo();
		sql.append("SELECT count(1)")
		   .append("FROM COMMUNITY_TOPIC T1 ")
		   .append("INNER JOIN USER_INFO T2 ON T2.id=T1.createrId ")
		   .append("WHERE 1=1 ");
		
		if(!Utils.isEmpty(topic.getIsTop())){
			sql.append("AND T1.isTop = ? ");
			paramInfo.setTypeAndData(Types.CHAR, topic.getIsTop());
		}
		
		if(!Utils.isEmpty(topic.getContent())){
			sql.append("AND T1.content like ? ");
			paramInfo.setTypeAndData(Types.VARCHAR, "%" + topic.getContent() + "%");
		}
		return getCount(sql.toString(), paramInfo, 0);
	}
	
	/**
	 * 根据话题id获取话题信息
	 * @param id
	 * @return
	 */
	public CommunityTopic getTopicInfo(String id){
		StringBuilder sql = new StringBuilder();
		ParamInfo paramInfo = new ParamInfo();
		sql.append("SELECT T1.id,T1.content,T1.createrId,T1.createTime,")
		   .append("T2.nickname name,T2.url,T1.isTop,")
		   .append("(SELECT count(1) FROM COMMUNITY_TOPIC_PRAISE T WHERE T.topicId=T1.id) praiseNum,")
		   .append("(SELECT count(1) FROM COMMUNITY_TOPIC_COMMENT T WHERE T.topicId=T1.id) commentNum ")
		   .append("FROM COMMUNITY_TOPIC T1 ")
		   .append("INNER JOIN USER_INFO T2 ON T2.id=T1.createrId ")
		   .append("WHERE T1.id=? ");
		
		paramInfo.setTypeAndData(Types.BIGINT, id);
		
		return getRecord(sql.toString(), paramInfo,CommunityTopic.class);
	}
	
	/**
	 * 根据id删除话题
	 * @param id
	 */
	public void delRecById(String id){
		StringBuilder sql = new StringBuilder();
		ParamInfo paramInfo = new ParamInfo();
		sql.append("DELETE FROM COMMUNITY_TOPIC WHERE id=? ");
		paramInfo.setTypeAndData(Types.BIGINT, id);
		delRecord(sql.toString(), paramInfo);
	}
	
	/**
	 * 话题不置顶
	 * @param id
	 * @return
	 */
	public void notTop(){
		StringBuilder sql = new StringBuilder();
		ParamInfo paramInfo = new ParamInfo();
		sql.append("UPDATE COMMUNITY_TOPIC SET isTop=? WHERE isTop=? ");
		paramInfo.setTypeAndData(Types.CHAR, GloabConstant.YESNO_NO);
		paramInfo.setTypeAndData(Types.CHAR, GloabConstant.YESNO_YES);
		updateRecord(sql.toString(), paramInfo);
	}
	
	/**
	 * 话题置顶
	 * @param id
	 * @return
	 */
	public void top(String id){
		StringBuilder sql = new StringBuilder();
		ParamInfo paramInfo = new ParamInfo();
		sql.append("UPDATE COMMUNITY_TOPIC SET isTop=? WHERE id=? ");
		paramInfo.setTypeAndData(Types.CHAR, GloabConstant.YESNO_YES);
		paramInfo.setTypeAndData(Types.BIGINT, id);
		updateRecord(sql.toString(), paramInfo);
	}
}
