package com.offering.core;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.offering.pay.PingPay;
import com.offering.utils.JpushUtils;


public class InitListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		JpushUtils.destroy();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
//		WebApplicationContext context = WebApplicationContextUtils
//				.getRequiredWebApplicationContext(arg0.getServletContext());
		JpushUtils.init();
		//ping++支付接口初始化
		PingPay.init();
	}
}
