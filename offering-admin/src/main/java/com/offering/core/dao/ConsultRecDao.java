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
	
	/**
	 * 咨询历史纪录
	 * @param userId
	 * @param type
	 * @return
	 */
	List<ConsultRecord> consultHistory(String userId,String type,PageInfo pageInfo);
	
	/**
	 * 根据创建人和大拿获取资讯记录
	 * @param creater
	 * @param greaterId
	 * @return
	 */
	ConsultRecord getConsultByCreater(String creater,String greaterId);
}
