package com.offering.core.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.offering.bean.sys.PageInfo;
import com.offering.bean.user.Greater;
import com.offering.bean.user.Topic;
import com.offering.core.dao.ActivityDao;
import com.offering.core.dao.GreaterDao;
import com.offering.core.dao.TopicDao;
import com.offering.core.service.GreaterService;
import com.offering.redis.RedisOp;
import com.offering.utils.Utils;

/**
 * 大拿service实现
 * @author surfacepro3
 *
 */
@Service
public class GreaterServiceImpl implements GreaterService{

	@Autowired
	private GreaterDao greaterDao;
	
	@Autowired
	private ActivityDao activityDao;
	
	@Autowired
	private TopicDao topicDao;
	
	
	@Autowired
	private RedisOp redisOp;
	
	/**
	 * 获取大拿列表
	 * @param page
	 * @return
	 */
	public List<Greater> listGreaters(PageInfo page,int v)
	{
		List<Greater> greaterList = greaterDao.listGreaters_app(page);
		if(greaterList != null && greaterList.size() > 0)
		{
			List<String> idList = new ArrayList<String>();
			String tags = "";
			for(Greater greater : greaterList){
				tags = "";
				if(!Utils.isEmpty(greater.getSchoolName()))
					tags = tags + greater.getSchoolName() + ",";
				if(!Utils.isEmpty(greater.getCompany()))
					tags = tags + greater.getCompany() + ",";
				if(!Utils.isEmpty(greater.getIndustryName()))
					tags = tags + greater.getIndustryName() + ",";
				if(!Utils.isEmpty(greater.getWorkYears())){
					if(Integer.valueOf(greater.getWorkYears()) <= 5)
						tags = tags + greater.getWorkYears() + "年工作经验,";
					else
						tags = tags + "五年以上工作经验,";
				}
				
				greater.setTags(tags + greater.getTags());
					
				idList.add(greater.getId());
			}
			
			List<Topic> topicList = topicDao.listTopicsByGreaterId(idList);
			Map<String, Topic> topicMap = new HashMap<String, Topic>();
			String id = null;
			if(topicList != null && topicList.size() > 0)
			{
				for(Topic topic : topicList)
				{
					id = topic.getGreaterId();
					if(topicMap.containsKey(id))
						continue;
					topicMap.put(id, topic);
				}
			}
			for(Greater greater : greaterList)
				greater.setTopic(topicMap.get(greater.getId()));
		}
		return greaterList;
	}
	
	/**
	 * 根据大拿id获取大拿信息
	 * @param id
	 * @param v
	 * @return
	 */
	public Greater getGreaterInfoById(String id,int v)
	{
		Greater greater = greaterDao.getGreaterInfoById_app(id);
		if(greater == null)
			return null;
		List<String> idList = new ArrayList<String>();
		idList.add(id);
		greater.setTopics(topicDao.listTopicsByGreaterId(idList));
		greater.setActivities(activityDao.listActivitysByGreaterId(id));
		
		String tags = "";
		if(!Utils.isEmpty(greater.getSchoolName()))
			tags = tags + greater.getSchoolName() + ",";
		if(!Utils.isEmpty(greater.getCompany()))
			tags = tags + greater.getCompany() + ",";
		if(!Utils.isEmpty(greater.getIndustryName()))
			tags = tags + greater.getIndustryName() + ",";
		if(!Utils.isEmpty(greater.getWorkYears())){
			if(Integer.valueOf(greater.getWorkYears()) <= 5)
				tags = tags + greater.getWorkYears() + "年工作经验,";
			else
				tags = tags + "五年以上工作经验,";
		}
		
		greater.setTags(tags + greater.getTags());
		return greater;
	}
}
