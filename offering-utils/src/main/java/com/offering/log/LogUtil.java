package com.offering.log;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * 日志共通类
 * @author surfacepro3
 *
 */
public class LogUtil {

	private final static Logger LOG = Logger.getLogger(LogUtil.class);
	
	private final static Level level = Logger.getRootLogger().getLevel();
	
	public static void info(String mess){
		if(level.isGreaterOrEqual(Level.INFO)){
			LOG.info(processMessage(mess));
		}
	}
	
	public static void debug(String mess){
		if(level.isGreaterOrEqual(Level.DEBUG)){
			LOG.debug(processMessage(mess));
		}
	}
	
	public static void error(String mess){
		if(level.isGreaterOrEqual(Level.ERROR)){
			LOG.error(processMessage(mess));
		}
	}
	
	private static  String processMessage(String mess) {
		StringBuilder sb = new StringBuilder(128);
		
		StackTraceElement stack[] = (new Throwable()).getStackTrace();   
		StackTraceElement ste=stack[2];   
		sb.append(ste.getFileName())
		  .append("(").append(ste.getMethodName()).append(" ")
		  .append(ste.getLineNumber()).append(") ")
		  .append(mess);
		return sb.toString();
	}
}
