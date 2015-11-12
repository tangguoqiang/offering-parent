package com.offering.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

public abstract class BaseJob implements Job{
	
	public ApplicationContext applicationContext; 

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		try {
			SchedulerContext schCtx = context.getScheduler().getContext();
			applicationContext = (ApplicationContext)schCtx.get("applicationContext");
			doSomeThing();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public abstract void doSomeThing();

}
