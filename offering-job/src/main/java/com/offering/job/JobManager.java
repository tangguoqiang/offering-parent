package com.offering.job;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import com.offering.utils.Utils;

public class JobManager {
	
	private final static Logger LOG = Logger.getLogger(JobManager.class);
	
	private final static String BASE_PACKAGE = "com.offering.job.impl.";
	
	private final static String GROUP_DEFAULT = "GROUP_OFFERING";
	private final static String GROUP_INTERVAL = "GROUP_INTERVAL";

	private static Scheduler scheduler;
	
	public static void init(Scheduler scheduler){
		JobManager.scheduler = scheduler;
	}
	
	public static void destroy(){
		try {
			scheduler.shutdown();
		} catch (SchedulerException e) {
			LOG.error(e.toString());
		}
	}
	
	/**
	 * 新增任务
	 * @param jobName
	 * @param jobCls
	 */
	public static void addJob(String jobName,String jobCls){
		addJob(jobName,GROUP_DEFAULT ,jobCls,null);
	}
	
	/**
	 * 新增任务
	 * @param jobName
	 * @param jobCls
	 * @param startTime
	 */
	public static void addJob(String jobName,String jobCls,Date startTime){
		addJob(jobName,GROUP_DEFAULT ,jobCls,startTime);
	}

	/**
	 * 新增任务
	 * @param jobName
	 * @param jobGroup
	 * @param jobCls
	 * @param startTime
	 */
	@SuppressWarnings("unchecked")
	public static void addJob(String jobName,String jobGroup,String jobCls,Date startTime){
		if(Utils.isEmpty(jobCls)){
			LOG.error("jobCls不能为空");
			return;
		}
			
		Class<BaseJob> cls = null;
		try {
			cls = (Class<BaseJob>)Class.forName(BASE_PACKAGE + jobCls);
		} catch (ClassNotFoundException e) {
			LOG.error(e.toString());
			return;
		}
		JobDetail jobDetail = JobBuilder.newJob(cls)
				.withIdentity(jobName, jobGroup).build();
		
		TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
		triggerBuilder.withIdentity(jobName, jobGroup);
		triggerBuilder.forJob(jobDetail);
		if(startTime != null){
			triggerBuilder.startAt(startTime);
		}else{
			triggerBuilder.startNow();
		}
    	
		try {
			scheduler.scheduleJob(jobDetail,triggerBuilder.build());
		} catch (SchedulerException e) {
			LOG.error(e.toString());
		}
	}
	
	/**
	 * 新增定时任务
	 * @param jobName
	 * @param jobGroup
	 * @param jobCls
	 */
	@SuppressWarnings("unchecked")
	public static void addIntervalJob(String jobName,String jobCls,
			int intervalSeconds,int repeatCount){
		if(Utils.isEmpty(jobCls)){
			LOG.error("jobCls不能为空");
			return;
		}
			
		Class<BaseJob> cls = null;
		try {
			cls = (Class<BaseJob>)Class.forName(BASE_PACKAGE + jobCls);
		} catch (ClassNotFoundException e) {
			LOG.error(e.toString());
			return;
		}
		JobDetail jobDetail = JobBuilder.newJob(cls)
				.withIdentity(jobName, GROUP_INTERVAL).build();
		
		Trigger trigger = TriggerBuilder.newTrigger()
				.startNow()
				.forJob(jobDetail)
				.withSchedule(SimpleScheduleBuilder.simpleSchedule()
						.withIntervalInSeconds(intervalSeconds)
						.withRepeatCount(repeatCount))
				.build();
		try {
			scheduler.scheduleJob(jobDetail,trigger);
		} catch (SchedulerException e) {
			LOG.error(e.toString());
		}
	}
	
	/**
	 * 更新任务开始时间
	 * @param jobName
	 * @param startTime
	 */
	public static void updateJobTime(String jobName,Date startTime){
		updateJobTime(jobName, GROUP_DEFAULT,startTime);
	}
	
	/**
	 * 更新任务开始时间
	 * @param jobName
	 * @param jobGroup
	 * @param startTime
	 */
	public static void updateJobTime(String jobName,String jobGroup,Date startTime){
		TriggerKey triggerKey = new TriggerKey(jobName, jobGroup);
		try {
			Trigger trigger = scheduler.getTrigger(triggerKey);
			trigger = trigger.getTriggerBuilder().startAt(startTime).build();
			scheduler.rescheduleJob(triggerKey, trigger);
		} catch (SchedulerException e) {
			LOG.error(e.toString());
		}
	}
}