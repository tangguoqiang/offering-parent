package com.offering.core.dao;

import com.offering.bean.chart.PrivateChat;

/**
 * 私聊
 * @author surfacepro3
 *
 */
public interface PrivateChatDao extends BaseDao<PrivateChat>{

	/**
	 * 判断私聊是否已经存在,存在返回true
	 * @param sender
	 * @param receiver
	 * @return
	 */
	boolean isExists(String sender,String receiver);
}
