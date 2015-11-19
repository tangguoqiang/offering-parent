package com.offering.core.dao.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.offering.bean.sys.ParamInfo;
import com.offering.bean.user.Topic;
import com.offering.core.dao.TopicDao;

/**
 * 话题dao实现
 * @author surfacepro3
 *
 */
@Repository
public class TopicDaoImpl extends BaseDaoImpl<Topic> implements TopicDao{
	
	/**
	 * 根据大拿批量获取话题数据
	 * @param greaterIds
	 * @return
	 */
	public List<Topic> listTopicsByGreaterId(List<String> greaterIds)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id,greaterId,title,content,askNums from (");
		sql.append("SELECT T1.id,T1.greaterId,T1.title,T1.content, ")
		   .append("(SELECT count(1) FROM TOPIC_MEMBER T WHERE T.topicId=T1.id) askNums ")
		   .append("FROM TOPIC_INFO T1 ")
		   .append("WHERE 1=1  ");
		ParamInfo paramInfo = new ParamInfo();
		if(greaterIds != null && greaterIds.size() > 0)
		{
			sql.append("AND greaterId in (");
			for(String id : greaterIds)
			{
				sql.append("?,");
				paramInfo.setTypeAndData(Types.BIGINT, id);
			}
			sql.replace(sql.length() - 1, sql.length(), ")");
		}
		sql.append(") TMP ORDER BY greaterId,askNums DESC ");
		
		return getRecords(sql.toString(),paramInfo,Topic.class);
	}

}
