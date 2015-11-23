package com.offering.core.job.impl;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.springframework.context.ApplicationContext;

import com.offering.constant.GloabConstant;
import com.offering.core.dao.ConsultRecDao;
import com.offering.core.job.BaseJob;
import com.offering.utils.RCUtils;

/**
 * 咨询话题job
 * @author surfacepro3
 *
 */
public final class ConsultJob extends BaseJob{
	
	private final static Logger LOG = Logger.getLogger(ConsultJob.class);

	@Override
	public void doSomeThing(JobDataMap jobData,
			ApplicationContext applicationContext) {
		LOG.info("任务开始执行");
		if(jobData != null && jobData.containsKey("groupId")
				&& jobData.containsKey("userId")&& jobData.containsKey("crId")){
			LOG.info(jobData.getString("userId") + "," +jobData.getString("groupId"));
			//解散群组
			RCUtils.dismissGroup(jobData.getString("userId"), 
					jobData.getString("groupId"));
			
			//更新记录状态为待评价
			ConsultRecDao crDao = (ConsultRecDao)applicationContext.getBean("ConsultRecDao");
			crDao.updateStatus(String.valueOf(jobData.get("crId")),GloabConstant.CONSULT_STATUS_1);
		}
		LOG.info("任务执行结束");
	}
}
