package com.offering.core.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.offering.bean.sys.PageInfo;
import com.offering.bean.user.User;
import com.offering.core.service.UserService;
import com.offering.utils.Utils;

/**
 * 用户维护
 * @author surfacepro3
 *
 */
@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	/**
	 * 查询用户数据
	 * @param user
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/user/listUsers", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> listUsers(User user,PageInfo page){
		Map<String, Object> m = new HashMap<String, Object>();
		List<User> l = userService.listUsers(user, page);
		m.put("records", l);
		m.put("totalCount", userService.getUserCount(user));
		return m;
	}
	
	/**
	 * 查询用户数据
	 * @param userName
	 * @return
	 */
	@RequestMapping(value = "/user/getUserInfo", method = RequestMethod.POST)
	@ResponseBody
	public User getUserInfo(String id){
		return userService.getUserInfoById(id);
	}
	
	@RequestMapping(value = "/user/saveUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveUser(User user){
		Map<String, Object> m = new HashMap<String, Object>();
		boolean isExists = userService.isExistUser(user);
		if(isExists){
			m.put("success", false);
			m.put("msg", "用户名已经存在！");
		}else{
			if(Utils.isEmpty(user.getId())){
				//新增用户
				userService.insertUser(user);
			}else{
				//更新用户信息
				userService.updateUser(user);
			}
			m.put("success", true);
		}
		
		return m;
	}
	
	/**
	 * 删除用户
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/user/delUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delUser(String id){
		Map<String, Object> m = new HashMap<String, Object>();
		userService.delUser(id);
		m.put("success", true);
		
		return m;
	}
}
