package com.offering.core.service.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.offering.bean.Activity;
import com.offering.bean.ChartGroup;
import com.offering.bean.Member;
import com.offering.bean.Message;
import com.offering.bean.PageInfo;
import com.offering.bean.ParamInfo;
import com.offering.bean.Speaker;
import com.offering.constant.GloabConstant;
import com.offering.core.dao.BaseDao;
import com.offering.core.service.ActivityService;
import com.offering.utils.RCUtils;
import com.offering.utils.Utils;

@Service
public class ActivityServiceImpl implements ActivityService{

	@Autowired
	private BaseDao<Activity> activityDao;
	
	@Autowired
	private BaseDao<Speaker> speakerDao;
	
	@Autowired
	private BaseDao<ChartGroup> groupDao;
	
	@Autowired
	private BaseDao<Member> memberDao;
	
	@Autowired
	private BaseDao<Message> messDao;
	
	
	/**
	 * 获取活动列表
	 * @param page
	 * @return
	 */
	public List<Activity> listActivities(Activity act,PageInfo page)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("select id,title,startTime,endTime,type, ")
		   .append("status,url FROM ACTIVITY_INFO ");
		ParamInfo paramInfo = new ParamInfo();
		if(act == null)
		{
			sql.append("WHERE status <> ? ");
			paramInfo.setTypeAndData(Types.CHAR, GloabConstant.ACTIVITY_STATUS_CG);
		}else{
			sql.append("WHERE 1=1 ");
			if(!Utils.isEmpty(act.getTitle()))
			{
				sql.append("AND title like ? ");
				paramInfo.setTypeAndData(Types.VARCHAR, "%" + act.getTitle() + "%");
			}
			
			if(!Utils.isEmpty(act.getStatus()))
			{
				sql.append(" AND status = ? ");
				paramInfo.setTypeAndData(Types.CHAR, act.getStatus());
			}
		}
		
		sql.append("order by startTime desc ");
		return activityDao.getRecords(sql.toString(), paramInfo, page, Activity.class);
	}
	
	/**
	 * 获取活动列表(app端)
	 * @param act
	 * @param page
	 * @return
	 */
	public List<Activity> listActivities_app(String startTime,String endTime,int limit){
		StringBuilder sql = new StringBuilder();
		sql.append("select id,title,startTime,endTime,type, ")
		   .append("status,url,remark, ")
		   .append("(select count(1) from RC_GROUP_MEMBER T where T.groupId=T1.id) joinMembers ")
		   .append("FROM ACTIVITY_INFO T1 ")
		   .append("WHERE status <> ? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.CHAR, GloabConstant.ACTIVITY_STATUS_CG);
		
		if(!Utils.isEmpty(startTime))
		{
			sql.append("AND startTime > ? ");
			paramInfo.setTypeAndData(Types.BIGINT, startTime);
		}
		
		if(!Utils.isEmpty(endTime))
		{
			sql.append("AND startTime < ? ");
			paramInfo.setTypeAndData(Types.BIGINT, endTime);
		}
		if(Utils.isEmpty(endTime) && !Utils.isEmpty(startTime))
		{
			sql.append("order by startTime asc ");
		}else{
			sql.append("order by startTime desc ");
		}
		sql.append("limit ").append(limit);
		return activityDao.getRecords(sql.toString(), paramInfo, Activity.class);
	}
	
	/**
	 * 获取活动数量
	 * @param act
	 * @return
	 */
	public long getActivityCount(Activity act)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("select count(1) ")
		   .append("FROM ACTIVITY_INFO ");
		ParamInfo paramInfo = new ParamInfo();
		if(act != null){
			sql.append("WHERE 1=1 ");
			if(!Utils.isEmpty(act.getTitle()))
			{
				sql.append("AND title like ? ");
				paramInfo.setTypeAndData(Types.VARCHAR, "%" + act.getTitle() + "%");
			}
			
			if(!Utils.isEmpty(act.getStatus()))
			{
				sql.append(" AND status = ? ");
				paramInfo.setTypeAndData(Types.CHAR, act.getStatus());
			}
		}
		
		return activityDao.getCount(sql.toString(), paramInfo);
	}
	
	/**
	 * 根据ID获取活动信息
	 * @param id
	 * @return
	 */
	public Activity getActivityById(String id)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT T1.id,title,startTime,endTime,type,status,url,summary, ")
		   .append("share_activity_image,address,remark, ")
		   .append("(select count(1) from RC_GROUP_MEMBER T where T.groupId=T1.id) joinMembers ")
		   .append("FROM ACTIVITY_INFO T1 ")
