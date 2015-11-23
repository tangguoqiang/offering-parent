package com.offering.core.service;

import java.util.List;

import com.offering.bean.sys.PageInfo;
import com.offering.bean.user.ConsultRecord;
import com.offering.bean.user.Greater;

/**
 * 大拿service
 * @author surfacepro3
 *
 */
public interface GreaterService {

	/**
	 * 获取大拿列表
	 * @param page
	 * @return
	 */
	List<Greater> listGreaters(PageInfo page,int v);
	
	/**
	 * 根据大拿id获取大拿信息
	 * @param id
	 * @param v
	 * @return
	 */
	Greater getGreaterInfoById(String id,int v);
	
	/**
	 * 问大拿
	 * @param cr
	 * @param title
	 */
	void askGreater(ConsultRecord cr,String title);
	
	/**
	 * 咨询历史纪录
	 * @param userId
	 * @param type
	 * @return
	 */
	List<ConsultRecord>  consultHistory(String userId,String type);
}
