package com.offering.core.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.offering.bean.user.User;
import com.offering.constant.GloabConstant;
import com.offering.core.job.JobManager;
import com.offering.core.job.JobManager.JobType;
import com.offering.core.service.UserService;
import com.offering.log.LogUtil;
import com.offering.utils.MD5Util;

@Controller
public class MainController {
	
	@Autowired
	private UserService userService;

	/**
	 * 入口操作
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/",  method ={RequestMethod.POST,RequestMethod.GET})
	public String door(HttpSession session) {
		User user = (User)session.getAttribute("user");
		if(user != null){
			return "pages/index";
		}else{
			return "pages/login";
		}
	}
	
	/**
	 * 登陆操作
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(@RequestParam("username") String userName,
			@RequestParam("password")String password,HttpServletRequest req) {
		Map<String, Object> m = new HashMap<String, Object>();
		User user = userService.getUserInfoByNmae(userName,MD5Util.string2MD5(password));
		if(user != null){
			m.put("success", true);
		}else{
			m.put("success", false);
			m.put("msg", "用户名或密码错误！");
		}
		
		req.getSession().setAttribute("user", user);
		return m;
	}
	
	/**
	 * 注销操作
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> logout(HttpServletRequest req) {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("success", true);
		req.getSession().setAttribute("user",null);
		return m;
	}
	
	/**
	 * 新增任务
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addJob", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> addJob(@RequestParam("jobName") String jobName,
			@RequestParam("type")String type,@RequestParam("startTime")String startTime,HttpServletRequest req) {
		Map<String, Object> m = new HashMap<String, Object>();
		JobDataMap jobData = new JobDataMap();
		String key = null;
		String[] value = null;
		for(Entry<String, String[]> entry : req.getParameterMap().entrySet()){
			key = entry.getKey();
			value = entry.getValue();
			if(value != null && value.length > 0){
				jobData.put(key, value[0]);
			}
		}
		JobType jt = null;
		if(GloabConstant.JOB_TYPE_0.equals(type)){
			jt = JobType.CONSULT;
		}else{
			jt = JobType.ACTIVITY;
		}
		JobManager.addJob(jobName, jt, 
				new Date(Long.valueOf(startTime)),jobData);
		
		m.put("success", true);
		return m;
	}
	
	/**
	 * 文件下载
	 * @param path
	 * @param fileName
	 * @return
	 */
	@RequestMapping(value = "/download/{path}/{fileName}.{suff}",method={RequestMethod.GET,RequestMethod.POST})
	public void dowload(@PathVariable("path")String path, 
			@PathVariable("fileName")String fileName,@PathVariable("suff")String suff,
			HttpServletResponse rep){
		rep.setHeader("Content-Disposition", "attachment; filename=" + fileName + "." + suff);  
		LogUtil.debug(fileName + "." + suff);
		if("apk".equals(suff))
		{
//			rep.setHeader("Content-Encoding","gzip");
			rep.setContentType("application/octet-stream");
		}
		else
			rep.setContentType("image/*");  
		String filePath = GloabConstant.ROOT_DIR + path + "/" + fileName + "." + suff;
		long contentLength = new File(filePath).length();
		LogUtil.debug("文件大小:" + contentLength);
        rep.setContentLength((int) contentLength);
		FileInputStream fis = null; 
        OutputStream os = null; 
        try {
        	fis = new FileInputStream(filePath);
            os = rep.getOutputStream();
            int count = 0;
            byte[] buffer = new byte[1024*8];
            while ( (count = fis.read(buffer)) != -1 ){
              os.write(buffer, 0, count);
              os.flush();
            }
        }catch(Exception e){
        	e.printStackTrace();
        }finally {
            try {
				fis.close();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}
}
