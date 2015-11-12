package com.offering.job.impl;

import com.offering.job.BaseJob;

public class TestJob extends BaseJob{

	@Override
	public void doSomeThing() {
		System.out.println(TestJob.class);
		System.out.println(applicationContext.containsBean("schedulerFactory"));
	}

}
