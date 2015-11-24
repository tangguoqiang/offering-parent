package com.offering.core.service;

import java.util.List;

import com.offering.bean.sys.PageInfo;
import com.offering.bean.user.ConsultRecord;
import com.offering.bean.user.Greater;
import com.offering.bean.user.Topic;

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
	String askGreater(ConsultRecord cr,String title);
	
	/**
	 * 咨询历史纪录
	 * @param userId
	 * @param type
	 * @return
	 */
	List<ConsultRecord>  consultHistory(String userId,String type);
	
	/**
	 * 根据创建人和大拿获取资讯记录
	 * @param creater
	 * @param greaterId
	 * @return
	 */
	ConsultRecord getConsultByCreater(String creater,String greaterId);
	
	/**
	 * 根据话题id获取话题信息
	 * @param id
	 * @return
	 */
	Topic getTopicInfoById(String id);
}
