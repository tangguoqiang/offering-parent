package com.offering.core.service;

import java.util.List;

import com.offering.bean.sys.PageInfo;
import com.offering.bean.user.Greater;
import com.offering.bean.user.Topic;
import com.offering.bean.user.User;

/**
 * 大拿service
 * @author surfacepro3
 *
 */
public interface GreaterService {

	/**
	 * 查询大拿列表
	 * @param page
	 * @return
	 */
	List<Greater> listGreaters(User user,PageInfo page);
	
	/**
	 * 获取大拿数量
	 * @param user
	 * @return
	 */
	long getGreaterCount(User user);
	
	/**
	 * 根据大拿id获取大拿信息
	 * @param id
	 * @return
	 */
	Greater getGreaterInfoById(String id);
	
	/**
	 * 新增大拿
	 * @param greater
	 */
	void insertGreater(User user,Greater greater);
	
	/**
	 * 更新大拿
	 * @param greater
	 */
	void updateGreater(User user,Greater greater);
	
	/**
	 * 上传大拿头像
	 * @param id
	 * @param url
	 */
	void uploadGreaterImage(String id,String url,String uploadType);
	
	/**
	 * 删除大拿
	 * @param id
	 */
	void delGreater(String id);
	
	/**
	 * 根据大拿id获取话题数据
	 * @param greaterId
	 * @return
	 */
	List<Topic> listTopics(String greaterId);
	
	/**
	 * 根据话题id获取话题信息
	 * @param id
	 * @return
	 */
	Topic getTopicInfo(String id);
	
	/**
	 * 新增话题
	 * @param topic
	 */
	void addTopic(Topic topic);
	
	/**
	 * 更新话题
	 * @param topic
	 */
	void updateTopic(Topic topic);
	
	/**
	 * 删除话题
	 * @param id
	 */
	void delTopic(String id);
}
