package com.offering.core;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SecurityFilter  extends HttpServlet implements Filter{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3332634273317120921L;

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		 HttpServletRequest request=(HttpServletRequest)arg0;   
         HttpServletResponse response  =(HttpServletResponse) arg1;    
//         HttpSession session = request.getSession(true);     
//         String usercode = (String) request.getRemoteUser();// 登录人
//         String user_role = (String)session.getAttribute("role");//登录人角色
         String url=request.getRequestURI();   
//         if(usercode==null || "".equals(usercode) || user_role == null || "".equals(user_role)) {      
//              //判断获取的路径不为空且不是访问登录页面或执行登录操作时跳转   
//         && ( url.indexOf("Login")<0 && url.indexOf("login")<0 )
              if(url!=null && !url.equals("") &&(url.indexOf("login") >=0 ||url.indexOf("index")>=0 )) {   
                  response.sendRedirect(request.getContextPath() + "/");   
                  return ;   
              }              
//          }   
          arg2.doFilter(arg0, arg1);   
          return;   
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
