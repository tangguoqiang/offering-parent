package com.offering.core.service;

import java.util.List;
import java.util.Map;

/**
 * 系统功能service
 * @author surfacepro3
 *
 */
public interface SystemService {

	/**
	 * 统计每天新增用户量
	 * @return
	 */
	List<Map<String, Object>> countNewUsersByDay();
}
