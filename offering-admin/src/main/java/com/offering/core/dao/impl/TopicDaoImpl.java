package com.offering.core.dao.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.offering.bean.sys.ParamInfo;
import com.offering.bean.user.Topic;
import com.offering.constant.DBConstant;
import com.offering.core.dao.TopicDao;

/**
 * 话题dao实现
 * @author surfacepro3
 *
 */
@Repository
public class TopicDaoImpl extends BaseDaoImpl<Topic> implements TopicDao{
	
	/**
	 * 根据大拿id获取话题数据
	 * @param greaterIds
	 * @return
	 */
	public List<Topic> listTopicsByGreaterId(String greaterId)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT T1.id,T1.greaterId,T1.title,T1.content ")
		   .append("FROM TOPIC_INFO T1 ")
		   .append("WHERE T1.greaterId=? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.BIGINT, greaterId);
		
		return getRecords(sql.toString(),paramInfo,Topic.class);
	}
	
	/**
	 * 根据话题id获取话题信息
	 * @param id
	 * @return
	 */
	public Topic getTopicInfoById(String id){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT T1.id,T1.greaterId,T1.title,T1.content ")
		   .append("FROM TOPIC_INFO T1 ")
		   .append("WHERE T1.id=?  ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.BIGINT, id);
		
		return getRecord(sql.toString(),paramInfo,Topic.class);
	}
	
	/**
	 * 根据大拿id删除话题数据
	 * @param greaterId
	 */
	public void delRecsByGreaterId(String greaterId){
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM  ").append(DBConstant.TOPIC_INFO)
		   .append(" WHERE greaterId=? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.BIGINT, greaterId);
		delRecord(sql.toString(), paramInfo);
	}
	
	/**
	 * 更新话题
	 * @param topic
	 */
	public void updateTopic(Topic topic){
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE ").append(DBConstant.TOPIC_INFO)
		   .append(" SET title=?,content=? WHERE id=? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.VARCHAR, topic.getTitle());
		paramInfo.setTypeAndData(Types.VARCHAR, topic.getContent());
		paramInfo.setTypeAndData(Types.BIGINT, topic.getId());
		updateRecord(sql.toString(), paramInfo);
		
	}

}
