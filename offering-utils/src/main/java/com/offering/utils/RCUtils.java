package com.offering.utils;

import io.rong.ApiHttpClient;
import io.rong.models.FormatType;
import io.rong.models.SdkHttpResult;
import io.rong.models.TxtMessage;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.offering.constant.GloabConstant;

/**
 * 融云接口
 * @author surfacepro3
 *
 */
public class RCUtils {

	private final static Logger LOG = Logger.getLogger(RCUtils.class);
	//测试环境pvxdm17jx829r/TYkLhjaVE7,正式环境uwd1c0sxd3lt1/Vm4N3C2JLcS1
	private final static String APP_KEY_TEST = "pvxdm17jx829r";
	private final static String APP_SECRET_TEST = "TYkLhjaVE7";
	
	private final static String APP_KEY_RPODUCT = "uwd1c0sxd3lt1";
	private final static String APP_SECRET_RPODUCT  = "Vm4N3C2JLcS1";
	
	private static String APP_KEY = "";
	private static String APP_SECRET = "";
	
	private final static int CODE_OK = 200;
	
	static{
		if(GloabConstant.CURRENT_MODEL.equals(GloabConstant.MODEL_PRODUCT)){
			//生产环境
			APP_KEY = APP_KEY_RPODUCT;
			APP_SECRET = APP_SECRET_RPODUCT;
		}else{
			APP_KEY = APP_KEY_TEST;
			APP_SECRET = APP_SECRET_TEST;
		}
	}
	
	/**
	 * 用户登录获取token
	 * @param userId
	 * @param userName
	 * @return
	 */
	public static String getToken(String userId,String userName,String url)
	{
		SdkHttpResult result = null;
		try {
			result = ApiHttpClient.getToken(APP_KEY, APP_SECRET, userId, userName,
					url, FormatType.json);
			LOG.info(result.getHttpCode());
			LOG.info(result);
			if(CODE_OK == result.getHttpCode())
			{
				JSONObject jsonObj = new JSONObject(result.getResult());
				if(jsonObj.has("token"))
					return jsonObj.getString("token");
			}
		} catch (Exception e) {
			LOG.error(e);
		}
		return null;
	}
	
	/**
	 * 私聊服务
	 * @param fromUserId
	 * @param toUserId
	 * @param msg
	 */
	public static void privateChat(String fromUserId,String toUserId,String msg)
	{
		List<String> toUserIds = new ArrayList<String>();
		toUserIds.add(toUserId);
		try {
			ApiHttpClient.publishMessage(APP_KEY, APP_SECRET, fromUserId, toUserIds, 
					new TxtMessage(msg), FormatType.json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 创建群组
	 * @param userIds
	 * @param groupId
	 * @param groupName
	 */
	public static void createGroup(List<String> userIds,String groupId,String groupName)
	{
		try {
			ApiHttpClient.createGroup(APP_KEY, APP_SECRET, 
					userIds, groupId, groupName, FormatType.json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 加入群组
	 * @param userId
	 * @param groupId
	 * @param groupName
	 */
	public static void joinGroup(String userId,String groupId,String groupName)
	{
		try {
			ApiHttpClient.joinGroup(APP_KEY, APP_SECRET, userId, groupId, groupName,  FormatType.json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 离开群组
	 * @param userId
	 * @param groupId
	 */
	public static void quitGroup(String userId,String groupId)
	{
		try {
			ApiHttpClient.quitGroup(APP_KEY, APP_SECRET, userId, groupId, FormatType.json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 解散群组
	 * @param userId
	 * @param groupId
	 */
	public static void dismissGroup(String userId,String groupId)
	{
		try {
			ApiHttpClient.dismissGroup(APP_KEY, APP_SECRET, userId, groupId, FormatType.json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void refreshUser(String userId,String userName,String url)
	{
		try {
			ApiHttpClient.refreshUser(APP_KEY, APP_SECRET, userId, userName, url, FormatType.json);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