//		   .append("LEFT JOIN RC_GROUP T2 ON T2.id=T1.id ")
		   .append("WHERE T1.id=? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.BIGINT, id);
		return activityDao.getRecord(sql.toString(), paramInfo, Activity.class);
	}
	
	/**
	 * 根据活动ID获取主讲人列表
	 * @param activityId
	 * @return
	 */
	public List<Speaker> listSpeakers(String activityId)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT T1.id,T1.speakerId,T1.remark,T2.nickname AS name,T2.url ")
		   .append("FROM ACTIVITY_SPEAKER T1 ")
		   .append("LEFT JOIN USER_INFO T2 ON T2.id=T1.speakerId ")
		   .append("WHERE T1.activityId=? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.BIGINT, activityId);
		return speakerDao.getRecords(sql.toString(), paramInfo, Speaker.class);
	}
	
	/**
	 * 根据活动ID获取主讲人列表(批量)
	 * @param activityId
	 * @return
	 */
	public Map<String, Speaker> listSpeakers(List<String> ids)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT T1.id,T1.speakerId,T1.activityId,T1.remark,T2.nickname AS name,T2.url, ")
		   .append("T3.company,T3.post,T3.tags ")
		   .append("FROM ACTIVITY_SPEAKER T1 ")
		   .append("INNER JOIN USER_INFO T2 ON T2.id=T1.speakerId ")
		   .append("INNER JOIN USER_GREATER T3 ON T3.id=T1.speakerId ")
		   .append("WHERE T2.nickname <> '小O' ");
		ParamInfo paramInfo = new ParamInfo();
		if(ids != null && ids.size() > 0)
		{
			sql.append("AND T1.activityId in (");
			for(String id : ids)
			{
				sql.append("?,");
				paramInfo.setTypeAndData(Types.BIGINT, id);
			}
			sql.replace(sql.length() - 1, sql.length(), ")");
		}
		List<Speaker> speakers = speakerDao.getRecords(sql.toString(), paramInfo, Speaker.class);
		
		Map<String, Speaker> m = new HashMap<String, Speaker>();
		if(speakers != null)
		{
			for(Speaker speaker : speakers)
			{
				m.put(speaker.getActivityId(), speaker);
			}
		}
		return m;
	}
	
	/**
	 * 根据群聊id获取群聊信息
	 * @param groupId
	 * @return
	 */
	public ChartGroup getGroupById(String groupId){
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT status,T2.groupName,T2.createTime,T2.groupInfo,T2.share_group_image ")
		   .append("FROM ACTIVITY_INFO T1 ")
		   .append("INNER JOIN RC_GROUP T2 ON T2.id=T1.id ")
		   .append("WHERE T1.id=? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.BIGINT, groupId);
		return groupDao.getRecord(sql.toString(), paramInfo, ChartGroup.class);
	}
	
	/**
	 * 新增群组成员
	 * @param userId
	 * @param groupId
	 */
	public void addGroupMember(String userId,String groupId)
	{
		Member member = new Member();
		member.setGroupId(groupId);
		member.setMemberId(userId);
		memberDao.insertRecord(member, "RC_GROUP_MEMBER");
	}
	
	/**
	 * 删除群组成员
	 * @param userId
	 * @param groupId
	 */
	public void delGroupMember(String userId,String groupId)
	{
		String sql = "DELETE FROM RC_GROUP_MEMBER WHERE groupId=? AND memberId = ? ";
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.BIGINT, groupId);
		paramInfo.setTypeAndData(Types.BIGINT, userId);
		memberDao.delRecord(sql, paramInfo);
	}
	
	/**
	 * 根据用户id查询消息列表
	 * @param userId
	 * @return
	 */
	public List<Message> listMessages(String userId)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT T1.groupId id,T2.groupName name,'0' AS type,T2.share_group_image AS url ")
		   .append("FROM RC_GROUP_MEMBER T1 ")
		   .append("LEFT JOIN RC_GROUP T2 ON T2.ID=T1.groupId ")
		   .append("WHERE T1.memberId=? ");
		sql.append("UNION ALL ");
		sql.append("SELECT T3.receiver id,T4.nickname name,'1' type,T4.url ")
		   .append("FROM RC_PRIVATE T3 ")
		   .append("INNER JOIN USER_INFO T4 ON T4.ID=T3.receiver ")
		   .append("WHERE T3.sender = ? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.BIGINT, userId);
		paramInfo.setTypeAndData(Types.BIGINT, userId);
		return messDao.getRecords(sql.toString(), paramInfo, Message.class);
	}
	
	/**
	 * 根据群组id获取群成员
	 * @param groupId
	 * @return
	 */
	public List<Member> listMembers(String groupId)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT T1.memberId,T2.nickname,T2.url ")
		   .append("FROM RC_GROUP_MEMBER T1 ")
		   .append("INNER JOIN USER_INFO T2 ON T2.ID=T1.memberId ")
		   .append("WHERE T1.groupId=? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.BIGINT, groupId);
		return memberDao.getRecords(sql.toString(), paramInfo, Member.class);
	}
	
	/**
	 * 根据群组id获取群成员
	 * @param groupId
	 * @return
	 */
	public Map<String, List<Member>> listMembers(List<String> ids)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT T1.memberId id,T2.nickname,T2.url,T1.groupId ")
		   .append("FROM RC_GROUP_MEMBER T1 ")
		   .append("INNER JOIN USER_INFO T2 ON T2.ID=T1.memberId ")
		   .append("WHERE 1=1 ");
		ParamInfo paramInfo = new ParamInfo();
		if(ids != null && ids.size() > 0)
		{
			sql.append("AND T1.groupId in (");
			for(String id : ids)
			{
				sql.append("?,");
				paramInfo.setTypeAndData(Types.BIGINT, id);
			}
			sql.replace(sql.length() - 1, sql.length(), ")");
		}
		sql.append(" order by T1.id DESC ");
		List<Member> members = memberDao.getRecords(sql.toString(), paramInfo, Member.class);
		Map<String, List<Member>> m = new HashMap<String, List<Member>>();
		if(members != null)
		{
			List<Member> l = null;
			String id = null;
			for(Member member : members)
			{
				if(Utils.isEmpty(member.getUrl()))
					continue;
				id = member.getGroupId();
				if(m.containsKey(id))
				{
					l = m.get(id);
					if(l.size() >= 15)
						continue;
				}
				else
					l = new ArrayList<Member>();
				l.add(member);
				m.put(id,l);
			}
		}
		return m;
	}
	
	/**
	 * 创建私聊
	 * @param sender
	 * @param receiver
	 */
	public void createPrivateChart(String sender,String receiver)
	{
		String sql = "INSERT INTO RC_PRIVATE (sender,receiver) VALUES (?,?)";
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.BIGINT, sender);
		paramInfo.setTypeAndData(Types.BIGINT, receiver);
		memberDao.insertRecord(sql, paramInfo);
	}
	
	/**
	 * 删除私聊
	 * @param sender
	 * @param receiver
	 */
	public void delPrivateChart(String sender,String receiver)
	{
		String sql = "DELETE FROM RC_PRIVATE WHERE sender=? AND receiver=? ";
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.BIGINT, sender);
		paramInfo.setTypeAndData(Types.BIGINT, receiver);
		memberDao.delRecord(sql, paramInfo);
	}
	
	/**
	 * 判断用户是否已经加入活动，已加入返回true
	 * @param activityId
	 * @param userId
	 * @return
	 */
	public boolean isJoin(String activityId,String userId)
	{
		if(Utils.isEmpty(userId))
			return false;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT count(1) ")
		   .append("FROM RC_GROUP_MEMBER ")
		   .append("where groupId=? and memberId=? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.BIGINT, activityId);
		paramInfo.setTypeAndData(Types.BIGINT, userId);
		long count = memberDao.getCount(sql.toString(), paramInfo);
		if(count > 0)
			return true;
		return false;
	}
	
	/**
	 * 新增活动
	 * @param act
	 */
	@Transactional
	public void insertActivity(Activity act,ChartGroup group)
	{
//		String sql = "INSERT INTO ACTIVITY_INFO (title,startTime,endTime,type,status) VALUES (?,?,?,?,?)";
//		ParamInfo paramInfo = new ParamInfo();
//		paramInfo.setTypeAndData(Types.VARCHAR, act.getTitle());
//		paramInfo.setTypeAndData(Types.BIGINT, act.getStartTime());
//		paramInfo.setTypeAndData(Types.BIGINT, act.getEndTime());
//		paramInfo.setTypeAndData(Types.CHAR, act.getType());
//		paramInfo.setTypeAndData(Types.CHAR, act.getStatus());
//		return activityDao.insertRecord(sql, paramInfo);
		
		long id = activityDao.insertRecord(act,"ACTIVITY_INFO");
		group.setId(id + "");
		groupDao.insertRecord(group, "RC_GROUP");
	}
	
	/**
	 * 更新活动
	 * @param act
	 */
	@Transactional
	public void updateActivity(Activity act,ChartGroup group)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE ACTIVITY_INFO SET title=?,startTime=?,endTime=?,type=? WHERE id=? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.VARCHAR, act.getTitle());
		paramInfo.setTypeAndData(Types.BIGINT, act.getStartTime());
		paramInfo.setTypeAndData(Types.BIGINT, act.getEndTime());
		paramInfo.setTypeAndData(Types.CHAR, act.getType());
		paramInfo.setTypeAndData(Types.BIGINT, act.getId());
		activityDao.updateRecord(sql.toString(), paramInfo);
		
		sql = new StringBuilder();
		sql.append("UPDATE RC_GROUP SET groupName=?,groupInfo=? WHERE id=? ");
		paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.VARCHAR, group.getGroupName());
		paramInfo.setTypeAndData(Types.VARCHAR, group.getGroupInfo());
		paramInfo.setTypeAndData(Types.BIGINT, group.getId());
		groupDao.updateRecord(sql.toString(), paramInfo);
	}
	
	/**
	 * 删除活动
	 * @param id
	 */
	@Transactional
	public void delActivity(String id)
	{
		//删除融云群成员表数据
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM RC_GROUP_MEMBER WHERE groupId=? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.BIGINT, id);
		groupDao.updateRecord(sql.toString(), paramInfo);
		
		//删除融云群组表表数据
		sql = new StringBuilder();
		sql.append("DELETE FROM RC_GROUP WHERE id=? ");
		groupDao.updateRecord(sql.toString(), paramInfo);
				
		//删除活动主讲人表数据
		sql = new StringBuilder();
		sql.append("DELETE FROM  ACTIVITY_SPEAKER WHERE activityId=? ");
		activityDao.updateRecord(sql.toString(), paramInfo);
		
		//删除活动信息数据
		sql = new StringBuilder();
		sql.append("DELETE FROM ACTIVITY_INFO WHERE id=? ");
		activityDao.updateRecord(sql.toString(), paramInfo);
	}
	
	/**
	 * 活动发布
	 * @param id
	 */
	@Transactional
	public String publishActivity(String id)
	{
		List<Speaker> speakers = listSpeakers(id);
		if(speakers == null || speakers.size() == 0)
			return "请先添加活动主讲人!";
		List<String> userIds = new ArrayList<String>();
		for(Speaker speaker:speakers)
			userIds.add(speaker.getSpeakerId());
		ChartGroup group = getGroupById(id);
		if(group == null)
			return "活动对应群信息不存在!";
		if(!GloabConstant.ACTIVITY_STATUS_CG.equals(group.getStatus()))
			return "只能发布状态为草稿的活动!";
		
		Activity act = getActivityById(id);
		StringBuilder msg = new StringBuilder();
		if(Utils.isEmpty(act.getUrl()))
			msg.append("活动图片，");
		if(Utils.isEmpty(act.getShare_activity_image()))
			msg.append("活动分享图片，");
		if(Utils.isEmpty(group.getShare_group_image()))
			msg.append("群分享图片，");
		if(!Utils.isEmpty(msg.toString()))
		{
			return msg.replace(msg.length() - 1, msg.length(), "").append("还未上传!").toString();
		}
		
		RCUtils.createGroup(userIds, id, group.getGroupName());
		
		String sql = "INSERT INTO RC_GROUP_MEMBER (groupId,memberId) VALUES (?,?)";
		List<ParamInfo> paramList = new ArrayList<ParamInfo>();
		ParamInfo paramInfo = null;
		for(Speaker speaker:speakers)
		{
			paramInfo = new ParamInfo();
			paramInfo.setTypeAndData(Types.BIGINT, id);
			paramInfo.setTypeAndData(Types.BIGINT, speaker.getSpeakerId());
			paramList.add(paramInfo);
		}
		activityDao.batchExcute(sql, paramList);
		updateActivityStatus(id,GloabConstant.ACTIVITY_STATUS_WKS);
		return "";
	}
	
	/**
	 * 更新活动状态
	 * @param id
	 * @param status
	 */
	public void updateActivityStatus(String id,String status)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE ACTIVITY_INFO SET status=? WHERE id=? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.VARCHAR, status);
		paramInfo.setTypeAndData(Types.BIGINT, id);
		activityDao.updateRecord(sql.toString(), paramInfo);
	}
	
	/**
	 * 新增主讲人
	 * @param speaker
	 */
	public void insertSpeaker(Speaker speaker)
	{
		speakerDao.insertRecord(speaker, "ACTIVITY_SPEAKER");
	}
	
	/**
	 * 更新主讲人
	 * @param speaker
	 */
	public void updateSpeaker(Speaker speaker)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE ACTIVITY_SPEAKER SET remark=? WHERE id=? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.VARCHAR, speaker.getRemark());
		paramInfo.setTypeAndData(Types.BIGINT, speaker.getId());
		speakerDao.updateRecord(sql.toString(), paramInfo);
	}
	
	/**
	 * 根据id获取主讲人信息
	 * @param id
	 * @return
	 */
	public Speaker getSpeakerInfo(String id)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id,speakerId,remark FROM ACTIVITY_SPEAKER WHERE id=? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.BIGINT, id);
		return speakerDao.getRecord(sql.toString(), paramInfo, Speaker.class);
	}
	
	/**
	 * 删除主讲人
	 * @param id
	 */
	public void delSpeaker(String id)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM ACTIVITY_SPEAKER WHERE id=? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.BIGINT, id);
		groupDao.updateRecord(sql.toString(), paramInfo);
	}
	
	/**
	 * 上传图片
	 * @param id
	 * @param url
	 */
	public void uploadActivityImage(String id,String url,String uploadType)
	{
		StringBuilder sql = new StringBuilder();
		if("0".equals(uploadType))
			sql.append("UPDATE ACTIVITY_INFO SET url=? WHERE id=? ");
		else if("1".equals(uploadType))
			sql.append("UPDATE ACTIVITY_INFO SET share_activity_image=? WHERE id=? ");
		else if("2".equals(uploadType))
			sql.append("UPDATE RC_GROUP SET share_group_image=? WHERE id=? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.VARCHAR, url);
		paramInfo.setTypeAndData(Types.BIGINT, id);
		speakerDao.updateRecord(sql.toString(), paramInfo);
	}
	
	/**
	 * 根据活动ID获取其参加活动的次数
	 * @param userId
	 * @return
	 */
	public long getJoinActivityNum(String userId)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT count(1) FROM RC_GROUP_MEMBER WHERE memberId=? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.BIGINT, userId);
		return activityDao.getCount(sql.toString(), paramInfo);
	}
	
	/**
	 * 根据用户ID获取其咨询大拿的次数
	 * @param userId
	 * @return
	 */
	public long getAskGreaterNum(String userId)
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT count(DISTINCT receiver) FROM RC_PRIVATE WHERE sender=? ");
		ParamInfo paramInfo = new ParamInfo();
		paramInfo.setTypeAndData(Types.BIGINT, userId);
		return activityDao.getCount(sql.toString(), paramInfo ,0);
	}
}
