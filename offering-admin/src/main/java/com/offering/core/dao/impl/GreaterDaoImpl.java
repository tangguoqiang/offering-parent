package com.offering.core.dao.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.offering.bean.sys.PageInfo;
import com.offering.bean.sys.ParamInfo;
import com.offering.bean.user.Greater;
import com.offering.bean.user.User;
import com.offering.constant.GloabConstant;
import com.offering.core.dao.GreaterDao;
import com.offering.utils.Utils;

/**
 * 大拿dao实现
 * @author surfacepro3
 *
 */
@Repository
public class GreaterDaoImpl extends BaseDaoImpl<Greater> implements GreaterDao{
	
	/**
	 * 查询大拿列表
	 * @param page
	 * @return
	 */
	public List<Greater> listGreaters(User user,PageInfo page)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT T1.id,T2.nickname,T2.phone,T1.company,T1.post,T2.url,T1.introduce,")
		   .append("T1.online_startTime,T1.online_endTime,")
		   .append("T4.name schoolName,T3.name industryName,T1.workYears,T1.tags ")
		   .append("FROM USER_GREATER T1 ")
		   .append("INNER JOIN USER_INFO T2 ON T2.ID=T1.ID ")
		   .append("LEFT JOIN SYS_DICT T3 ON T3.code=T2.industry AND T3.groupName=? ")
		   .append("LEFT JOIN SYS_SCHOOL T4 ON T4.ID=T2.schoolId ")
		   .append("WHERE 1=1 ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.VARCHAR, GloabConstant.GROUP_INDUSTRY);
		if(!Utils.isEmpty(user.getNickname()))
		{
			sql.append(" AND T2.nickname like ? ");
			paramInfo.setTypeAndData(Types.VARCHAR, "%" + user.getNickname() + "%");
		}
		
		if(!Utils.isEmpty(user.getPhone()))
		{
			sql.append(" AND T2.phone like ? ");
			paramInfo.setTypeAndData(Types.BIGINT, user.getPhone());
		}
		
		sql.append("ORDER BY T2.insertTime DESC ");
		return getRecords(sql.toString(),paramInfo,page,Greater.class);
	}
	
	/**
	 * 获取大拿数量
	 * @param user
	 * @return
	 */
	public long getGreaterCount(User user)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT count(1) ")
		   .append("FROM USER_GREATER T1 ")
		   .append("INNER JOIN USER_INFO T2 ON T2.ID=T1.ID ")
		   .append("WHERE 1=1 ");
		ParamInfo paramInfo = new ParamInfo();
		if(!Utils.isEmpty(user.getNickname()))
		{
			sql.append(" AND T2.nickname like ? ");
			paramInfo.setTypeAndData(Types.VARCHAR, "%" + user.getNickname() + "%");
		}
		
		if(!Utils.isEmpty(user.getPhone()))
		{
			sql.append(" AND T2.phone like ? ");
			paramInfo.setTypeAndData(Types.BIGINT, user.getPhone());
		}
		return getCount(sql.toString(),paramInfo);
	}
	
	
	/**
	 * 根据大拿id获取大拿信息
	 * @param id
	 * @return
	 */
	public Greater getGreaterInfoById(String id)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT T1.id,T2.nickname,T1.company,T1.post,T2.url,T1.introduce,T2.phone,")
		   .append("T1.online_startTime,T1.online_endTime,T1.backgroud_url,T2.industry,T1.orderNo,")
		   .append("T4.name schoolName,T3.name industryName,T1.workYears,T1.tags,T1.isshow ")
		   .append("FROM USER_GREATER T1 ")
		   .append("INNER JOIN USER_INFO T2 ON T2.ID=T1.ID ")
		   .append("LEFT JOIN SYS_SCHOOL T3 ON T3.ID=T2.schoolId ")
		   .append("LEFT JOIN SYS_DICT T4 ON T4.code=T2.industry AND T4.groupName=? ")
		   .append("WHERE T1.id=?  ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.VARCHAR, GloabConstant.GROUP_INDUSTRY);
		paramInfo.setTypeAndData(Types.BIGINT, id);
		
		return getRecord(sql.toString(),paramInfo,Greater.class);
	}
	
	/**
	 * 更新大拿信息
	 * @param greater
	 */
	public void updateGreater(Greater greater){
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE USER_GREATER SET ");
		ParamInfo paramInfo = new ParamInfo();
		if(!Utils.isEmpty(greater.getCompany()))
		{
			sql.append("company=?,");
			paramInfo.setTypeAndData(Types.VARCHAR, greater.getCompany());
		}
		
		if(!Utils.isEmpty(greater.getPost()))
		{
			sql.append("post=?,");
			paramInfo.setTypeAndData(Types.VARCHAR, greater.getPost());
		}
		
		if(!Utils.isEmpty(greater.getWorkYears()))
		{
			sql.append("workYears=?,");
			paramInfo.setTypeAndData(Types.INTEGER, greater.getWorkYears());
		}
		
		if(!Utils.isEmpty(greater.getOnline_startTime()))
		{
			sql.append("online_startTime=?,");
			paramInfo.setTypeAndData(Types.VARCHAR, greater.getOnline_startTime());
		}
		
		if(!Utils.isEmpty(greater.getOnline_endTime()))
		{
			sql.append("online_endTime=?,");
			paramInfo.setTypeAndData(Types.VARCHAR, greater.getOnline_endTime());
		}
		
		if(!Utils.isEmpty(greater.getTags()))
		{
			sql.append("tags=?,");
			paramInfo.setTypeAndData(Types.VARCHAR, greater.getTags());
		}
		
		if(!Utils.isEmpty(greater.getIntroduce()))
		{
			sql.append("introduce=?,");
			paramInfo.setTypeAndData(Types.VARCHAR, greater.getIntroduce());
		}
		
		if(!Utils.isEmpty(greater.getIsshow()))
		{
			sql.append("isshow=?,");
			paramInfo.setTypeAndData(Types.CHAR, greater.getIsshow());
		}
		
		if(!Utils.isEmpty(greater.getOrderNo()))
		{
			sql.append("orderNo=?,");
			paramInfo.setTypeAndData(Types.VARCHAR, greater.getOrderNo());
		}
		
		if(sql.toString().endsWith(","))
		{
			sql.replace(sql.length() - 1, sql.length(), "");
			sql.append(" where id=?");
			paramInfo.setTypeAndData(Types.BIGINT, greater.getId());
			
			updateRecord(sql.toString(), paramInfo);
		}
	}
}
