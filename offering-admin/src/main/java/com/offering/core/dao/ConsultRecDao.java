package com.offering.core.dao;

import java.util.List;

import com.offering.bean.sys.PageInfo;
import com.offering.bean.user.ConsultRecord;

/**
 * 咨询记录
 * @author surfacepro3
 *
 */
public interface ConsultRecDao extends BaseDao<ConsultRecord>{

	/**
	 * 查询咨询历史
	 * @param cr
	 * @param page
	 * @return
	 */
	List<ConsultRecord> listConsultRecs(ConsultRecord cr,PageInfo page);
	long getConsultCount(ConsultRecord cr);
	
	/**
	 * 更新咨询记录状态
	 * @param id
	 * @param status
	 */
	void updateStatus(String id,String status);
	
	/**
	 * 获取正在进行中的咨询
	 * @return
	 */
	List<ConsultRecord> listRecs_in();
}
