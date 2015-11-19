package com.offering.core.dao;

import java.util.List;

import com.offering.bean.sys.PageInfo;
import com.offering.bean.user.Greater;

/**
 * 大拿dao
 * @author surfacepro3
 *
 */
public interface GreaterDao extends BaseDao<Greater>{

	/**
	 * 查询大拿列表(app端)
	 * @param page
	 * @return
	 */
	List<Greater> listGreaters_app(PageInfo page);
	
	/**
	 * 根据大拿id获取大拿信息(app端)
	 * @param id
	 * @return
	 */
	Greater getGreaterInfoById_app(String id);
}
