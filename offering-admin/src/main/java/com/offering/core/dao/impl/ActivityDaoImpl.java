package com.offering.core.dao.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.offering.bean.activity.Activity;
import com.offering.bean.sys.ParamInfo;
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
}
