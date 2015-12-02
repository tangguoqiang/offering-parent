package com.offering.core.service;

import java.util.List;

import com.offering.bean.sys.PageInfo;
import com.offering.bean.user.ConsultRecord;

/**
 * 咨询历史service
 * @author surfacepro3
 *
 */
public interface ConsultService {

	/**
	 * 查询咨询历史
	 * @param cr
	 * @param page
	 * @return
	 */
	List<ConsultRecord> listConsultRecs(ConsultRecord cr,PageInfo page);
	long getConsultCount(ConsultRecord cr);
}
