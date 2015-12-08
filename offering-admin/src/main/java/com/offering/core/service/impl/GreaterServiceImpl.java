package com.offering.core.service.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.offering.bean.sys.PageInfo;
import com.offering.bean.sys.ParamInfo;
import com.offering.bean.user.Greater;
import com.offering.bean.user.Topic;
import com.offering.bean.user.User;
import com.offering.constant.DBConstant;
import com.offering.core.dao.GreaterDao;
import com.offering.core.dao.TopicDao;
import com.offering.core.dao.UserDao;
import com.offering.core.service.GreaterService;

/**
 * 大拿service实现
 * @author surfacepro3
 *
 */
@Service
public class GreaterServiceImpl implements GreaterService{

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private GreaterDao greaterDao;
	
	@Autowired
	private TopicDao topicDao;
	
	/**
	 * 查询大拿列表
	 * @param page
	 * @return
	 */
	public List<Greater> listGreaters(User user,PageInfo page)
	{
		return greaterDao.listGreaters(user, page);
	}
	
	/**
	 * 获取大拿数量
	 * @param user
	 * @return
	 */
	public long getGreaterCount(User user)
	{
		return greaterDao.getGreaterCount(user);
	}
	
	/**
	 * 根据大拿id获取大拿信息
	 * @param id
	 * @return
	 */
	public Greater getGreaterInfoById(String id)
	{
		return greaterDao.getGreaterInfoById(id);
	}
	
	/**
	 * 新增大拿
	 * @param greater
	 */
	@Transactional
	public void insertGreater(User user,Greater greater)
	{
		user.setInsertTime(String.valueOf(System.currentTimeMillis()));
		long id = userDao.insertRecord(user, "USER_INFO");
		greater.setId(String.valueOf(id));
		greaterDao.insertRecord(greater, "USER_GREATER");
	}
	
	/**
	 * 更新大拿
	 * @param user
	 * @return
	 */
	@Transactional
	public void updateGreater(User user,Greater greater){
		userDao.updateUser(user);
		greaterDao.updateGreater(greater);
	}
	
	/**
	 * 上传大拿头像
	 * @param id
	 * @param url
	 */
	public void uploadGreaterImage(String id,String url,String uploadType)
	{
		StringBuilder sql = new StringBuilder();
		if("0".equals(uploadType))
		{
			sql.append("update USER_INFO set url =? WHERE id=? ");
		}else{
			sql.append("update USER_GREATER set backgroud_url =? WHERE id=? ");
		}
		
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.VARCHAR, url);
		paramInfo.setTypeAndData(Types.BIGINT, id);
		userDao.updateRecord(sql.toString(), paramInfo);
	}
	
	/**
	 * 删除大拿
	 * @param id
	 */
	@Transactional
	public void delGreater(String id)
	{
		topicDao.delRecsByGreaterId(id);
		greaterDao.delRecordById(id, DBConstant.USER_GREATER);
		userDao.delRecordById(id, DBConstant.USER_INFO);
	}
	
	/**
	 * 根据大拿id获取话题数据
	 * @param greaterId
	 * @return
	 */
	public List<Topic> listTopics(String greaterId){
		return topicDao.listTopicsByGreaterId(greaterId);
	}
	
	/**
	 * 根据话题id获取话题信息
	 * @param id
	 * @return
	 */
	public Topic getTopicInfo(String id){
		return topicDao.getTopicInfoById(id);
	}
	
	/**
	 * 新增话题
	 * @param topic
	 */
	public void addTopic(Topic topic){
		topicDao.insertRecord(topic, DBConstant.TOPIC_INFO);
	}
	
	/**
	 * 更新话题
	 * @param topic
	 */
	public void updateTopic(Topic topic){
		topicDao.updateTopic(topic);
	}
	
	/**
	 * 删除话题
	 * @param id
	 */
	public void delTopic(String id){
		topicDao.delRecordById(id, DBConstant.TOPIC_INFO);
	}
}
