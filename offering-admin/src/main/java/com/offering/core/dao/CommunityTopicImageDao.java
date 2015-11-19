package com.offering.core.dao;

import java.util.List;

import com.offering.bean.community.CommunityTopicImage;

/**
 * 话题附件dao
 * @author surfacepro3
 *
 */
public interface CommunityTopicImageDao extends BaseDao<CommunityTopicImage>{

	/**
	 * 批量插入附件
	 * @param topicId
	 * @param images
	 */
	void insertRecords(String topicId,String[] images);
	
	/**
	 * 根据话题id获取附件数据
	 * @param idList
	 * @return
	 */
	List<CommunityTopicImage> listImagesByTopicId(List<String> idList);
}
