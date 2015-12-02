package com.offering.core.dao.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.offering.bean.sys.PageInfo;
import com.offering.bean.sys.ParamInfo;
import com.offering.bean.user.User;
import com.offering.constant.GloabConstant;
import com.offering.core.dao.UserDao;
import com.offering.utils.Utils;

/**
 * 用户dao实现
 * @author surfacepro3
 *
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{

	/**
	 * 查询用户数据
	 * @param user
	 * @param page
	 * @return
	 */
	public List<User> listUsers(User user,PageInfo page){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT T1.id,nickname,phone,industry,  ")
		   .append("T2.name as schoolName,major,grade,T3.name AS gradeName ")
		   .append("FROM USER_INFO T1 ")
		   .append("LEFT JOIN SYS_SCHOOL T2 ON T2.ID=T1.schoolId ")
		   .append("LEFT JOIN SYS_DICT T3 ON T3.CODE=T1.grade AND T3.groupName=? ")
		   .append("WHERE 1=1 ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.VARCHAR, GloabConstant.GROUP_GRADE);
		if(!Utils.isEmpty(user.getNickname()))
		{
			sql.append(" AND T1.nickname like ? ");
			paramInfo.setTypeAndData(Types.VARCHAR, "%" + user.getNickname() + "%");
		}
		
		if(!Utils.isEmpty(user.getType()))
		{
			sql.append(" AND T1.type = ? ");
			paramInfo.setTypeAndData(Types.CHAR, user.getType());
		}
		
		if(!Utils.isEmpty(user.getPhone()))
		{
			sql.append(" AND phone = ? ");
			paramInfo.setTypeAndData(Types.BIGINT, user.getPhone());
		}
		return getRecords(sql.toString(),paramInfo,page,User.class);
	}
	
	/**
	 * 查询用户数据的数量
	 * @param user
	 * @return
	 */
	public long getUserCount(User user){
		StringBuilder sql = new StringBuilder();
		sql.append(" select count(1) from USER_INFO WHERE 1=1 ");
		ParamInfo paramInfo = new ParamInfo();
		if(user != null){
			if(!Utils.isEmpty(user.getName()))
			{
				sql.append(" AND nickname like ? ");
				paramInfo.setTypeAndData(Types.VARCHAR, "%" + user.getName() + "%");
			}
			
			if(!Utils.isEmpty(user.getType()))
			{
				sql.append(" AND type = ? ");
				paramInfo.setTypeAndData(Types.CHAR, user.getType());
			}
			
			if(!Utils.isEmpty(user.getPhone()))
			{
				sql.append(" AND phone = ? ");
				paramInfo.setTypeAndData(Types.BIGINT, user.getPhone());
			}
		}
		
		return getCount(sql.toString(), paramInfo);
	}
	
	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	public long insertUser(User user){
		user.setInsertTime(System.currentTimeMillis() + "");
		long id = insertRecord(user, "USER_INFO");
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		String d = sdf.format(new Date());
//		redisOp.increase(d +  "_user", 1);
		return id;
	}
	
	/**
	 * 更新用户
	 * @param user
	 * @return
	 */
	public void updateUser(User user){
		StringBuilder sql = new StringBuilder();
		sql.append("update USER_INFO set ");
		ParamInfo paramInfo = new ParamInfo();
		if(!Utils.isEmpty(user.getName()))
		{
			sql.append("name=?,");
			paramInfo.setTypeAndData(Types.VARCHAR, user.getName());
		}
		
		if(!Utils.isEmpty(user.getPhone()))
		{
			sql.append("phone=?,");
			paramInfo.setTypeAndData(Types.VARCHAR, user.getPhone());
		}
		
		if(!Utils.isEmpty(user.getNickname()))
		{
			sql.append("nickname=?,");
			paramInfo.setTypeAndData(Types.VARCHAR, user.getNickname());
		}
		
		if(!Utils.isEmpty(user.getSchoolId()))
		{
			sql.append("schoolId=?,");
			paramInfo.setTypeAndData(Types.BIGINT, user.getSchoolId());
		}
		
		if(!Utils.isEmpty(user.getMajor()))
		{
			sql.append("major=?,");
			paramInfo.setTypeAndData(Types.VARCHAR, user.getMajor());
		}
		
		if(!Utils.isEmpty(user.getGrade()))
		{
			sql.append("grade=?,");
			paramInfo.setTypeAndData(Types.CHAR, user.getGrade());
		}
		
		if(!Utils.isEmpty(user.getUrl()))
		{
			sql.append("url=?,");
			paramInfo.setTypeAndData(Types.VARCHAR, user.getUrl());
		}
		
		if(!Utils.isEmpty(user.getBackground_url()))
		{
			sql.append("background_url=?,");
			paramInfo.setTypeAndData(Types.VARCHAR, user.getBackground_url());
		}
		
		if(!Utils.isEmpty(user.getIndustry()))
		{
			sql.append("industry=?,");
			paramInfo.setTypeAndData(Types.VARCHAR, user.getIndustry());
		}
		
		if(sql.toString().endsWith(","))
		{
			sql.replace(sql.length() - 1, sql.length(), "");
			sql.append(" where id=?");
			paramInfo.setTypeAndData(Types.BIGINT, user.getId());
			
			updateRecord(sql.toString(), paramInfo);
		}
	}
	
	/**
	 * 判断用户是否存在
	 * @param user
	 * @return
	 */
	public boolean isExistUser(User user){
		StringBuilder sql = new StringBuilder();
		sql.append(" select 1 from USER_INFO WHERE phone=? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.VARCHAR, user.getPhone());
		//paramInfo.setTypeAndData(Types.CHAR, user.getType());
		if(!Utils.isEmpty(user.getId()))
		{
			sql.append("and id <> ?");
			paramInfo.setTypeAndData(Types.BIGINT, user.getId());
		}
		List<User> l = getRecords(sql.toString(),paramInfo,User.class);
		if(l != null && l.size() > 0)
			return true;
		return false;
	}
}
