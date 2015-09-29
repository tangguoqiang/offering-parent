package com.offering.core.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.offering.bean.User;
import com.offering.constant.GloabConstant;
import com.offering.core.service.MainService;
import com.offering.core.service.UserService;
import com.offering.utils.MD5Util;

@Controller
public class MainController {
	
	private final static Logger LOG = Logger.getLogger(MainController.class);
	
	@Autowired
	private MainService mainService;
	
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
	 * 密码修改
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/resetPass", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> resetPass(String userName,
			String oldPass,String newPass) {
		Map<String, Object> m = new HashMap<String, Object>();
		User user = mainService.getUserInfo(userName,MD5Util.string2MD5(oldPass));
		if(user != null){
			mainService.resetPass(userName,MD5Util.string2MD5(newPass));
			m.put("success", true);
		}else{
			m.put("success", false);
			m.put("msg", "旧密码输入错误！");
		}
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
		LOG.info(fileName + "." + suff);
		if("apk".equals(suff))
		{
//			rep.setHeader("Content-Encoding","gzip");
			rep.setContentType("application/octet-stream");
		}
		else
			rep.setContentType("image/*");  
		String filePath = GloabConstant.ROOT_DIR + path + "/" + fileName + "." + suff;
		long contentLength = new File(filePath).length();
		LOG.info("apk文件大小:" + contentLength);
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
	
	/**
	 * 微信分享页面-活动
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/wxshare/activity_{id}",  method ={RequestMethod.POST,RequestMethod.GET})
	public String share_wx_activity(@PathVariable("id")String id,Model model) {
		model.addAttribute("activityId", id);
		return "pages/wx/share";
	}
	
	/**
	 * 微信分享页面-群成员
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/wxshare/groupmember_{id}",  method ={RequestMethod.POST,RequestMethod.GET})
	public String share_wx_group(@PathVariable("id")String id,Model model) {
		model.addAttribute("groupId", id);
		return "pages/wx/share_group";
	}
	
	/**
	 * 微信打开APP页面
	 * 
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/wxshare/open_app",  method ={RequestMethod.POST,RequestMethod.GET})
	public String open_app() {
		return "pages/wx/open_app";
	}
}
