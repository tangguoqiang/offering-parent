package com.offering.core.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.offering.bean.ChartGroup;
import com.offering.bean.Member;
import com.offering.bean.PageInfo;
import com.offering.bean.Speaker;
import com.offering.constant.GloabConstant;
import com.offering.core.service.ActivityService;
import com.offering.utils.Utils;

/**
 * 活动入口
 * @author surfacepro3
 *
 */
@Controller
@RequestMapping(value ="/activity")
public class ActivityController {

	private final static Logger LOG = Logger.getLogger(ActivityController.class);
	
	@Autowired
	private ActivityService activityService;
	
	/**
	 * 查询活动数据
	 * @param act
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/listActivities", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> listGreaters(Activity act,PageInfo page){
		Map<String, Object> m = new HashMap<String, Object>();
		List<Activity> l = activityService.listActivities(act,page);
		m.put("records", l);
		m.put("totalCount", activityService.getActivityCount(act));
		return m;
	}
	
	/**
	 * 查询活动数据
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getActivityInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getActivityInfo(String id){
		Map<String, Object> m = new HashMap<String, Object>();
		//TODO 多表关联查询结构有待优化
		Activity act = activityService.getActivityById(id);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(act != null)
		{
			m.put("id", act.getId());
			m.put("title", act.getTitle());
			m.put("type", act.getType());
			if(!Utils.isEmpty(act.getStartTime()))
				act.setStartTime(sdf.format(new Date(Long.valueOf(act.getStartTime()))));
			if(!Utils.isEmpty(act.getEndTime()))
				act.setEndTime(sdf.format(new Date(Long.valueOf(act.getEndTime()))));
			m.put("startTime", act.getStartTime());
			m.put("endTime", act.getEndTime());
		}
		
		ChartGroup group = activityService.getGroupById(id);
		if(group != null)
		{
			m.put("groupName", group.getGroupName());
			m.put("groupInfo", group.getGroupInfo());
		}
		return m;
	}
	
	/**
	 * 新增/更新活动
	 * @param user
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/saveActivity", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveActivity(Activity act,ChartGroup group) throws ParseException{
		Map<String, Object> m = new HashMap<String, Object>();
		act.setStatus(GloabConstant.ACTIVITY_STATUS_CG);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		if(!Utils.isEmpty(act.getStartTime()))
		{
			act.setStartTime(sdf.parse(act.getStartTime()).getTime() + "");
		}
		
		if(!Utils.isEmpty(act.getEndTime()))
		{
			act.setEndTime(sdf.parse(act.getEndTime()).getTime() + "");
		}
		if(Utils.isEmpty(act.getId())){
			//新增活动
			group.setCreateTime(System.currentTimeMillis() + "");
			activityService.insertActivity(act,group);
		}else{
			//更新活动
			activityService.updateActivity(act,group);
		}
		m.put("success", true);
		return m;
	}
	
	/**
	 * 删除活动
	 * @param id
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/delActivity", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delActivity(String id)
	{
		Map<String, Object> m = new HashMap<String, Object>();
		activityService.delActivity(id);
		m.put("success", true);
		return m;
	}
	
	/**
	 * 活动发布
	 * @param id
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/publishActivity", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> publishActivity(String id)
	{
		Map<String, Object> m = new HashMap<String, Object>();
		String msg = activityService.publishActivity(id);
		if(Utils.isEmpty(msg))
			m.put("success", true);
		else{
			m.put("success", false);
			m.put("msg", msg);
		}
		return m;
	}
	
	/**
	 * 开始活动
	 * @param id
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/startActivity", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> startActivity(String id)
	{
		Map<String, Object> m = new HashMap<String, Object>();
		Activity  act = activityService.getActivityById(id);
		if(act != null)
		{
			if(!GloabConstant.ACTIVITY_STATUS_WKS.equals(act.getStatus()))
			{
				m.put("success", false);
				m.put("msg", "活动状态不为'未开始'，请重新选择!");
			}else{
				activityService.updateActivityStatus(id, GloabConstant.ACTIVITY_STATUS_JX);
				m.put("success", true);
			}
		}
		
		return m;
	}
	
	/**
	 * 结束活动
	 * @param id
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/endActivity", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> endActivity(String id)
	{
		Map<String, Object> m = new HashMap<String, Object>();
		Activity  act = activityService.getActivityById(id);
		if(act != null)
		{
			if(!GloabConstant.ACTIVITY_STATUS_JX.equals(act.getStatus()))
			{
				m.put("success", false);
				m.put("msg", "活动状态不为'进行中'，请重新选择!");
			}else{
				activityService.updateActivityStatus(id, GloabConstant.ACTIVITY_STATUS_JS);
				m.put("success", true);
			}
		}
		
		return m;
	}
	
	/**
	 * 查询主讲人数据
	 * @param act
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/listSpeakers", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> listSpeakers(String id){
		Map<String, Object> m = new HashMap<String, Object>();
		List<Speaker> l = activityService.listSpeakers(id);
		m.put("records", l);
		return m;
	}
	
	/**
	 * 保存主讲人
	 * @param speaker
	 * @return
	 */
	@RequestMapping(value = "/saveSpeaker", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveSpeaker(Speaker speaker){
		Map<String, Object> m = new HashMap<String, Object>();
		if(Utils.isEmpty(speaker.getId())){
			//新增主讲人
			activityService.insertSpeaker(speaker);
		}else{
			//更新主讲人
			activityService.updateSpeaker(speaker);
		}
		m.put("success", true);
		return m;
	}
	
	/**
	 * 获取主讲人信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getSpeakerInfo", method = RequestMethod.POST)
	@ResponseBody
	public Speaker getSpeakerInfo(String id){
		return activityService.getSpeakerInfo(id);
	}
	
	/**
	 * 删除主讲人
	 * @param id
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/delSpeaker", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delSpeaker(String id)
	{
		Map<String, Object> m = new HashMap<String, Object>();
		activityService.delSpeaker(id);
		m.put("success", true);
		return m;
	}
	
	/**
	 * 上传头像
	 * @param req
	 * @return
	 */
	@SuppressWarnings("unchecked")
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
	                filename = "activity_" + System.currentTimeMillis() + filename.substring(filename.indexOf("."));
	                LOG.info("获取文件总量的容量:"+ item.getSize());  
	                InputStream in = item.getInputStream();  
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
				try {
					out.close();
				} catch (IOException e) {
					LOG.error(e.getMessage());
				}
	    }
	    
