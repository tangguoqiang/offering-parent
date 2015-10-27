package com.offering.utils;

import org.apache.log4j.Logger;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

/**
 * 极光推送服务
 * @author surfacepro3
 *
 */
public class JpushUtils {
	private final static Logger LOG = Logger.getLogger(JPushClient.class);

	private final static String MASTER_SECRET = "c0f0215ffaa9a3a841a890c7";
	private final static String APP_KEY = "3324b6b960334c3beee7acac";
	private static JPushClient jpushClient = null;
	static {
		jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, 3);
	}

	public static void notify(String content, String[] alias) {
		PushPayload pushPayload = PushPayload.newBuilder()
				.setPlatform(Platform.all())
				.setAudience(Audience.alias(alias))
				.setNotification(Notification.alert(content)).build();
		try {
			jpushClient.sendPush(pushPayload);
		} catch (APIConnectionException | APIRequestException e) {
			LOG.error(e);
		}
	}

	public static void notifyAll(String content) {
		try {
			jpushClient.sendPush(PushPayload.alertAll(content));
		} catch (APIConnectionException | APIRequestException e) {
			LOG.error(e);
		}
	}
	
	public static void sendMessage(String content) {
		try {
			Message mess = Message.newBuilder()
							.setMsgContent(content)
							.build();
			PushPayload pushPayload = PushPayload.newBuilder()
					.setPlatform(Platform.all())
					.setMessage(mess).build();
			jpushClient.sendPush(pushPayload);
		} catch (APIConnectionException | APIRequestException e) {
			LOG.error(e);
		}
	}

	public static void main(String[] args) {
		notify("测试",new String[]{"gtang"});
	}

	// private static PushPayload buildPushPayload()
	// {
	// return PushPayload.newBuilder()
	// .setPlatform(Platform.all())
	// .setAudience(Audience.alias("alias1",""))
	// .setNotification(Notification.alert(ALERT))
	// .build();
	// }
}
