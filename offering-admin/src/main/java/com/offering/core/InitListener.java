package com.offering.core;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.offering.utils.JpushUtils;


public class InitListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		JpushUtils.destroy();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		JpushUtils.init();
	}

}
