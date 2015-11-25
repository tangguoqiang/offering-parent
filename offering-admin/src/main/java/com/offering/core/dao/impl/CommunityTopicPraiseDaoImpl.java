package com.offering.core.dao.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.offering.bean.community.CommunityTopicPraise;
import com.offering.bean.sys.ParamInfo;
import com.offering.constant.GloabConstant;
import com.offering.core.dao.CommunityTopicPraiseDao;

/**
 * 话题点赞dao实现
 * @author surfacepro3
 *
 */
@Repository
public class CommunityTopicPraiseDaoImpl extends BaseDaoImpl<CommunityTopicPraise> 
	implements CommunityTopicPraiseDao{

	/**
	 * 判断用户是否为该话题点过赞，点过则返回true,否则返回false
	 * @param createrId
	 * @param topicId
	 * @return
	 */
	public boolean isExistsPraise(String createrId,String topicId){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT count(1) FROM COMMUNITY_TOPIC_PRAISE WHERE topicId=? AND createrId=? ");
		ParamInfo param = new ParamInfo();
		param.setTypeAndData(Types.BIGINT, topicId);
		param.setTypeAndData(Types.BIGINT, createrId);
		long n = getCount(sql.toString(), param, 0);
		if(n > 0)
			return true;
		return false;
	}
	
	/**
	 * 根据话题id获取点赞数
	 * @param topicId
	 * @return
	 */
	public long getPraiseCount(String topicId){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT count(1) FROM COMMUNITY_TOPIC_PRAISE WHERE topicId=?");
		ParamInfo param = new ParamInfo();
		param.setTypeAndData(Types.BIGINT, topicId);
		return getCount(sql.toString(), param, 0);
	}
	
	/**
	 * 更新状态到已读
	 * @param id
	 */
	public void updateToRead(String id){
		StringBuilder sql = new StringBuilder();
		ParamInfo paramInfo = new ParamInfo();
		sql.append("UPDATE COMMUNITY_TOPIC_PRAISE SET isRead=? WHERE id=? ");
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
			sql.append("UPDATE COMMUNITY_TOPIC_PRAISE SET isRead=? WHERE id IN (");
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
	 * 删除赞
	 * @param createrId
	 * @param topicId
	 */
	public void delPraise(String createrId,String topicId){
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM COMMUNITY_TOPIC_PRAISE WHERE topicId=? AND createrId=? ");
		ParamInfo param = new ParamInfo();
		param.setTypeAndData(Types.BIGINT, topicId);
		param.setTypeAndData(Types.BIGINT, createrId);
		delRecord(sql.toString(), param);
	}
	
	/**
	 * 根据话题id删除点赞信息
	 * @param topidId
	 */
	public void delRecByTopicId(String topidId){
		StringBuilder sql = new StringBuilder();
		ParamInfo paramInfo = new ParamInfo();
		sql.append("DELETE FROM COMMUNITY_TOPIC_PRAISE WHERE topicId=? ");
		paramInfo.setTypeAndData(Types.BIGINT, topidId);
		delRecord(sql.toString(), paramInfo);
	}
}
