package com.offering.core.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

import com.offering.bean.sys.PageInfo;
import com.offering.bean.user.Greater;
import com.offering.bean.user.User;
import com.offering.constant.GloabConstant;
import com.offering.core.service.UserService;
import com.offering.utils.MD5Util;
import com.offering.utils.Utils;

@Controller
@RequestMapping(value ="/greater")
public class GreaterController {

	private final static Logger LOG = Logger.getLogger(GreaterController.class);
	
	@Autowired
	private UserService userService;
	
	/**
	 * 查询大拿数据
	 * @param user
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/listGreaters", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> listGreaters(User user,PageInfo page){
		Map<String, Object> m = new HashMap<String, Object>();
		List<Greater> l = userService.listGreaters(user,page);
		m.put("records", l);
		m.put("totalCount", userService.getGreaterCount(user));
		return m;
	}
	
	/**
	 * 查询大拿数据
	 * @param user
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/getGreaterInfo", method = RequestMethod.POST)
	@ResponseBody
	public Greater getGreaterInfo(String id){
		return userService.getGreaterInfoById(id);
	}
	
	/**
	 * 新增/更新大拿
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/saveGreater", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveGreater(Greater greater){
		Map<String, Object> m = new HashMap<String, Object>();
		User user = new User();
		user.setId(greater.getId());
		user.setNickname(greater.getNickname());
		user.setPhone(greater.getPhone());
		user.setType(GloabConstant.USER_TYPE_GREATER);
		
		greater.setNickname(null);
		greater.setPhone(null);
		boolean isExists = userService.isExistUser(user);
		if(isExists){
			m.put("success", false);
			m.put("msg", "该手机号已经注册！");
		}else{
			if(Utils.isEmpty(user.getId())){
				//新增用户
				//初始密码123
				user.setPassword(MD5Util.string2MD5("offering"));
				userService.insertGreater(user,greater);
			}else{
				//更新用户信息
				userService.updateGreater(user,greater);
			}
			m.put("success", true);
		}
		
		return m;
	}
	
	/**
	 * 上传头像
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> uploadImage(HttpServletRequest req){
		 DiskFileItemFactory factory = new DiskFileItemFactory();  
       //设置暂时存放文件的存储室，这个存储室可以和最终存储文件的文件夹不同。因为当文件很大的话会占用过多内存所以设置存储室。  
       factory.setRepository(new File(GloabConstant.ROOT_DIR + "tmp"));  
       //设置缓存的大小，当上传文件的容量超过缓存时，就放到暂时存储室。  
       factory.setSizeThreshold(1024*1024);  
       //上传处理工具类（高水平API上传处理？）  
       ServletFileUpload upload = new ServletFileUpload(factory);  
	    upload.setSizeMax(10*1024*1024);   // 设置允许用户上传文件大小,单位:字节 10M
	    Map<String, String> paramMap = new HashMap<String, String>();
	    String filename = null;
	    OutputStream out  = null;
	    InputStream in = null;
	    try{
	    	List<FileItem> list = (List<FileItem>)upload.parseRequest(req);  
	    	for(FileItem item:list){  
	    		String name = item.getFieldName();  
	    		if(item.isFormField()){  
	    			//如果获取的表单信息是普通的文本信息。即通过页面表单形式传递来的字符串。  
	    			String value = item.getString();  
	    			paramMap.put(name, value);
	    		}else{   
	            	//如果传入的是非简单字符串，而是图片，音频，视频等二进制文件。  
	                String value = item.getName();  
	                filename = value.substring(value.lastIndexOf("\\") + 1);  
	                filename = System.currentTimeMillis() +"_greater" + filename.substring(filename.indexOf("."));
	                LOG.info("获取文件总量的容量:"+ item.getSize());  
	                in = item.getInputStream();  
	                out = new FileOutputStream(
		            		new File(GloabConstant.ROOT_DIR + "userImages",filename));
					int length = 0;  
		            byte[] buf = new byte[1024];  
		            while((length = in.read(buf))!=-1){  
		                out.write(buf,0,length);  
		            }  
	    		}  
	    	}  
	    }catch(Exception e){  
	    	LOG.error(e.getMessage());
	    }  
	    finally{
	    	if(out != null)
	    	{
	    		try {
					out.close();
				} catch (IOException e) {
					LOG.error(e.getMessage());
				}
	    	}
	    	
	    	
	    	if(in != null)
	    	{
	    		try {
	    			in.close();
				} catch (IOException e) {
					LOG.error(e.getMessage());
				}
	    	}
	    }
	    
	    String id = paramMap.get("id");
	    String uploadType = paramMap.get("uploadType");
	    
	    Greater greater = userService.getGreaterInfoById(id);
	    if("0".equals(uploadType))
	    {
	    	//大拿头像
	    	if(!Utils.isEmpty(greater.getUrl()))
	    	{
	    		File tmpFile = new File(GloabConstant.ROOT_DIR + "userImages" 
	    				,greater.getUrl().substring(greater.getUrl().lastIndexOf("/") + 1));
	    		if(tmpFile.exists())
	    		{
	    			tmpFile.delete();
	    		}
	    	}
	    }else{
	    	//背景图片
	    	if(!Utils.isEmpty(greater.getBackgroud_url()))
	    	{
	    		File tmpFile = new File(GloabConstant.ROOT_DIR + "userImages" 
	    				,greater.getBackgroud_url().substring(greater.getBackgroud_url().lastIndexOf("/") + 1));
	    		if(tmpFile.exists())
	    		{
	    			tmpFile.delete();
	    		}
	    	}
//	    	filename = filename.substring(0,filename.indexOf(".")) +
//	    			"_backgroud"+ filename.substring(filename.indexOf("."));
	    }
	    
	    String url = "/download/userImages/" + filename;
	    userService.uploadGreaterImage(id,url,uploadType);
	    
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("success", true);
		return m;
	}
	
	/**
	 * 删除大拿
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delGreater", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delGreater(String id)
	{
		userService.delGreater(id);
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("success", true);
		return m;
	}
}
