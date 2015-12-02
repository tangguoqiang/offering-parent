package com.offering.core.dao.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.offering.bean.sys.PageInfo;
import com.offering.bean.sys.ParamInfo;
import com.offering.bean.user.ConsultRecord;
import com.offering.constant.DBConstant;
import com.offering.constant.GloabConstant;
import com.offering.core.dao.ConsultRecDao;
import com.offering.utils.Utils;

/**
 * 咨询记录dao实现
 * @author surfacepro3
 *
 */
@Repository(value="ConsultRecDao")
public class ConsultRecDaoImpl extends BaseDaoImpl<ConsultRecord> implements ConsultRecDao{

	/**
	 * 查询咨询历史
	 * @param cr
	 * @param page
	 * @return
	 */
	public List<ConsultRecord> listConsultRecs(ConsultRecord cr,PageInfo page){
		StringBuilder sql = new StringBuilder(128);
		sql.append("SELECT T1.id,T1.description,T1.createTime,T1.status,T4.title, ")
		   .append("T2.nickname createrName,T2.url createrUrl,T3.nickname greaterName,T3.url greaterUrl ")
		   .append("FROM ").append(DBConstant.CONSULT_RECORD).append(" T1 ")
		   .append("INNER JOIN ").append(DBConstant.USER_INFO)
		   .append(" T2 ON T2.id=T1.creater ")
		   .append("INNER JOIN ").append(DBConstant.USER_INFO)
		   .append(" T3 ON T3.id=T1.greaterId ")
		   .append("LEFT JOIN ").append(DBConstant.TOPIC_INFO)
		   .append(" T4 ON T4.id=T1.topicId ")
		   .append("WHERE 1=1 ");
		ParamInfo pi = new ParamInfo(1);
		if(!Utils.isEmpty(cr.getDescription())){
			sql.append("AND T1.description LIKE ? ");
			pi.setTypeAndData(Types.VARCHAR, "%" + cr.getDescription() + "%");
		}
		if(!Utils.isEmpty(cr.getGreaterName())){
			sql.append("AND T3.nickname LIKE ? ");
			pi.setTypeAndData(Types.VARCHAR, "%" + cr.getGreaterName() + "%");
		}
		if(!Utils.isEmpty(cr.getTitle())){
			sql.append("AND T4.title LIKE ? ");
			pi.setTypeAndData(Types.VARCHAR, "%" + cr.getTitle() + "%");
		}
		sql.append("ORDER BY T1.createTime DESC ");
		
		return getRecords(sql.toString(), pi,page, ConsultRecord.class);
	}
	
	public long getConsultCount(ConsultRecord cr){
		StringBuilder sql = new StringBuilder(128);
		sql.append("SELECT count(1) ")
		   .append("FROM ").append(DBConstant.CONSULT_RECORD).append(" T1 ")
		   .append("INNER JOIN ").append(DBConstant.USER_INFO)
		   .append(" T2 ON T2.id=T1.creater ")
		   .append("INNER JOIN ").append(DBConstant.USER_INFO)
		   .append(" T3 ON T3.id=T1.greaterId ")
		   .append("LEFT JOIN ").append(DBConstant.TOPIC_INFO)
		   .append(" T4 ON T4.id=T1.topicId ")
		   .append("WHERE 1=1 ");
		ParamInfo pi = new ParamInfo(1);
		if(!Utils.isEmpty(cr.getDescription())){
			sql.append("AND T1.description LIKE ? ");
			pi.setTypeAndData(Types.VARCHAR, "%" + cr.getDescription() + "%");
		}
		if(!Utils.isEmpty(cr.getGreaterName())){
			sql.append("AND T3.nickname LIKE ? ");
			pi.setTypeAndData(Types.VARCHAR, "%" + cr.getGreaterName() + "%");
		}
		if(!Utils.isEmpty(cr.getTitle())){
			sql.append("AND T4.title LIKE ? ");
			pi.setTypeAndData(Types.VARCHAR, "%" + cr.getTitle() + "%");
		}
		sql.append("ORDER BY T1.createTime DESC ");
		return getCount(sql.toString(), pi);
	}
	
	/**
	 * 更新咨询记录状态
	 * @param id
	 * @param status
	 */
	public void updateStatus(String id,String status){
		StringBuilder sql = new StringBuilder(128);
		sql.append("UPDATE ").append(DBConstant.CONSULT_RECORD)
		   .append(" SET status=? WHERE id=? ");
		ParamInfo pi = new ParamInfo(2);
		pi.setTypeAndData(Types.CHAR, status);
		pi.setTypeAndData(Types.BIGINT, id);
		updateRecord(sql.toString(), pi);
	}
	
	/**
	 * 获取正在进行中的咨询
	 * @return
	 */
	public List<ConsultRecord> listRecs_in(){
		StringBuilder sql = new StringBuilder(128);
		sql.append("SELECT T1.id,T1.greaterId,T1.topicId,T1.createTime,T1.creater,T1.chatId ")
		   .append("FROM ").append(DBConstant.CONSULT_RECORD).append(" T1 ")
		   .append("WHERE T1.status=? ");
		ParamInfo pi = new ParamInfo(1);
		pi.setTypeAndData(Types.CHAR, GloabConstant.CONSULT_STATUS_0);
		return getRecords(sql.toString(), pi, ConsultRecord.class);
	}
}
