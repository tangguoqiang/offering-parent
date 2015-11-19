package com.offering.core.service;

/**
 * 聊天service
 * @author surfacepro3
 *
 */
public interface ChartService {
	
	/**
	 * 创建私聊
	 * @param sender
	 * @param receiver
	 * @param type
	 * @param topicId
	 */
	void createPrivateChart(String sender,String receiver,String type,String topicId);

}
