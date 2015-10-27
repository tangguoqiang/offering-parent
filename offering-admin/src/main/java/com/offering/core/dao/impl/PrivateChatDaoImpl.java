package com.offering.core.dao.impl;

import java.sql.Types;

import org.springframework.stereotype.Repository;

import com.offering.bean.ParamInfo;
import com.offering.bean.PrivateChat;
import com.offering.core.dao.PrivateChatDao;

/**
 * 私聊dao实现
 * @author surfacepro3
 *
 */
@Repository
public class PrivateChatDaoImpl extends BaseDaoImpl<PrivateChat> implements PrivateChatDao{

	/**
	 * 判断私聊是否已经存在,存在返回true
	 * @param sender
	 * @param receiver
	 * @return
	 */
	public boolean isExists(String sender,String receiver)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id FROM RC_PRIVATE ")
		   .append("WHERE sender=? AND receiver=? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.BIGINT, sender);
		paramInfo.setTypeAndData(Types.BIGINT, receiver);
		if(getCount(sql.toString(), paramInfo) > 0)
			return true;
		return false;
	}
}
