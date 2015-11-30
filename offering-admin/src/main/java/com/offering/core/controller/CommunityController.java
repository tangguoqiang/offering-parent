package com.offering.core.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.offering.bean.community.CommunityTopic;
import com.offering.bean.sys.PageInfo;
import com.offering.core.service.CommunityService;

/**
 * 社区入口
 * @author surfacepro3
 *
 */
@Controller
@RequestMapping(value ="/community")
public class CommunityController {
	
	@Autowired
	private CommunityService communityService;

	/**
	 * 查询活动数据
	 * @param act
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/listTopics", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> listTopics(CommunityTopic topic,PageInfo page){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("records", communityService.listTopics(topic,page));
		m.put("totalCount", communityService.getTopicCount(topic));
		return m;
	}
	
	/**
	 * 根据话题id获取话题信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getTopicInfo", method = RequestMethod.POST)
	@ResponseBody
	public CommunityTopic getTopicInfo(String id){
		return communityService.getTopicInfo(id);
	}
	
	/**
	 * 根据话题id删除话题
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delTopic", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delTopic(String id){
		Map<String, Object> m = new HashMap<String, Object>();
		communityService.deleteCommunityTopic(id);
		m.put("success", true);
		return m;
	}
	
	/**
	 * 话题置顶
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/top", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> top(String id){
		Map<String, Object> m = new HashMap<String, Object>();
		communityService.top(id);
		m.put("success", true);
		return m;
	}
	
	/**
	 * 查询评论数据
	 * @param topicId
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/listComments", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> listComments(String topicId,PageInfo page){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("records", communityService.listComments(topicId,page));
		m.put("totalCount", communityService.getCommentsCount(topicId));
		return m;
	}
	
	/**
	 * 根据评论id删除评论
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delComment", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delComment(String id){
		Map<String, Object> m = new HashMap<String, Object>();
		communityService.delComment(id);
		m.put("success", true);
		return m;
	}
}
