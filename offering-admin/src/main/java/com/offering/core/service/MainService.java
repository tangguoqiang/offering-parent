package com.offering.core.service;

import com.offering.bean.User;

/**
 * 入口service
 * @author gtang
 *
 */
public interface MainService {

	/**
	 * 根据用户名和密码获取用户信息
	 * @param username
	 * @param password
	 * @return
	 */
	public User getUserInfo(String username,String password);
	
	/**
	 * 修改密码
	 * @param username
	 * @param password
	 */
	public void resetPass(String username,String password);
}
