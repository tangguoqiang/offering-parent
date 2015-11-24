package com.offering.core.dao.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.offering.bean.activity.Activity;
import com.offering.bean.sys.ParamInfo;
import com.offering.constant.DBConstant;
import com.offering.constant.GloabConstant;
import com.offering.core.dao.ActivityDao;

/**
 * 活动dao实现
 * @author surfacepro3
 *
 */
@Repository
public class ActivityDaoImpl extends BaseDaoImpl<Activity> implements ActivityDao{

	/**
	 * 根据大拿id获取活动
	 * @param greaterId
	 * @return
	 */
	public List<Activity> listActivitysByGreaterId(String greaterId)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT T1.id,T1.title,T1.url,case when T1.status='2' THEN '3' else T1.type end type ")
		   .append("FROM ACTIVITY_INFO T1 ")
		   .append("WHERE createrId=?  AND status<> ? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.BIGINT, greaterId);
		paramInfo.setTypeAndData(Types.CHAR, GloabConstant.ACTIVITY_STATUS_CG);
		
		return getRecords(sql.toString(),paramInfo,Activity.class);
	
	}
	
	/**
	 * 根据用户id查询参加过的活动
	 * @param userId
	 * @param type
	 * @return
	 */
	public List<Activity> activityHistory(String userId,String type){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT T2.id,T2.title,T2.url,case when T2.status='2' THEN '3' else T2.type end type ")
		   .append("FROM ").append(DBConstant.RC_GROUP_MEMBER).append(" T1 ")
		   .append("INNER JOIN ").append(DBConstant.ACTIVITY_INFO)
		   .append(" T2 ON T2.id=T1.groupId ")
		   .append("WHERE T1.memberId=?  AND T2.status<> ? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.BIGINT, userId);
		paramInfo.setTypeAndData(Types.CHAR, GloabConstant.ACTIVITY_STATUS_CG);
		
		return getRecords(sql.toString(),paramInfo,Activity.class);
	}
}
