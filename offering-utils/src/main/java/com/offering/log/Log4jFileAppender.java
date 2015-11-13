package com.offering.log;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

import com.offering.constant.GloabConstant;

/**
 * 
 * @author surfacepro3
 *
 */
public class Log4jFileAppender extends FileAppender{
	
	@Override
	public void append(LoggingEvent event) {
		if(GloabConstant.CURRENT_MODEL.equals(GloabConstant.MODEL_PRODUCT)){
			//生产环境
			if(event.getLevel().isGreaterOrEqual(Level.ERROR) ){
				super.append(event);
			}
		}else{
			super.append(event);
		}
	}
	
	@Override
	public void setFile(String file) {
		if(System.getProperty("os.name").toLowerCase().indexOf("windows") != -1)
			file = "C:\\Users\\surfacepro3\\Desktop\\offering.log";
		super.setFile(file);
	}
}
