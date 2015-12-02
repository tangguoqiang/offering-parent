package com.offering.core.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.offering.bean.sys.PageInfo;
import com.offering.bean.user.ConsultRecord;
import com.offering.core.service.ConsultService;

/**
 * 咨询历史
 * @author surfacepro3
 *
 */
@Controller
@RequestMapping(value ="/consult")
public class ConsultController {

	@Autowired
	private ConsultService consultService;
	
	/**
	 * 查询咨询历史
	 * @param cr
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/listConsultRecs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> listConsultRecs(ConsultRecord cr,PageInfo page){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("records", consultService.listConsultRecs(cr,page));
		m.put("totalCount", consultService.getConsultCount(cr));
		return m;
	}
}
