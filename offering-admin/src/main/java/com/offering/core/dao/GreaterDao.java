package com.offering.core.dao;

import java.util.List;

import com.offering.bean.sys.PageInfo;
import com.offering.bean.user.Greater;
import com.offering.bean.user.User;

/**
 * 大拿dao
 * @author surfacepro3
 *
 */
public interface GreaterDao extends BaseDao<Greater>{
	
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
	 * 更新大拿信息
	 * @param greater
	 */
	public void updateGreater(Greater greater);
}
