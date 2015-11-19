package com.offering.core.service.impl;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.offering.bean.sys.PageInfo;
import com.offering.bean.sys.ParamInfo;
import com.offering.bean.user.Greater;
import com.offering.bean.user.User;
import com.offering.constant.GloabConstant;
import com.offering.core.dao.BaseDao;
import com.offering.core.service.UserService;
import com.offering.redis.RedisOp;
import com.offering.utils.Utils;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private BaseDao<User> userDao;
	@Autowired
	private BaseDao<Greater> greaterDao;
	
	@Autowired
	private RedisOp redisOp;
	
	/**
	 * 根据手机号和密码获取用户信息
	 * @param phone
	 * @param password
	 * @return
	 */
	public User getUserInfoByPhone(String phone,String password){
		String sql = " select id,name,nickname,type,token,password,url from USER_INFO WHERE phone=? ";
		if(password != null)
			sql += "AND password=?";
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.BIGINT, phone);
		if(password != null)
			paramInfo.setTypeAndData(Types.VARCHAR, password);
		return userDao.getRecord(sql,paramInfo,User.class);
	}
	
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
	 * 登陆标识验证
	 * @param userId
	 * @param token
	 * @return
	 */
	public boolean checkToken(String userId,String token)
	{
		StringBuilder sql = new StringBuilder();
		sql.append(" select 1 from USER_INFO WHERE id=? AND token=? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.BIGINT, userId);
		paramInfo.setTypeAndData(Types.VARCHAR, token);
		List<User> l = userDao.getRecords(sql.toString(),paramInfo,User.class);
		if(l != null && l.size() > 0)
			return true;
		return false;
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
		   .append("background_url ")
		   .append("from USER_INFO T1 ")
		   .append("LEFT JOIN SYS_SCHOOL T2 ON T2.ID=T1.schoolId ")
		   .append("LEFT JOIN SYS_DICT T3 ON T3.CODE=T1.grade AND T3.groupName=? ")
		   .append("WHERE T1.id=?");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.VARCHAR, GloabConstant.GROUP_GRADE);
		paramInfo.setTypeAndData(Types.BIGINT, userId);
		return userDao.getRecord(sql.toString(),paramInfo,User.class);
	}
	
	/**
	 * 更新token
	 * @param userId
	 * @param token
	 */
	public void updateToken(String userId,String token)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("update USER_INFO set token=? where id=?");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.VARCHAR,token);
		paramInfo.setTypeAndData(Types.BIGINT, userId);
		
		userDao.updateRecord(sql.toString(), paramInfo);
	}
	
	/**
	 * 更新融云token
	 * @param userId
	 * @param token
	 */
	public void updateRCToken(String userId,String token)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("update USER_INFO set rc_token=? where id=?");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.VARCHAR,token);
		paramInfo.setTypeAndData(Types.BIGINT, userId);
		
		userDao.updateRecord(sql.toString(), paramInfo);
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
		
