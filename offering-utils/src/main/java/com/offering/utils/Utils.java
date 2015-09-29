package com.offering.utils;

import static com.offering.constant.GloabConstant.CODE_ERR_OTHER;
import static com.offering.constant.GloabConstant.CODE_ERR_PARAM;
import static com.offering.constant.GloabConstant.CODE_SUCCESS;
import static com.offering.constant.GloabConstant.REP_CODE;
import static com.offering.constant.GloabConstant.REP_DATA;
import static com.offering.constant.GloabConstant.REP_MSG;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

/**
 * 字符串操作公共类
 * @author gtang
 *
 */
public class Utils {

	public static boolean isEmpty(String s){
		return s == null || "".equals(s);
	}
	
	public static String getUUID() {  
        UUID uuid = UUID.randomUUID();  
        String str = uuid.toString();  
        // 去掉"-"符号  
        String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);  
        return temp;  
    }  
	
	/**
	 * 检查请求参数
	 * @param req
	 * @param params
	 * @return
	 */
	public static Map<String, Object> checkParam(HttpServletRequest req,String[] params)
	{
		if(params == null)
			return null;
		StringBuilder msg = new StringBuilder();
		for(String param : params)
		{
			if(isEmpty(req.getParameter(param)))
				msg.append(param).append(",");
		}
		if(Utils.isEmpty(msg.toString()))
			return null;
		msg.replace(msg.length() - 1, msg.length(), ")");
		Map<String, Object> m = new HashMap<String, Object>();
		m.put(REP_CODE, CODE_ERR_PARAM);
		m.put(REP_MSG, "缺少参数(" + msg.toString());
		return m;
	}
	
	public static Map<String, Object> success(Map<String, Object> dataMap)
	{
		Map<String, Object> m = new HashMap<String, Object>();
		m.put(REP_CODE, CODE_SUCCESS);
		m.put(REP_MSG, "操作成功！");
		if(dataMap != null)
			m.put(REP_DATA, dataMap);
		return m;
	}
	
	public static Map<String, Object> failture(String msg)
	{
		Map<String, Object> m = new HashMap<String, Object>();
		m.put(REP_CODE, CODE_ERR_OTHER);
		m.put(REP_MSG, msg);
		return m;
	}
	
	/**
	 * 将bean转换成map
	 * @param l
	 * @param fields
	 * @param cls
	 * @return
	 */
	public static <T> List<Map<String, Object>> convertBeanToMap(List<T> oriList,
			String[] fields,Class<T> cls)
	{
		List<Map<String, Object>> returnList = new ArrayList<Map<String,Object>>();
		if(oriList != null && fields != null)
		{
			Map<String, Object> map = null;
			String methodName = null;
			Method m = null;
			for(T t : oriList)
			{
				map = new HashMap<String, Object>();
				for(String field : fields)
				{
					methodName = "get" + field.substring(0,1).toUpperCase() + field.substring(1);
					try {
						m = cls.getMethod(methodName);
						map.put(field, m.invoke(t));
					} catch (NoSuchMethodException | SecurityException  
							| IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						e.printStackTrace();
					}
				}
				returnList.add(map);
			}
		}
		return returnList;
	}
	
	/**
	 * 生成验证码
	 * @return
	 */
	public static String createIdCode()
	{
		Random rand = new Random();
		int n = rand.nextInt(10000);
		while(n <1000)
		{
			System.out.println(n);
			n = rand.nextInt(10000);
		}
			
		return  n + "";
	}
}
