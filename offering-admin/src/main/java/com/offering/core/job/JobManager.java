package com.offering.core.job;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.web.context.WebApplicationContext;

import com.offering.bean.user.ConsultRecord;
import com.offering.core.dao.ConsultRecDao;
import com.offering.utils.Utils;

public final class JobManager {
	
	private final static Logger LOG = Logger.getLogger(JobManager.class);
	
	private final static String BASE_PACKAGE = "com.offering.core.job.impl.";
	
	public final static String GROUP_DEFAULT = "GROUP_OFFERING";
	/**
	 * 活动任务
	 */
	public final static String GROUP_ACTIVITY = "GROUP_ACTIVITY";
	/**
	 * 咨询任务class
	 */
	private final static String CLS_ACTIVITY = "ActivityJob";
	/**
	 * 咨询任务
	 */
	private final static String GROUP_CONSULT = "GROUP_CONSULT";
	
	/**
	 * 咨询任务class
	 */
	private final static String CLS_CONSULT = "ConsultJob";
	
	public enum JobType{
		CONSULT,ACTIVITY
	}

	private static Scheduler scheduler;
	
	public static void init(Scheduler scheduler,WebApplicationContext context){
		JobManager.scheduler = scheduler;
		initConsultJob(context);
		initActivityJob(context);
	}
	
	/**
	 * 初始化咨询任务
	 * @param context
	 */
	private static void initConsultJob(WebApplicationContext context){
		ConsultRecDao crDao = (ConsultRecDao)context.getBean("ConsultRecDao");
		List<ConsultRecord> crList = crDao.listRecs_in();
		if(crList != null && crList.size() > 0){
			for(ConsultRecord cr : crList){
				LOG.info("groupId:" + cr.getChatId());
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(Long.valueOf(cr.getCreateTime()));
				//TODO 应该设置成24后删除
				cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
//				cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) +1 );
				JobDataMap jobData = new JobDataMap();
				jobData.put("groupId", cr.getChatId());
				jobData.put("userId", cr.getCreater());
				jobData.put("crId", cr.getId());
				addJob(cr.getChatId(), JobType.CONSULT, cal.getTime(), jobData);
			}
		}
	}
	
	/**
	 * 初始化活动任务
	 * @param context
	 */
	private static void initActivityJob(WebApplicationContext context){
		
	}
	
	public static void destroy(){
		try {
			scheduler.shutdown();
		} catch (SchedulerException e) {
			LOG.error(e.toString());
		}
	}
	
	public static boolean checkExists(String jobName,JobType type){
		String jobGroup = GROUP_DEFAULT;
		switch (type) {
			case CONSULT:
				jobGroup = GROUP_CONSULT;
				break;
			case ACTIVITY:
				jobGroup = GROUP_ACTIVITY;
				break;
		}
		return checkExists(jobName, jobGroup);
	}
	
	public static boolean checkExists(String jobName,String jobGroup){
		try {
			return scheduler.checkExists(new JobKey(jobName,jobGroup));
		} catch (SchedulerException e) {
			LOG.error(e.toString());
		}
		return false;
	}
	
	/**
	 * 新增任务
	 * @param jobName
	 * @param jobCls
	 */
	public static void addJob(String jobName,String jobCls
			,JobDataMap jobData){
		addJob(jobName,GROUP_DEFAULT ,jobCls,null,jobData);
	}
	
	/**
	 * 新增任务
	 * @param jobName
	 * @param jobCls
	 * @param startTime
	 */
	public static void addJob(String jobName,String jobCls,Date startTime
			,JobDataMap jobData){
		addJob(jobName,GROUP_DEFAULT ,jobCls,startTime,jobData);
	}
	

	/**
	 * 新增任务
	 * @param jobName
	 * @param JobType
	 * @param startTime
	 */
	public static void addJob(String jobName,JobType type,
			Date startTime,JobDataMap jobData){
		switch (type) {
			case CONSULT:
				addJob(jobName,GROUP_CONSULT ,CLS_CONSULT,startTime,jobData);
				break;
			case ACTIVITY:
				addJob(jobName,GROUP_CONSULT ,CLS_ACTIVITY,startTime,jobData);
				break;
			default:
				break;
		}
		
	}

	/**
	 * 新增任务
	 * @param jobName
	 * @param jobGroup
	 * @param jobCls
	 * @param startTime
	 */
	@SuppressWarnings("unchecked")
	public static void addJob(String jobName,String jobGroup,String jobCls,
			Date startTime,JobDataMap jobData){
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
		JobBuilder jobBuilder = JobBuilder.newJob(cls);
		jobBuilder.withIdentity(jobName, jobGroup);
		if(jobData != null){
			jobBuilder.usingJobData(jobData);
		}
		JobDetail jobDetail = jobBuilder.build();
				
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
	public static void addIntervalJob(String jobName,String jobGroup,String jobCls,
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
				.withIdentity(jobName, Utils.isEmpty(jobGroup) ? GROUP_DEFAULT : jobGroup)
				.build();
		
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