//		if(!Utils.isEmpty(user.getStatus()))
//		{
//			sql.append(" AND status = ? ");
//			paramInfo.setTypeAndData(Types.CHAR, user.getStatus());
//		}
		return userDao.getRecords(sql.toString(),paramInfo,page,User.class);
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
		if(!Utils.isEmpty(user.getName()))
		{
			sql.append(" AND name like ? ");
			paramInfo.setTypeAndData(Types.VARCHAR, "%" + user.getName() + "%");
		}
		
		if(!Utils.isEmpty(user.getType()))
		{
			sql.append(" AND type = ? ");
			paramInfo.setTypeAndData(Types.CHAR, user.getType());
		}
		
		if(!Utils.isEmpty(user.getStatus()))
		{
			sql.append(" AND status = ? ");
			paramInfo.setTypeAndData(Types.CHAR, user.getStatus());
		}
		return userDao.getCount(sql.toString(), paramInfo);
	}
	
	/**
	 * 根据用户名查询用户数据
	 * @param userName
	 * @return
	 */
	public User getUserByName(String userName){
		StringBuilder sql = new StringBuilder();
		sql.append(" select id,code,name,status,email,phone from USER_INFO WHERE name=? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.VARCHAR, userName);
		List<User> l = userDao.getRecords(sql.toString(),paramInfo,User.class);
		if(l != null && l.size() > 0)
			return l.get(0);
		return null;
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
		List<User> l = userDao.getRecords(sql.toString(),paramInfo,User.class);
		if(l != null && l.size() > 0)
			return true;
		return false;
	}
	
	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	public long insertUser(User user){
		user.setInsertTime(System.currentTimeMillis() + "");
		long id = userDao.insertRecord(user, "USER_INFO");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String d = sdf.format(new Date());
		redisOp.increase(d +  "_user", 1);
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
			
			userDao.updateRecord(sql.toString(), paramInfo);
		}
	}
	
	/**
	 * 删除用户
	 * @param id
	 */
	public void delUser(String id){
		StringBuilder sql = new StringBuilder();
		sql.append("delete from USER_INFO where id=? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.BIGINT, id);
		userDao.delRecord(sql.toString(), paramInfo);
	}
	
	/**
	 * 更新密码
	 * @param id
	 * @param password
	 */
	public void updatePassword(String id,String password)
	{
		String sql = "update USER_INFO set password=? where id=?";
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.VARCHAR, password);
		paramInfo.setTypeAndData(Types.BIGINT, id);
		userDao.updateRecord(sql.toString(), paramInfo);
	}
	
	/**
	 * 第三方登陆获取用户信息
	 * @param openId
	 * @param loginType
	 * @return
	 */
	public User getUserInfoByOpenId(String openId,String loginType)
	{
		String sql = " select id,name,type,token,password,nickname,url "
				+ "from USER_INFO WHERE openId=? AND login_type=?";
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.VARCHAR, openId);
		paramInfo.setTypeAndData(Types.CHAR, loginType);
		return userDao.getRecord(sql,paramInfo,User.class);
	}
	
	/**
	 * 查询大拿列表
	 * @param page
	 * @return
	 */
	public List<Greater> listGreaters(User user,PageInfo page)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT T1.id,T2.phone,T1.tags,T2.nickname,T1.company,T1.post,T2.url,T1.answerTimes ")
		   .append("FROM USER_GREATER T1 ")
		   .append("INNER JOIN USER_INFO T2 ON T2.ID=T1.ID ")
		   .append("WHERE 1=1 ");
		ParamInfo paramInfo = new ParamInfo();
		if(user != null)
		{
			if(!Utils.isEmpty(user.getNickname()))
			{
				sql.append(" AND T2.nickname like ? ");
				paramInfo.setTypeAndData(Types.VARCHAR, "%" + user.getNickname() + "%");
			}
		}else{
			sql.append("AND isshow=? ");
			paramInfo.setTypeAndData(Types.CHAR, GloabConstant.YESNO_YES);
		}
		
		sql.append("ORDER BY orderNo ASC ");
		return greaterDao.getRecords(sql.toString(),paramInfo,page,Greater.class);
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
		if(user != null)
		{
			if(!Utils.isEmpty(user.getNickname()))
			{
				sql.append(" AND T2.nickname like ? ");
				paramInfo.setTypeAndData(Types.VARCHAR, "%" + user.getNickname() + "%");
			}
		}
		return greaterDao.getCount(sql.toString(),paramInfo);
	}
	
	/**
	 * 根据大拿id获取大拿信息
	 * @param id
	 * @return
	 */
	public Greater getGreaterInfoById(String id)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT T1.id,T2.nickname,T2.phone,T1.company,T1.post,T2.url,T1.isshow,T1.orderNo,")
		   .append("T1.tags,T1.experience,T1.specialty,T1.job,T1.answerTimes,T1.backgroud_url ")
		   .append("FROM USER_GREATER T1 ")
		   .append("INNER JOIN USER_INFO T2 ON T2.ID=T1.ID ")
		   .append("WHERE T1.ID=? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.BIGINT, id);
		return greaterDao.getRecord(sql.toString(),paramInfo,Greater.class);
	}
	
	/**
	 * 新增大拿
	 * @param greater
	 */
	@Transactional
	public void insertGreater(User user,Greater greater)
	{
		long id = userDao.insertRecord(user, "USER_INFO");
		greater.setId(String.valueOf(id));
		greaterDao.insertRecord(greater, "USER_GREATER");
	}
	
	/**
	 * 更新大拿
	 * @param user
	 * @return
	 */
	@Transactional
	public void updateGreater(User user,Greater greater){
		updateUser(user);
		StringBuilder sql = new StringBuilder();
		sql.append("update USER_GREATER set ");
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
		
		if(!Utils.isEmpty(greater.getTags()))
		{
			sql.append("tags=?,");
			paramInfo.setTypeAndData(Types.VARCHAR, greater.getTags());
		}
		
		if(!Utils.isEmpty(greater.getExperience()))
		{
			sql.append("experience=?,");
			paramInfo.setTypeAndData(Types.VARCHAR, greater.getExperience());
		}
		
		if(!Utils.isEmpty(greater.getSpecialty()))
		{
			sql.append("specialty=?,");
			paramInfo.setTypeAndData(Types.CHAR, greater.getSpecialty());
		}
		
		if(!Utils.isEmpty(greater.getJob()))
		{
			sql.append("job=?,");
			paramInfo.setTypeAndData(Types.VARCHAR, greater.getJob());
		}
		
		if(!Utils.isEmpty(greater.getAnswerTimes()))
		{
			sql.append("answerTimes=?,");
			paramInfo.setTypeAndData(Types.BIGINT, greater.getAnswerTimes());
		}
		
		if(!Utils.isEmpty(greater.getIsshow()))
		{
			sql.append("isshow=?,");
			paramInfo.setTypeAndData(Types.CHAR, greater.getIsshow());
		}
		
		if(!Utils.isEmpty(greater.getOrderNo()))
		{
			sql.append("orderNo=?,");
			paramInfo.setTypeAndData(Types.INTEGER, greater.getOrderNo());
		}
		
		if(sql.toString().endsWith(","))
		{
			sql.replace(sql.length() - 1, sql.length(), "");
			sql.append(" where id=?");
			paramInfo.setTypeAndData(Types.BIGINT, greater.getId());
			
			userDao.updateRecord(sql.toString(), paramInfo);
		}
	}
	
	/**
	 * 上传大拿头像
	 * @param id
	 * @param url
	 */
	public void uploadGreaterImage(String id,String url,String uploadType)
	{
		StringBuilder sql = new StringBuilder();
		if("0".equals(uploadType))
		{
			sql.append("update USER_INFO set url =? WHERE id=? ");
		}else{
			sql.append("update USER_GREATER set backgroud_url =? WHERE id=? ");
		}
		
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.VARCHAR, url);
		paramInfo.setTypeAndData(Types.BIGINT, id);
		userDao.updateRecord(sql.toString(), paramInfo);
	}
	
	/**
	 * 删除大拿
	 * @param id
	 */
	@Transactional
	public void delGreater(String id)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("delete from USER_GREATER where id=? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.BIGINT, id);
		greaterDao.delRecord(sql.toString(), paramInfo);
		delUser(id);
	}
}
