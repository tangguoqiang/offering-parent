package com.offering.core.service.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.offering.bean.sys.PageInfo;
import com.offering.bean.sys.ParamInfo;
import com.offering.bean.user.User;
import com.offering.constant.GloabConstant;
import com.offering.core.dao.UserDao;
import com.offering.core.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	
	
	/**
	 * 根据用户名获取用户信息
	 * @param name
	 * @param password
	 * @return
	 */
	public User getUserInfoByNmae(String name,String password)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("select id,nickname ")
		   .append("from USER_INFO ")
		   .append("WHERE nickname=? AND type=? ");
		if(password != null)
			sql.append("AND password=? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.VARCHAR, name);
		paramInfo.setTypeAndData(Types.CHAR, GloabConstant.USER_TYPE_ADMIN);
		if(password != null)
			paramInfo.setTypeAndData(Types.VARCHAR, password);
		return userDao.getRecord(sql.toString(),paramInfo,User.class);
	}
	
	/**
	 * 查询用户数据
	 * @param user
	 * @param page
	 * @return
	 */
	public List<User> listUsers(User user,PageInfo page){
		return userDao.listUsers(user, page);
	}
	
	/**
	 * 查询用户数据的数量
	 * @param user
	 * @return
	 */
	public long getUserCount(User user){
		return userDao.getUserCount(user);
	}
	
	/**
	 * 根据用户id获取用户信息
	 * @param userId
	 * @return
	 */
	public User getUserInfoById(String userId){
		StringBuilder sql = new StringBuilder();
		sql.append("select T1.id,password,token,phone,T1.name,nickname,industry,T1.type, ")
		   .append("schoolId,T2.name as schoolName,major,grade,T3.name AS gradeName,url,rc_token, ")
		   .append("background_url,T4.company,T4.post ")
		   .append("from USER_INFO T1 ")
		   .append("LEFT JOIN SYS_SCHOOL T2 ON T2.ID=T1.schoolId ")
		   .append("LEFT JOIN SYS_DICT T3 ON T3.CODE=T1.grade AND T3.groupName=? ")
		   .append("LEFT JOIN USER_GREATER T4 ON T4.ID=T1.id ")
		   .append("WHERE T1.id=?");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.VARCHAR, GloabConstant.GROUP_GRADE);
		paramInfo.setTypeAndData(Types.BIGINT, userId);
		return userDao.getRecord(sql.toString(),paramInfo,User.class);
	}
	
	/**
	 * 更新用户状态
	 * @param userId
	 * @param status
	 */
	public void updateStatus(String userId,String status)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("update USER_INFO set status=? where id=?");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.CHAR,status);
		paramInfo.setTypeAndData(Types.BIGINT, userId);
		
		userDao.updateRecord(sql.toString(), paramInfo);
	}
	
	/**
	 * 判断用户名是否存在
	 * @param user
	 * @return
	 */
	public boolean isExistUser(User user){
		return userDao.isExistUser(user);
	}
}
