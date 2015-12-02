package com.offering.core.service;

import java.util.List;

import com.offering.bean.sys.PageInfo;
import com.offering.bean.user.User;

public interface UserService {

	/**
	 * 根据用户名获取用户信息
	 * @param name
	 * @param password
	 * @return
	 */
	User getUserInfoByNmae(String name,String password);
	
	/**
	 * 查询用户数据
	 * @param user
	 * @param page
	 * @return
	 */
	List<User> listUsers(User user,PageInfo page);
	
	/**
	 * 查询用户数据的数量
	 * @param user
	 * @return
	 */
	long getUserCount(User user);
	
	/**
	 * 根据用户id获取用户信息
	 * @param userId
	 * @return
	 */
	User getUserInfoById(String userId);
	
	
	/**
	 * 更新用户状态
	 * @param userId
	 * @param status
	 */
	void updateStatus(String userId,String status);
	
	/**
	 * 判断用户名是否存在
	 * @param user
	 * @return
	 */
	boolean isExistUser(User user);
}
