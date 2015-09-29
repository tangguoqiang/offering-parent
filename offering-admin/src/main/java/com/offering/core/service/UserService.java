package com.offering.core.service;

import java.util.List;

import com.offering.bean.Greater;
import com.offering.bean.PageInfo;
import com.offering.bean.User;

public interface UserService {

	/**
	 * 根据手机号和密码获取用户信息
	 * @param phone
	 * @param password
	 * @return
	 */
	public User getUserInfoByPhone(String phone,String password);
	
	/**
	 * 根据用户名获取用户信息
	 * @param name
	 * @param password
	 * @return
	 */
	User getUserInfoByNmae(String name,String password);
	
	/**
	 * 登陆标识验证
	 * @param userId
	 * @param token
	 * @return
	 */
	public boolean checkToken(String userId,String token);
	
	/**
	 * 根据用户id获取用户信息
	 * @param userId
	 * @return
	 */
	public User getUserInfoById(String userId);
	
	/**
	 * 更新token
	 * @param userId
	 * @param token
	 */
	public void updateToken(String userId,String token);
	
	/**
	 * 更新融云token
	 * @param userId
	 * @param token
	 */
	public void updateRCToken(String userId,String token);
	
	/**
	 * 更新用户状态
	 * @param userId
	 * @param status
	 */
	public void updateStatus(String userId,String status);
	
	/**
	 * 查询用户数据
	 * @param user
	 * @param page
	 * @return
	 */
	public List<User> listUsers(User user,PageInfo page);
	
	/**
	 * 查询用户数据的数量
	 * @param user
	 * @return
	 */
	public long getUserCount(User user);
	
	/**
	 * 根据用户名查询用户数据
	 * @param userName
	 * @return
	 */
	public User getUserByName(String userName);
	
	/**
	 * 判断用户名是否存在
	 * @param user
	 * @return
	 */
	public boolean isExistUser(User user);
	
	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	public long insertUser(User user);
	
	/**
	 * 更新用户
	 * @param user
	 * @return
	 */
	public void updateUser(User user);
	
	/**
	 * 删除用户
	 * @param id
	 */
	public void delUser(String id);
	
	/**
	 * 更新密码
	 * @param id
	 * @param password
	 */
	public void updatePassword(String id,String password);
	
	/**
	 * 第三方登陆获取用户信息
	 * @param openId
	 * @param loginType
	 * @return
	 */
	public User getUserInfoByOpenId(String openId,String loginType);
	
	/**
	 * 查询大拿列表
	 * @param page
	 * @return
	 */
	List<Greater> listGreaters(User user,PageInfo page);
	
	/**
	 * 获取大拿数量
	 * @param user
	 * @return
	 */
	long getGreaterCount(User user);
	
	/**
	 * 根据大拿id获取大拿信息
	 * @param id
	 * @return
	 */
	Greater getGreaterInfoById(String id);
	
	/**
	 * 新增大拿
	 * @param greater
	 */
	void insertGreater(User user,Greater greater);
	
	/**
	 * 更新大拿
	 * @param greater
	 */
	void updateGreater(User user,Greater greater);
	
	/**
	 * 上传大拿头像
	 * @param id
	 * @param url
	 */
	void uploadGreaterImage(String id,String url,String uploadType);
	
	/**
	 * 删除大拿
	 * @param id
	 */
	void delGreater(String id);
}
