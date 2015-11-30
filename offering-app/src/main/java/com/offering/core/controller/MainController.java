package com.offering.core.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.offering.bean.chart.ChartGroup;
import com.offering.bean.chart.Member;
import com.offering.constant.GloabConstant;
import com.offering.core.service.ActivityService;
import com.offering.core.service.TradeService;
import com.offering.utils.Utils;
import com.pingplusplus.model.Event;
import com.pingplusplus.model.Webhooks;

@Controller
public class MainController {
	
	private final static Logger LOG = Logger.getLogger(MainController.class);
	
	@Autowired
	private TradeService tradeService;
	
	@Autowired
	private ActivityService activityService;

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
		LOG.debug(fileName + "." + suff);
		if("apk".equals(suff))
		{
//			rep.setHeader("Content-Encoding","gzip");
			rep.setContentType("application/octet-stream");
		}
		else
			rep.setContentType("image/*");  
		String filePath = GloabConstant.ROOT_DIR + path + "/" + fileName + "." + suff;
		long contentLength = new File(filePath).length();
		LOG.debug("文件大小:" + contentLength);
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
	
	/**
	 * 支付回调函数
	 * @param userName
	 * @param oldPass
	 * @param newPass
	 * @return
	 */
	@RequestMapping(value = "/payCallBack", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> payCallBack(HttpServletRequest req,
			HttpServletResponse response) throws Exception{
		Map<String, Object> m = new HashMap<String, Object>();
		req.setCharacterEncoding("UTF8");
        //获取头部所有信息
//        Enumeration<String> headerNames = req.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String key = (String) headerNames.nextElement();
//            String value = req.getHeader(key);
//            LOG.info(key+" "+value);
//        }
        // 获得 http body 内容
        BufferedReader reader = req.getReader();
        StringBuffer buffer = new StringBuffer();
        String string;
        while ((string = reader.readLine()) != null) {
            buffer.append(string);
        }
        reader.close();
        
        
        // 解析异步通知数据
        Event event = Webhooks.eventParse(buffer.toString());
        if ("charge.succeeded".equals(event.getType())) {
        	//TODO 交易成功后的处理
        	JSONObject jsonObj = new JSONObject(buffer.toString());
            LOG.info(jsonObj);
            tradeService.trade_success(jsonObj);
            response.setStatus(200);
        } else if ("refund.succeeded".equals(event.getType())) {
        	//退款成功事件
            response.setStatus(200);
        } else {
            response.setStatus(500);
        }
		return m;
	}
}
