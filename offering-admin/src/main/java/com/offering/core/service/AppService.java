package com.offering.core.service;

import java.util.List;

import com.offering.bean.Greater;
import com.offering.bean.PageInfo;

public interface AppService {

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
	 * 创建私聊
	 * @param sender
	 * @param receiver
	 * @param type
	 * @param topicId
	 */
	void createPrivateChart(String sender,String receiver,String type,String topicId);
}
