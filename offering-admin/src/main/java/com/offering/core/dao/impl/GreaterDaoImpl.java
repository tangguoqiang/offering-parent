package com.offering.core.dao.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.offering.bean.Greater;
import com.offering.bean.PageInfo;
import com.offering.bean.ParamInfo;
import com.offering.constant.GloabConstant;
import com.offering.core.dao.GreaterDao;

/**
 * 大拿dao实现
 * @author surfacepro3
 *
 */
@Repository
public class GreaterDaoImpl extends BaseDaoImpl<Greater> implements GreaterDao{
	
	/**
	 * 查询大拿列表(app端)
	 * @param page
	 * @return
	 */
	public List<Greater> listGreaters_app(PageInfo page)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT T1.id,T2.nickname,T1.post,T2.url,T1.introduce,")
		   .append("T1.online_startTime,T1.online_endTime,")
		   .append("CONCAT(IFNULL(T4.name,''),',',T1.company,',',T3.name,',',T1.workYears,'年工作经验,',T1.tags) tags ")
		   .append("FROM USER_GREATER T1 ")
		   .append("INNER JOIN USER_INFO T2 ON T2.ID=T1.ID ")
		   .append("LEFT JOIN SYS_DICT T3 ON T3.code=T1.industry AND T3.groupName=? ")
		   .append("LEFT JOIN SYS_SCHOOL T4 ON T4.ID=T2.schoolId ")
		   .append("WHERE isshow=?  ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.VARCHAR, GloabConstant.GROUP_INDUSTRY);
		paramInfo.setTypeAndData(Types.CHAR, GloabConstant.YESNO_YES);
		
		sql.append("ORDER BY orderNo ASC ");
		return getRecords(sql.toString(),paramInfo,page,Greater.class);
	}
	
	/**
	 * 根据大拿id获取大拿信息(app端)
	 * @param id
	 * @return
	 */
	public Greater getGreaterInfoById_app(String id)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT T1.id,T2.nickname,T1.post,T2.url,T1.introduce,")
		   .append("T1.online_startTime,T1.online_endTime,T1.backgroud_url,")
		   .append("CONCAT(IFNULL(T3.name,''),',',T1.company,',',T1.industry,',',T1.workYears,'年工作经验,',T1.tags) tags ")
		   .append("FROM USER_GREATER T1 ")
		   .append("INNER JOIN USER_INFO T2 ON T2.ID=T1.ID ")
		   .append("LEFT JOIN SYS_SCHOOL T3 ON T3.ID=T2.schoolId ")
		   .append("WHERE T1.id=?  ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.BIGINT, id);
		
		return getRecord(sql.toString(),paramInfo,Greater.class);
	}
}
