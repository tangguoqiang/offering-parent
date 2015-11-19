package com.offering.core.dao.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.offering.bean.community.CommunityTopicImage;
import com.offering.bean.sys.ParamInfo;
import com.offering.core.dao.CommunityTopicImageDao;

@Repository
public class CommunityTopicImageDaoImpl extends BaseDaoImpl<CommunityTopicImage> 
	implements CommunityTopicImageDao{

	/**
	 * 批量插入图片
	 */
	public void insertRecords(String topicId, String[] images) {
		if(images != null && images.length > 0){
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO COMMUNITY_TOPIC_IMAGE (topicId,url) VALUES (?,?)");
			
			List<ParamInfo> l = new ArrayList<ParamInfo>();
			ParamInfo param = null;
			for(String image : images){
				param = new ParamInfo();
				param.setTypeAndData(Types.BIGINT, topicId);
				param.setTypeAndData(Types.VARCHAR, image);
				l.add(param);
			}
			batchExcute(sql.toString(), l);
		}
	}
	
	/**
	 * 根据话题id获取附件数据
	 * @param idList
	 * @return
	 */
	public List<CommunityTopicImage> listImagesByTopicId(List<String> idList){
		if(idList!= null && idList.size() > 0){
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT topicId,url FROM COMMUNITY_TOPIC_IMAGE WHERE topicId in (");
			ParamInfo paramInfo = new ParamInfo();
			for(String id : idList){
				sql.append("?,");
				paramInfo.setTypeAndData(Types.BIGINT, id);
			}
			sql.replace(sql.length() - 1, sql.length(), ")");
			return getRecords(sql.toString(), paramInfo, CommunityTopicImage.class);
		}
		return null;
	}

}
