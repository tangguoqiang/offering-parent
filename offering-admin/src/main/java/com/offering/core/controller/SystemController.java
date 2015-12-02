package com.offering.core.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.offering.core.service.SystemService;

/**
 * 系统功能入口
 * @author surfacepro3
 *
 */
@Controller
@RequestMapping(value ="/sys")
public class SystemController {

	@Autowired
	private SystemService sysService;
	
	/**
	 * 统计每天新增用户量
	 * @return
	 */
	@RequestMapping(value = "/countNewUsersByDay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> countNewUsersByDay(){
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("records", sysService.countNewUsersByDay());
		return m;
	}
}
