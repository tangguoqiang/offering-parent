package com.offering.job;

import java.util.Calendar;
import java.util.Date;

import org.quartz.Scheduler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	ApplicationContext context = new ClassPathXmlApplicationContext(
				"classpath:applicationContext.xml");
    	Scheduler scheduler = (Scheduler)context.getBean("schedulerFactory");
    	JobManager.init(scheduler);
    	Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
//		cal.set(Calendar.HOUR_OF_DAY, 24);
		cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) + 1);
		cal.set(Calendar.SECOND, 0);
    	JobManager.addJob("aaa","TestJob",cal.getTime());
    	
    	cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE) +1 );
    	JobManager.updateJobTime("aaa",cal.getTime());
    }
}
