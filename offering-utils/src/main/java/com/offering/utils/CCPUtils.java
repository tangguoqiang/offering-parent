package com.offering.utils;

import java.util.HashMap;
import java.util.Set;

import com.cloopen.rest.sdk.CCPRestSDK;

/**
 * 容联接口
 * @author surfacepro3
 *
 */
public class CCPUtils {

	private final static String BASE_URL = "app.cloopen.com";
	private final static String PORT = "8883";
	private final static String ACCOUNT_ID = "8a48b5514e8a7522014e95a8e6b70db6";
	private final static String AUTH_TOKEN = "37a358acd0614e54a701f99f9fe3fa01";
	private final static String APP_ID = "8a48b5514e8a7522014e95aa68650db8";
	
	private final static String TEMPLATE_Id = "28642";
	private final static String EFFECT_TIME = "15";
	
	/**
	 * 发送模板信息
	 * @param phoneNum 手机号
	 * @param idCode 验证码
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String sendSMS(String phoneNum,String idCode)
	{
		 CCPRestSDK restAPI = new CCPRestSDK();
		// 初始化服务器地址和端口，沙盒环境配置成sandboxapp.cloopen.com，生产环境配置成app.cloopen.com，端口都是8883. 
		 restAPI.init(BASE_URL, PORT);
		// 初始化主账号名称和主账号令牌
		 restAPI.setAccount(ACCOUNT_ID, AUTH_TOKEN);
		// 初始化应用ID。如切换到生产环境，请使用自己创建应用的APPID
		 restAPI.setAppId(APP_ID);
		 HashMap<String, Object> result = restAPI.sendTemplateSMS(phoneNum, TEMPLATE_Id,
				 new String[]{idCode,EFFECT_TIME});
		 System.out.println("SDKTestVoiceVerify result=" + result); 
		 if("000000".equals(result.get("statusCode"))){
			 //正常返回输出data包体信息（map）
			 HashMap data = (HashMap) result.get("data");
			 Set<String> keySet = data.keySet();
			 for(String key:keySet){ 
				 Object object = data.get(key); 
				 System.out.println(key +" = "+object); 
			 }	
			 return "";
		 }else{
			 //异常返回输出错误码和错误信息
			 System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
			 return result.get("statusMsg").toString();
		 }
	}
}
