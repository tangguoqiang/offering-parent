package com.offering.core.service.impl;

import java.sql.Types;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.offering.bean.ParamInfo;
import com.offering.bean.User;
import com.offering.core.dao.BaseDao;
import com.offering.core.service.MainService;

/**
 * 入口service的实现
 * @author gtang
 *
 */
@Service
public class MainServiceImpl implements MainService{

	@Autowired
	public BaseDao<User> mainDao;
	
	/**
	 * 根据用户名和密码获取用户信息
	 * @param username
	 * @param password
	 * @return
	 */
	public User getUserInfo(String username,String password){
		String sql = " select id,name,type,status from USER_INFO WHERE name=? AND password=?";
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.VARCHAR, username);
		paramInfo.setTypeAndData(Types.VARCHAR, password);
		return mainDao.getRecord(sql,paramInfo,User.class);
	}
	
	/**
	 * 修改密码
	 * @param username
	 * @param password
	 */
	public void resetPass(String username,String password){
		String sql = "update USER_INFO set password =? WHERE name=? ";
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.VARCHAR, password);
		paramInfo.setTypeAndData(Types.VARCHAR, username);
		mainDao.updateRecord(sql, paramInfo);
	}
}
