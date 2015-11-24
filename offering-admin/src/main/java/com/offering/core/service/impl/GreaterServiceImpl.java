package com.offering.core.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.offering.bean.sys.PageInfo;
import com.offering.bean.user.ConsultRecord;
import com.offering.bean.user.Greater;
import com.offering.bean.user.Topic;
import com.offering.constant.DBConstant;
import com.offering.constant.GloabConstant;
import com.offering.core.dao.ActivityDao;
import com.offering.core.dao.ConsultRecDao;
import com.offering.core.dao.GreaterDao;
import com.offering.core.dao.TopicDao;
import com.offering.core.job.JobManager;
import com.offering.core.job.JobManager.JobType;
import com.offering.core.service.GreaterService;
import com.offering.redis.RedisOp;
import com.offering.utils.RCUtils;
import com.offering.utils.Utils;

/**
 * 大拿service实现
 * @author surfacepro3
 *
 */
@Service
public class GreaterServiceImpl implements GreaterService{

	private final static Logger LOG = Logger.getLogger(GreaterServiceImpl.class);
	
	@Autowired
	private GreaterDao greaterDao;
	
	@Autowired
	private ActivityDao activityDao;
	
	@Autowired
	private TopicDao topicDao;
	
	@Autowired
	private ConsultRecDao crDao;
	
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
	
	/**
	 * 问大拿
	 * @param cr
	 * @param title
	 */
	@Transactional
	public String askGreater(ConsultRecord cr,String title){
		if(cr != null){
			StringBuilder groupId = new StringBuilder(64);
			groupId.append(cr.getGreaterId()).append("_")
			       .append(cr.getCreater());
			String groupName = "咨询大拿";
			if(!Utils.isEmpty(cr.getTopicId())){
				groupId.append("_").append(cr.getTopicId());
				groupName = title;
			}
			LOG.info("groupId:" + groupId);
			cr.setChatId(groupId.toString());
			cr.setTitle(groupName);
			long createTime = System.currentTimeMillis();
			cr.setCreateTime(String.valueOf(createTime));
			cr.setStatus(GloabConstant.CONSULT_STATUS_0);
			//如果存在相同的任务则不进行操作
			if(JobManager.checkExists(groupId.toString(), JobType.CONSULT))
				return groupId.toString();
			//插入咨询记录
			long crId = crDao.insertRecord(cr, DBConstant.CONSULT_RECORD);
			
			//创建群组
			List<String> userIds = new ArrayList<String>();
			userIds.add(cr.getCreater());
			userIds.add(cr.getGreaterId());
			RCUtils.createGroup(userIds, groupId.toString(), groupName);
			
			//新增定时任务
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(createTime);
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
//			cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) +1 );
			JobDataMap jobData = new JobDataMap();
			jobData.put("groupId", groupId.toString());
			jobData.put("userId", cr.getCreater());
			jobData.put("crId", crId);
			JobManager.addJob(groupId.toString(), JobType.CONSULT, cal.getTime(),jobData);
			return groupId.toString();
		}
		return null;
	}
	
	/**
	 * 咨询历史纪录
	 * @param userId
	 * @param type
	 * @return
	 */
	public List<ConsultRecord>  consultHistory(String userId,String type){
		List<ConsultRecord> crList = crDao.consultHistory(userId,type);
		if(crList != null && crList.size() > 0){
			String status = null;
			for(ConsultRecord cr : crList){
				status = cr.getStatus();
				if(GloabConstant.CONSULT_STATUS_0.equals(status))
					cr.setStatus("进行中");
				else if(GloabConstant.CONSULT_STATUS_1.equals(status))
					cr.setStatus("待评价");
				else if(GloabConstant.CONSULT_STATUS_2.equals(status))
					cr.setStatus("已评价");
			}
		}
		return crList;
	}
	
	/**
	 * 根据创建人和大拿获取资讯记录
	 * @param creater
	 * @param greaterId
	 * @return
	 */
	public ConsultRecord getConsultByCreater(String creater,String greaterId){
		return crDao.getConsultByCreater(creater,greaterId);
	}
	
	/**
	 * 根据话题id获取话题信息
	 * @param id
	 * @return
	 */
	public Topic getTopicInfoById(String id){
		return topicDao.getTopicInfoById(id);
	}
}
