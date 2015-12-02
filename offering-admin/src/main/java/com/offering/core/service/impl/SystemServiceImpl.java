package com.offering.core.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.offering.core.dao.UserDao;
import com.offering.core.service.SystemService;
import com.offering.redis.RedisOp;
import com.offering.utils.Utils;

/**
 * 系统功能service实现
 * @author surfacepro3
 *
 */
@Service
public class SystemServiceImpl implements SystemService{

	@Autowired
	private RedisOp redisOp;
	
	@Autowired
	private UserDao userDao;
	
	/**
	 * 统计每天新增用户量
	 * @return
	 */
	public List<Map<String, Object>> countNewUsersByDay(){
		List<Map<String, Object>> l = new ArrayList<Map<String,Object>>();
		Map<String, Object> m = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		String count = null,day = null;
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, 1);
		
		for(int len = 10;len > 0;len --){
			m = new HashMap<String, Object>();
			cal.add(Calendar.DAY_OF_MONTH, -1);
			day = sdf.format(cal.getTime());
			count = redisOp.get(day + "_user");
			m.put("day", day);
			if(Utils.isEmpty(count))
				m.put("count", 0);
			else
				m.put("count", count);
			l.add(m);
		}
		m = new HashMap<String, Object>();
		m.put("day", "用户总数");
		m.put("count", userDao.getUserCount(null));
		l.add(m);
		return l;
	}
}
