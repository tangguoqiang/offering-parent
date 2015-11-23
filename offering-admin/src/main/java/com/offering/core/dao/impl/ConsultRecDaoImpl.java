package com.offering.core.dao.impl;

import java.sql.Types;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.offering.bean.sys.ParamInfo;
import com.offering.bean.user.ConsultRecord;
import com.offering.constant.DBConstant;
import com.offering.constant.GloabConstant;
import com.offering.core.dao.ConsultRecDao;

/**
 * 咨询记录dao实现
 * @author surfacepro3
 *
 */
@Repository(value="ConsultRecDao")
public class ConsultRecDaoImpl extends BaseDaoImpl<ConsultRecord> implements ConsultRecDao{

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
		ParamInfo pi = new ParamInfo(2);
		pi.setTypeAndData(Types.CHAR, GloabConstant.CONSULT_STATUS_0);
		return getRecords(sql.toString(), pi, ConsultRecord.class);
	}
}