	    String id = paramMap.get("id");
	    String uploadType = paramMap.get("uploadType");
	    
	    Activity act = activityService.getActivityById(id);
	    if("0".equals(uploadType))
	    {
	    	//活动图片
	    	if(!Utils.isEmpty(act.getUrl()))
	    	{
	    		File tmpFile = new File(GloabConstant.ROOT_DIR + "userImages" 
	    				,act.getUrl().substring(act.getUrl().lastIndexOf("/") + 1));
	    		if(tmpFile.exists())
	    		{
	    			tmpFile.delete();
	    		}
	    	}
	    }else if("1".equals(uploadType)){
	    	//活动分享图片
	    	if(!Utils.isEmpty(act.getShare_activity_image()))
	    	{
	    		File tmpFile = new File(GloabConstant.ROOT_DIR + "userImages" 
	    				,act.getShare_activity_image().substring(act.getShare_activity_image().lastIndexOf("/") + 1));
	    		if(tmpFile.exists())
	    		{
	    			tmpFile.delete();
	    		}
	    	}
	    }else if("2".equals(uploadType)){
	    	//群分享图片
	    	ChartGroup group = activityService.getGroupById(id);
	    	if(!Utils.isEmpty(group.getShare_group_image()))
	    	{
	    		File tmpFile = new File(GloabConstant.ROOT_DIR + "userImages" 
	    				,group.getShare_group_image().substring(group.getShare_group_image().lastIndexOf("/") + 1));
	    		if(tmpFile.exists())
	    		{
	    			tmpFile.delete();
	    		}
	    	}
	    }
	    
	    String url = "/download/userImages/" + filename;
	    activityService.uploadActivityImage(id,url,uploadType);
	    
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("success", true);
		return m;
	}
	
	/**
	 * 获取群成员
	 * @param groupId
	 * @return
	 */
	@RequestMapping(value = "/listMembers",method={RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> listMembers(String groupId) {
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
		//TODO 分页网页
		dataMap.put("share_url", "/wxshare/groupmember_" + groupId);
		dataMap.put("createTime", group.getCreateTime());
		dataMap.put("groupInfo", group.getGroupInfo());
		dataMap.put("groupName", group.getGroupName());
		return Utils.success(dataMap);
	}
}
