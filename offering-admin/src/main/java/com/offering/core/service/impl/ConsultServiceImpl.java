package com.offering.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.offering.bean.sys.PageInfo;
import com.offering.bean.user.ConsultRecord;
import com.offering.core.dao.ConsultRecDao;
import com.offering.core.service.ConsultService;

/**
 * 咨询历史service实现
 * @author surfacepro3
 *
 */
@Service
public class ConsultServiceImpl implements ConsultService {

	@Autowired
	private ConsultRecDao crDao;
	/**
	 * 查询咨询历史
	 * @param cr
	 * @param page
	 * @return
	 */
	public List<ConsultRecord> listConsultRecs(ConsultRecord cr,PageInfo page){
		return crDao.listConsultRecs(cr,page);
	}
	
	public long getConsultCount(ConsultRecord cr){
		return crDao.getConsultCount(cr);
	}
}
