package com.offering.core.dao;

import java.util.List;

import com.offering.bean.sys.PageInfo;
import com.offering.bean.user.User;

/**
 * 用户dao
 * @author surfacepro3
 *
 */
public interface UserDao extends BaseDao<User>{
	
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
	 * 判断用户名是否存在
	 * @param user
	 * @return
	 */
	public boolean isExistUser(User user);

}
