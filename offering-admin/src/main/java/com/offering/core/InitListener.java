package com.offering.core;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.Scheduler;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.offering.core.job.JobManager;
import com.offering.utils.JpushUtils;


public class InitListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		JpushUtils.destroy();
		JobManager.destroy();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		WebApplicationContext context = WebApplicationContextUtils
				.getRequiredWebApplicationContext(arg0.getServletContext());
		JpushUtils.init();
		JobManager.init((Scheduler)context.getBean("schedulerFactory"),context);
	}
}
