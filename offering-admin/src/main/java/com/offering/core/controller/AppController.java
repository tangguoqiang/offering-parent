package com.offering.core.controller;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.offering.bean.Activity;
import com.offering.bean.AppVersion;
import com.offering.bean.ChartGroup;
import com.offering.bean.Comcode;
import com.offering.bean.Greater;
import com.offering.bean.IDCode;
import com.offering.bean.Member;
import com.offering.bean.Message;
import com.offering.bean.PageInfo;
import com.offering.bean.School;
import com.offering.bean.Speaker;
import com.offering.bean.Suggest;
import com.offering.bean.User;
import com.offering.constant.GloabConstant;
import com.offering.core.service.ActivityService;
import com.offering.core.service.SystemService;
import com.offering.core.service.UserService;
import com.offering.redis.RedisOp;
import com.offering.utils.CCPUtils;
import com.offering.utils.RCUtils;
import com.offering.utils.Utils;

/**
 * 第三方APP接口
 */
@Controller
@RequestMapping(value="/app")
public class AppController {
	
	private final static Logger LOG = Logger.getLogger(AppController.class);
	
	/**
	 * 验证码过期时间
	 */
	private final static long IDCODE_EXPIRETIME = 2 * 60 * 1000; 
	private final static String IDCODE_PREF = "idcode_";
	
//	private Map<String, String> idCodeMap = new HashMap<String, String>();
//	private Map<String, Long> timeMap = new HashMap<String, Long>();
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SystemService sysService;
	
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private RedisOp redisOp;
	
	
	/**
	 * 获取融云token
	 * @param userId
	 * @param token
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/getRCToken",method={RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> getRCToken(String userId,String token,
			HttpServletRequest req) {
		Map<String, Object> m = Utils.checkParam(req, new String[]{"userId","token"});
		if(m != null)
			return m;
		if(userService.checkToken(userId,token)){
			Map<String, Object> dataMap = new HashMap<String, Object>();
			User user = userService.getUserInfoById(userId);
			if(user != null)
			{
				if(Utils.isEmpty(user.getRc_token()))
				{
					String rc_token = RCUtils.getToken(userId, user.getNickname(), "");
					dataMap.put("rc_token", rc_token);
					userService.updateRCToken(userId, rc_token);
				}else{
					dataMap.put("rc_token", user.getRc_token());
				}
				
			}
			return Utils.success(dataMap);
		}else{
			return Utils.failture("登陆失效，请重新登陆！");
		}
	}
	
	/**
	 * 刷新融云信息
	 * @param userId
	 * @param token
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/refreshRCInfo",method={RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> refreshRCInfo(String userId,String token,
			HttpServletRequest req) {
		Map<String, Object> m = Utils.checkParam(req, new String[]{"userId","token"});
		if(m != null)
			return m;
		if(userService.checkToken(userId,token)){
			Map<String, Object> dataMap = new HashMap<String, Object>();
			User user = userService.getUserInfoById(userId);
			if(user != null)
			{
				String url = null;
				if(!Utils.isEmpty(user.getUrl()))
					url = "http://" + req.getServerName() + ":" + req.getServerPort()  
							+ req.getContextPath() + user.getUrl();

				RCUtils.refreshUser(userId, user.getNickname(), url);
			}
			return Utils.success(dataMap);
		}else{
			return Utils.failture("登陆失效，请重新登陆！");
		}
	}
	 		
	/**
	 * 获取验证码
	 * @param phone
	 * @return
	 */
	@RequestMapping(value = "/getCode",method={RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> getCode(String phone,String type,HttpServletRequest req) {
		Map<String, Object> m = Utils.checkParam(req, new String[]{"phone"});
		if(m != null)
			return m;
		
		
		User user = new User();
		user.setPhone(phone);
		boolean isExists = userService.isExistUser(user);
		if(GloabConstant.CODE_REG.equals(type) && isExists)
		{
			return Utils.failture("该号码已经注册！");
		}
		
		if(GloabConstant.CODE_FINDPASS.equals(type) && !isExists)
		{
			return Utils.failture("该号码还未注册！");
		}
		
		IDCode oldCode = redisOp.get(IDCODE_PREF + phone, IDCode.class);
		if(oldCode != null)
		{
			if(System.currentTimeMillis() - Long.valueOf(oldCode.getCreateTime()) <= 60000)
			{
				return Utils.failture("一分钟内只能申请一次验证码!");
			}
		}
		String idCode = Utils.createIdCode();
		String msg = CCPUtils.sendSMS(phone, idCode);
		if(Utils.isEmpty(msg))
		{
			IDCode newCode = new IDCode();
			newCode.setCode(idCode);
			newCode.setCreateTime(System.currentTimeMillis() + "");
			redisOp.set(IDCODE_PREF + phone, newCode , IDCode.class,IDCODE_EXPIRETIME);
			return Utils.success(null);
		}else
			return Utils.failture(msg);
	}
	
	/**
	 * 注册操作
	 * @param user
	 * @param code
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/register",method={RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> register(User user,String code,HttpServletRequest req) {
		Map<String, Object> m = Utils.checkParam(req, new String[]{"phone","code","password"});
		if(m != null)
			return m;
		
		user.setId(null);
		String phone = user.getPhone();
		IDCode idcode = redisOp.get(IDCODE_PREF + phone, IDCode.class);
		if(idcode == null || !code.equals(idcode.getCode()))
			return Utils.failture("验证码错误，验证失败！");
		
		if(userService.isExistUser(user))
		{
			return Utils.failture("该号码已经注册！");
		}else{
			String token = Utils.getUUID();
			user.setToken(token);
			user.setType(GloabConstant.USER_TYPE_NORMAL);
			user.setLogin_type(GloabConstant.LOGIN_TYPE_PHONE);
			long userId = userService.insertUser(user);
			
			redisOp.delete(IDCODE_PREF + phone);
			
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("userId", userId + "");
			dataMap.put("token", token);
			dataMap.put("type", GloabConstant.USER_TYPE_NORMAL);
			return Utils.success(dataMap);
		}
	}
	
	/**
	 * 找回密码
	 * @param phone
	 * @param code
	 * @param password
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/findPassword",method={RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> findPassword(String phone,String code,
			String password,HttpServletRequest req) {
		Map<String, Object> m = Utils.checkParam(req, new String[]{"phone","code","password"});
		if(m != null)
			return m;
		
		m = new HashMap<String, Object>();
		IDCode idcode = redisOp.get(IDCODE_PREF + phone, IDCode.class);
		if(idcode == null || !code.equals(idcode.getCode()))
			return Utils.failture("验证码错误，验证失败！");
		
		User user = userService.getUserInfoByPhone(phone, null);
		if(user == null)
		{
			return Utils.failture("该号码还未注册！");
		}else{
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("userId", user.getId());
			dataMap.put("token", user.getToken());
			dataMap.put("type", user.getType());
			
			//更新密码
			userService.updatePassword(user.getId(),password);
			
			redisOp.delete(IDCODE_PREF + phone);
			return Utils.success(dataMap);
		}
	}
	
	/**
	 * 登陆操作
	 * @param phone
	 * @param password
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/login",method={RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> login(String phone,String password,HttpServletRequest req) {
		Map<String, Object> m = Utils.checkParam(req, new String[]{"phone","password"});
		if(m != null)
			return m;
		
		User user = userService.getUserInfoByPhone(phone,null);
		if(user != null){
			if(!password.equals(user.getPassword()))
				return Utils.failture("密码错误！");
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("userId", user.getId());
			dataMap.put("type", user.getType());
			String token = user.getToken();
			if(Utils.isEmpty(token))
			{
				token = Utils.getUUID();
				userService.updateToken(user.getId(),token);
			}
			dataMap.put("token", token);
			dataMap.put("nickname", user.getNickname());
			dataMap.put("url", user.getUrl());
			return Utils.success(dataMap);
		}else{
			return Utils.failture("该手机号还未注册！");
		}
	}
	
	/**
	 * 第三方登陆操作
	 * @param login_type
	 * @param openId
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/third_platform_login",method={RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> third_platform_login(String login_type,String openId,
			HttpServletRequest req) {
		Map<String, Object> m = Utils.checkParam(req, new String[]{"login_type","openId"});
		if(m != null)
			return m;
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		User user = userService.getUserInfoByOpenId(openId,login_type);
		if(user != null){
			dataMap.put("userId", user.getId());
			dataMap.put("type", user.getType());
			String token = user.getToken();
			if(Utils.isEmpty(token))
			{
				token = Utils.getUUID();
				userService.updateToken(user.getId(),token);
			}
			dataMap.put("token", token);
			dataMap.put("isExists", "0");
			dataMap.put("nickname", user.getNickname());
			dataMap.put("url", user.getUrl());
		}else{
			user = new User();
			user.setOpenId(openId);
			user.setLogin_type(login_type);
			String token = Utils.getUUID();
			user.setToken(token);
			user.setType(GloabConstant.USER_TYPE_NORMAL);
			long userId = userService.insertUser(user);
			
			dataMap.put("userId", userId + "");
			dataMap.put("token", token);
			dataMap.put("type", GloabConstant.USER_TYPE_NORMAL);
			//用户新注册
			dataMap.put("isExists", "1");
			dataMap.put("nickname", "");
			dataMap.put("url", "");
		}
		return Utils.success(dataMap);
	}
	
	/**
	 * 找回密码
	 * @param phone
	 * @param code
	 * @param password
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/resetPassword",method={RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> resetPassword(String userId,String oldPw,
			String newPw,HttpServletRequest req) {
		Map<String, Object> m = Utils.checkParam(req, new String[]{"userId","oldPw","newPw"});
		if(m != null)
			return m;
		
		User user = userService.getUserInfoById(userId);
		if(user == null)
		{
			return Utils.failture("该用户不存在！");
		}else{
			if(!oldPw.equals(user.getPassword()))
				return Utils.failture("原密码错误！");
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("userId", user.getId());
			dataMap.put("token", user.getToken());
			
			//更新密码
			userService.updatePassword(userId,newPw);
			
			return Utils.success(dataMap);
		}
	}
	
	/**
	 * 获取用户信息
	 * @param userId
	 * @param token
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/getUserInfo",method={RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> getUserInfo(String userId,String token
			,HttpServletRequest req) {
		Map<String, Object> m = Utils.checkParam(req, new String[]{"userId","token"});
		if(m != null)
			return m;
		if(userService.checkToken(userId,token)){
			User user = userService.getUserInfoById(userId);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("nickname", user.getNickname());
//			dataMap.put("name", user.getName());
			dataMap.put("school", user.getSchoolId());
			dataMap.put("schoolName", user.getSchoolName());
			dataMap.put("major", user.getMajor());
			dataMap.put("grade", user.getGrade());
			dataMap.put("gradeName", user.getGradeName());
			dataMap.put("industry", user.getIndustry());
			dataMap.put("url", user.getUrl());
			dataMap.put("background_url", user.getBackground_url());
			dataMap.put("type", user.getType());
			dataMap.put("joinActivityNum", activityService.getJoinActivityNum(userId) + "");
			dataMap.put("askGreaterNum", activityService.getAskGreaterNum(userId) + "");
			return Utils.success(dataMap);
		}else{
			return Utils.failture("登陆失效，请重新登陆！");
		}
	}
	
	/**
	 * 更新用户信息
	 * @param userId
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/updateUserInfo")
	@ResponseBody
	public Map<String, Object> updateUserInfo(HttpServletRequest req) {
        DiskFileItemFactory factory = new DiskFileItemFactory();  
//        String tmpPath = req.getSession().getServletContext().getRealPath("/upload");  
        //设置暂时存放文件的存储室，这个存储室可以和最终存储文件的文件夹不同。因为当文件很大的话会占用过多内存所以设置存储室。 
        factory.setRepository(new File(GloabConstant.ROOT_DIR+ "tmp"));  
        //设置缓存的大小，当上传文件的容量超过缓存时，就放到暂时存储室。  
        factory.setSizeThreshold(1024*1024);  
        //上传处理工具类（高水平API上传处理？）  
        ServletFileUpload upload = new ServletFileUpload(factory);  
	    upload.setSizeMax(10*1024*1024);   // 设置允许用户上传文件大小,单位:字节 10M
	    Map<String, String> paramMap = new HashMap<String, String>();
	    InputStream in = null;
	    String filename = null;
	    try{
	    	List<FileItem> list = (List<FileItem>)upload.parseRequest(req);  
	    	for(FileItem item:list){  
	    		String name = item.getFieldName();  
	    		if(item.isFormField()){  
	    			//如果获取的表单信息是普通的文本信息。即通过页面表单形式传递来的字符串。  
	    			String value = new String(item.getString().getBytes("iso8859-1"));  
	    			paramMap.put(name, value);
	    		}else{   
	            	//如果传入的是非简单字符串，而是图片，音频，视频等二进制文件。  
	                String value = item.getName();  
	                filename = value.substring(value.lastIndexOf("\\") + 1);  
	                filename = System.currentTimeMillis() + filename.substring(filename.indexOf("."));
	                LOG.info("获取文件总量的容量:"+ item.getSize());  
	                if(item.getSize() != 0)
	                	in = item.getInputStream();  
	    		}  
	    	}  
	    }catch(Exception e){  
	        e.printStackTrace();  
	    }  
	    
	    OutputStream out = null;
		String userId = paramMap.get("userId");
		String token = paramMap.get("token");
		User user = new User();
		user.setId(userId);
		if(userService.checkToken(userId,token)){
			if(in != null)
			{
				try{
					out = new FileOutputStream(
		            		new File(GloabConstant.ROOT_DIR + "userImages",filename));
					int length = 0;  
		            byte[] buf = new byte[1024];  
		            while((length = in.read(buf))!=-1){  
		                out.write(buf,0,length);  
		            }  
				}catch(Exception e){
					return Utils.failture("文件上传错误！");
				}finally{
					try {
						if(in != null)
							in.close();
						if(out != null)
							out.close();  
					} catch (IOException e) {
						e.printStackTrace();
					}  
				}
			}
            
			user.setNickname(paramMap.get("nickname"));
			user.setSchoolId(paramMap.get("schoolId"));
			user.setMajor(paramMap.get("major"));
			user.setGrade(paramMap.get("grade"));
			user.setIndustry(paramMap.get("industry"));
			user.setUrl("/download/userImages/" + filename);
			userService.updateUser(user);
			
			user = userService.getUserInfoById(userId);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("nickname", user.getNickname());
//			dataMap.put("name", user.getName());
			dataMap.put("school", user.getSchoolId());
			dataMap.put("schoolName", user.getSchoolName());
			dataMap.put("major", user.getMajor());
			dataMap.put("grade", user.getGrade());
			dataMap.put("gradeName", user.getGradeName());
			dataMap.put("industry", user.getIndustry());
			dataMap.put("url", user.getUrl());
			return Utils.success(dataMap);
		}else{
			return Utils.failture("登陆失效，请重新登陆！");
		}
	}
	
	/**
	 * 更新用户信息_不上传图片
	 * @param userId
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/updateUserInfo_new",method={RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> updateUserInfo_new(String userId,User user) {
		String token = user.getToken();
		user.setId(userId);
		try {
			if(!Utils.isEmpty(user.getNickname()))
			{
				user.setNickname(new String(user.getNickname().getBytes("iso8859-1")));
			}
			if(!Utils.isEmpty(user.getMajor()))
			{
				user.setMajor(new String(user.getMajor().getBytes("iso8859-1")));
			}
		} catch (UnsupportedEncodingException e) {
			LOG.error(e);
		}
		if(userService.checkToken(userId,token)){
			userService.updateUser(user);
			user = userService.getUserInfoById(userId);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("nickname", user.getNickname());
			dataMap.put("school", user.getSchoolId());
			dataMap.put("schoolName", user.getSchoolName());
			dataMap.put("major", user.getMajor());
			dataMap.put("grade", user.getGrade());
			dataMap.put("gradeName", user.getGradeName());
			dataMap.put("industry", user.getIndustry());
			dataMap.put("url", user.getUrl());
			return Utils.success(dataMap);
		}else{
			return Utils.failture("登陆失效，请重新登陆！");
		}
	}
	
	/**
	 * 上传用户背景图片
	 * @param userId
	 * @param user
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/uploadBackgroundImage")
	@ResponseBody
	public Map<String, Object> uploadBackgroundImage(HttpServletRequest req) {
        DiskFileItemFactory factory = new DiskFileItemFactory();  
        factory.setRepository(new File(GloabConstant.ROOT_DIR+ "tmp"));  
        //设置缓存的大小，当上传文件的容量超过缓存时，就放到暂时存储室。  
        factory.setSizeThreshold(1024*1024);  
        //上传处理工具类（高水平API上传处理？）  
        ServletFileUpload upload = new ServletFileUpload(factory);  
	    upload.setSizeMax(10*1024*1024);   // 设置允许用户上传文件大小,单位:字节 10M
	    Map<String, String> paramMap = new HashMap<String, String>();
	    InputStream in = null;
	    String filename = null;
	    try{
	    	List<FileItem> list = (List<FileItem>)upload.parseRequest(req);  
	    	for(FileItem item:list){  
	    		String name = item.getFieldName();  
	    		if(item.isFormField()){  
	    			//如果获取的表单信息是普通的文本信息。即通过页面表单形式传递来的字符串。  
	    			String value = new String(item.getString().getBytes("iso8859-1"));  
	    			paramMap.put(name, value);
	    		}else{   
	            	//如果传入的是非简单字符串，而是图片，音频，视频等二进制文件。  
	                String value = item.getName();  
	                filename = value.substring(value.lastIndexOf("\\") + 1);  
	                filename = System.currentTimeMillis() + filename.substring(filename.lastIndexOf("."));
	                LOG.info("获取文件总量的容量:"+ item.getSize());  
	                in = item.getInputStream();  
	    		}  
	    	}  
	    }catch(Exception e){  
	        e.printStackTrace();  
	    }  
	    
	    OutputStream out = null;
		String userId = paramMap.get("userId");
		String token = paramMap.get("token");
		User user = new User();
		user.setId(userId);
		if(userService.checkToken(userId,token)){
			try{
				out = new FileOutputStream(
	            		new File(GloabConstant.ROOT_DIR + "userImages",filename));
				int length = 0;  
	            byte[] buf = new byte[1024];  
	            while((length = in.read(buf))!=-1){  
	                out.write(buf,0,length);  
	            }  
			}catch(Exception e){
				return Utils.success(null);
			}finally{
				try {
					if(in != null)
						in.close();
					if(out != null)
						out.close();  
				} catch (IOException e) {
					e.printStackTrace();
				}  
			}
            
			user.setBackground_url("/download/userImages/" + filename);
			userService.updateUser(user);
			
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("background_url", "/download/userImages/" + filename);
			return Utils.success(dataMap);
		}else{
			return Utils.failture("登陆失效，请重新登陆！");
		}
	}
	
	/**
	 * 获取学校信息
	 * @param userId
	 * @param token
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/listSchools",method={RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> listSchools(String userId,String token,String province
			,HttpServletRequest req) {
		Map<String, Object> m = Utils.checkParam(req, new String[]{"userId","token"});
		if(m != null)
			return m;
		if(userService.checkToken(userId,token)){
			List<School> schools = sysService.listSchools(province);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			if(schools != null)
			{
				Map<String, List<Map<String, String>>> provinceMap = new HashMap<String, List<Map<String, String>>>();
				List<Map<String, String>> l = null;
				String key = null;
				Map<String, String> schoolMap = new HashMap<String, String>();
				for(School school : schools)
				{
					key = school.getProvince();
					l = provinceMap.get(key);
					if(l == null)
						l = new ArrayList<Map<String, String>>();
					schoolMap = new HashMap<String, String>();
					schoolMap.put("id", school.getId());
					schoolMap.put("name", school.getName());
					l.add(schoolMap);
					provinceMap.put(key, l);
				}
				dataMap.put("schools", provinceMap);
			}
			
			return Utils.success(dataMap);
		}else{
			return Utils.failture("登陆失效，请重新登陆！");
		}
	}
	
	/**
	 * 获取公共常量数据
	 * @param userId
	 * @param token
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/getComcodeByGroup",method={RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> getComcodeByGroup(String userId,String token,String group
			,HttpServletRequest req) {
		Map<String, Object> m = Utils.checkParam(req, new String[]{"userId","token","group"});
		if(m != null)
			return m;
		if(userService.checkToken(userId,token)){
			List<Comcode> comcodes = sysService.getComcodeByGroup(group);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("codes", comcodes);
			return Utils.success(dataMap);
		}else{
			return Utils.failture("登陆失效，请重新登陆！");
		}
	}
	
	/**
	 * 获取运营活动列表
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/listActivities",method={RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> listActivities(PageInfo page) {
		List<Activity> activities = activityService.listActivities(null,page);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("activities", activities);
		return Utils.success(dataMap);
	}
	
	/**
	 * 获取活动详细信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getActivityInfo",method={RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> getActivityInfo(String id,String userId) {
		Activity activity = activityService.getActivityById(id);
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if(activity == null)
			return Utils.failture("活动不存在!");
		
		dataMap.put("isJoin", GloabConstant.YESNO_NO);
		if(activityService.isJoin(id,userId))
			dataMap.put("isJoin", GloabConstant.YESNO_YES);
		dataMap.put("status", activity.getStatus());
		dataMap.put("summary", activity.getSummary());
		dataMap.put("url", activity.getUrl());
		dataMap.put("groupId", activity.getId());
		dataMap.put("share_activity_image", activity.getShare_activity_image());
//		dataMap.put("groupName", activity.getGroupName());
		dataMap.put("share_url", "/wxshare/activity_" + id);
		List<Speaker> speakers = activityService.listSpeakers(id);
		if(speakers != null && speakers.size() > 0)
			dataMap.put("speakers", speakers);
		return Utils.success(dataMap);
	}
	
	/**
	 * 参加活动
	 * @param id
	 * @param userId
	 * @param token
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/joinActivity",method={RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> joinActivity(String id,String userId,String token
			,HttpServletRequest req) {
		Map<String, Object> m = Utils.checkParam(req, new String[]{"id","userId","token"});
		if(m != null)
			return m;
		if(userService.checkToken(userId,token)){
			ChartGroup group = activityService.getGroupById(id);
			if(group == null)
				return Utils.failture("活动不存在!");
			
			String status = group.getStatus();
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("joinStatus", GloabConstant.JOIN_GROUP_FAITURE);
			if(GloabConstant.ACTIVITY_STATUS_WKS.equals(status) 
					|| GloabConstant.ACTIVITY_STATUS_JX.equals(status))
			{
				if(activityService.isJoin(id, userId))
					return Utils.failture("您已加入活动，请刷新！");
				//活动进行中可以加入
				RCUtils.joinGroup(userId, id, group.getGroupName());
				activityService.addGroupMember(userId,id);
				dataMap.put("joinStatus", GloabConstant.JOIN_GROUP_SUCCESS);
				dataMap.put("status", group.getStatus());
				return Utils.success(dataMap);
			}else
				return Utils.failture("活动已经结束!");
		}else{
			return Utils.failture("登陆失效，请重新登陆！");
		}
	}
	
	/**
	 * 退出聊天
	 * @param id
	 * @param userId
	 * @param token
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/quitChart",method={RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> quitChart(String id,String userId,String token
			,String type,HttpServletRequest req) {
		Map<String, Object> m = Utils.checkParam(req, new String[]{"id","type","userId","token"});
		if(m != null)
			return m;
		if(userService.checkToken(userId,token)){
			if(GloabConstant.CHART_GROUP.equals(type))
			{
				RCUtils.quitGroup(userId, id);
				activityService.delGroupMember(userId, id);
			}else if(GloabConstant.CHART_PRIVATE.equals(type))
			{
				activityService.delPrivateChart(userId, id);
			}
			return Utils.success(null);
		}else{
			return Utils.failture("登陆失效，请重新登陆！");
		}
	}
	
	/**
	 * 查询会话列表
	 * @param userId
	 * @param token
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/listMessages",method={RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> listMessages(String userId,String token,
			HttpServletRequest req) {
		Map<String, Object> m = Utils.checkParam(req, new String[]{"userId","token"});
		if(m != null)
			return m;
		if(userService.checkToken(userId,token)){
			List<Message> l = activityService.listMessages(userId);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			if(l != null && l.size() > 0)
			{
				dataMap.put("messages", l);
			}
			return Utils.success(dataMap);
		}else{
			return Utils.failture("登陆失效，请重新登陆！");
		}
	}
	
	/**
	 * 获取群成员
	 * @param userId
	 * @param token
	 * @param groupId
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/listMembers",method={RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> listMembers(String userId,String token,String groupId,
			HttpServletRequest req) {
		Map<String, Object> m = Utils.checkParam(req, new String[]{"userId","token","groupId"});
		if(m != null)
			return m;
		if(userService.checkToken(userId,token)){
			List<Member> l = activityService.listMembers(groupId);
			ChartGroup group = activityService.getGroupById(groupId);
			if(group == null)
				return Utils.failture("群组不存在！");
			Map<String, Object> dataMap = new HashMap<String, Object>();
			if(l != null && l.size() > 0)
			{
				dataMap.put("members", Utils.convertBeanToMap(l, 
						new String[]{"memberId","nickname","url"}, Member.class));
			}
			dataMap.put("share_url", "/wxshare/groupmember_" + groupId);
			dataMap.put("share_group_image", group.getShare_group_image());
			dataMap.put("createTime", group.getCreateTime());
			dataMap.put("groupInfo", group.getGroupInfo());
			return Utils.success(dataMap);
		}else{
			return Utils.failture("登陆失效，请重新登陆！");
		}
	}
	
	/**
	 * 获取群成员信息
	 * @param userId
	 * @param token
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/getMemberInfo",method={RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> getMemberInfo(String userId,String token,String memberId
			,HttpServletRequest req) {
		Map<String, Object> m = Utils.checkParam(req, new String[]{"userId","token","memberId"});
		if(m != null)
			return m;
		if(userService.checkToken(userId,token)){
			User user = userService.getUserInfoById(memberId);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("nickname", user.getNickname());
			dataMap.put("school", user.getSchoolId());
			dataMap.put("schoolName", user.getSchoolName());
			dataMap.put("major", user.getMajor());
			dataMap.put("grade", user.getGrade());
			dataMap.put("gradeName", user.getGradeName());
			dataMap.put("url", user.getUrl());
			dataMap.put("background_url", user.getBackground_url());
			dataMap.put("type", user.getType());
			dataMap.put("joinActivityNum", activityService.getJoinActivityNum(memberId) + "");
			dataMap.put("askGreaterNum", activityService.getAskGreaterNum(memberId) + "");
			return Utils.success(dataMap);
		}else{
			return Utils.failture("登陆失效，请重新登陆！");
		}
	}
	
	/**
	 * 意见反馈
	 * @param suggest
	 * @param contact
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/suggest",method={RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> suggest(String suggest,String contact,HttpServletRequest req) {
		Map<String, Object> m = Utils.checkParam(req, new String[]{"suggest"});
		if(m != null)
			return m;
		Suggest s = new Suggest();
		s.setSuggest(suggest);
		s.setContact(contact);
		s.setInsertTime(String.valueOf(System.currentTimeMillis()));
		sysService.insertSuggest(s);
		return Utils.success(null);
	}
	
	/**
	 * 查询大拿列表
	 * @param group
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/listGreaters",method={RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> listGreaters(PageInfo page) {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<Greater> greaters = userService.listGreaters(null,page);
		if(greaters != null && greaters.size() > 0)
		{
			dataMap.put("greaters", Utils.convertBeanToMap(greaters,
					new String[]{"id","nickname","company","post","url","answerTimes"}, Greater.class));
		}
		
		return Utils.success(dataMap);
	}
	
	/**
	 * 查询大拿信息
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/getGreaterInfo",method={RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> getGreaterInfo(String id,HttpServletRequest req) {
		Map<String, Object> m = Utils.checkParam(req, new String[]{"id"});
		if(m != null)
			return m;
		Greater greater = userService.getGreaterInfoById(id);
		if(greater == null)
			return Utils.failture("大拿不存在！");
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("nickname", greater.getNickname());
		dataMap.put("company", greater.getCompany());
		dataMap.put("post", greater.getPost());
		dataMap.put("tags", greater.getTags());
		dataMap.put("experience", greater.getExperience());
		dataMap.put("specialty", greater.getSpecialty());
		dataMap.put("job", greater.getJob());
		dataMap.put("url", greater.getUrl());
		dataMap.put("backgroud_url", greater.getBackgroud_url());
		return Utils.success(dataMap);
	}
	
	/**
	 * 问大拿
	 * @param userId
	 * @param token
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/askGreater",method={RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> askGreater(String userId,String token,String greaterId
			,HttpServletRequest req) {
		Map<String, Object> m = Utils.checkParam(req, new String[]{"userId","token","greaterId"});
		if(m != null)
			return m;
		if(userService.checkToken(userId,token)){
			activityService.createPrivateChart(userId,greaterId);
			return Utils.success(null);
		}else{
			return Utils.failture("登陆失效，请重新登陆！");
		}
	}
	
	/**
	 * 获取当前app版本
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getCurrentVersion",method={RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> getCurrentVersion(String deviceType) {
		AppVersion version = sysService.getCurrentVersion(deviceType);
		Map<String, Object> dataMap = new HashMap<String,Object>();
		if(version != null)
		{
			dataMap.put("versionCode", version.getVersionCode());
			dataMap.put("versionName", version.getVersionName());
			dataMap.put("updateDesc", version.getUpdateDesc());
			dataMap.put("appUrl", version.getAppUrl());
		}else{
			dataMap.put("versionCode", "");
			dataMap.put("versionName", "");
			dataMap.put("updateDesc", "");
			dataMap.put("appUrl", "");
		}
		return Utils.success(dataMap);
	}
}
