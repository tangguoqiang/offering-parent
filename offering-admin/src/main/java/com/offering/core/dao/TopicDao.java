package com.offering.core.dao;

import java.util.List;

import com.offering.bean.Topic;

/**
 * 话题dao
 * @author surfacepro3
 *
 */
public interface TopicDao extends BaseDao<Topic>{

	/**
	 * 根据大拿批量获取话题数据
	 * @param greaterIds
	 * @return
	 */
	List<Topic> listTopicsByGreaterId(List<String> greaterIds);
	
}
