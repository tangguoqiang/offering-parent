package com.offering.core.job;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

public abstract class BaseJob implements Job{
	

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			JobDataMap jobData = context.getJobDetail().getJobDataMap();
			SchedulerContext schCtx = context.getScheduler().getContext();
			ApplicationContext applicationContext = (ApplicationContext)schCtx.get("applicationContext");
			doSomeThing(jobData,applicationContext);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public abstract void doSomeThing(JobDataMap jobData,ApplicationContext applicationContext);

}
