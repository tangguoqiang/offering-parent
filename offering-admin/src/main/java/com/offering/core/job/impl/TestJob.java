package com.offering.core.job.impl;

import org.quartz.JobDataMap;
import org.springframework.context.ApplicationContext;

import com.offering.core.job.BaseJob;

public final class TestJob extends BaseJob{

	@Override
	public void doSomeThing(JobDataMap jobData,
			ApplicationContext applicationContext) {
		System.out.println(TestJob.class);
		System.out.println(applicationContext.containsBean("schedulerFactory"));
	}

}
