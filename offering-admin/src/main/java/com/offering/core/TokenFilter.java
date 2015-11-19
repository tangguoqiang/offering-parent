package com.offering.core;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * token校验filter
 * @author surfacepro3
 *
 */
public class TokenFilter implements Filter{
	
	private final static Logger LOG = Logger.getLogger(TokenFilter.class);

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse rep,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)req;   
//        HttpServletResponse response  =(HttpServletResponse) rep;    
        String userId = request.getParameter("userId");
        String url=request.getRequestURI();   
        if(url.matches("^/.+/v\\d+/.+$"))
        {
        	url = url.substring(url.lastIndexOf("/") + 1);
        }
        
        chain.doFilter(req, rep);   
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}


}
