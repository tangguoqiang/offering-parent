package com.offering.utils;

import java.util.Arrays;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.push.model.notification.Notification.Builder;

/**
 * 极光推送服务
 * @author surfacepro3
 *
 */
public class JpushUtils {
	private final static Logger LOG = Logger.getLogger(JPushClient.class);

	private final static String MASTER_SECRET = "76371ebe38ae2d08f6cce656";
	private final static String APP_KEY = "c3a87d9dbef060026cfd5fb9";
	private static JPushClient jpushClient = null;
	
	private final static Queue<JpushMessage> queue = new LinkedBlockingQueue<JpushMessage>(1000);
	
	private final static String ALIAS_PREV = "userId_";
	
	private final static Thread thread = new JpushThread("Jpush");
	
	public enum JpushType{
		NOTIFY,MESSAGE;
	}
	static {
		jpushClient = new JPushClient(MASTER_SECRET, APP_KEY, 3);
	}
	
	private JpushUtils(){
		
	}
	
	public static void init(){
		thread.start();
	}
	
	public static void destroy(){
		while(!queue.isEmpty()){
			thread.interrupt();
		}
	}
	
	public static void sendMessage(String content, String[] alias,
			Map<String, String> extras,JpushType type) {
		JpushMessage mess = new JpushMessage(content, alias, extras,type);
		LOG.info("新增推送消息:" + mess);
		queue.add(mess);
	}
	
	static class JpushMessage{
		String content;
		String[] alias;
		Map<String, String> extras;
		JpushType type;
		
		public JpushMessage(String content, String[] alias,
				Map<String, String> extras,JpushType type) {
			this.content = content;
			this.extras = extras;
			this.type = type;
			
			setAlias(alias);
		}
		
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String[] getAlias() {
			return alias;
		}
		public void setAlias(String[] alias) {
			if(alias != null){
				String[] tmpArr = new String[alias.length];
				int i = 0;
				for(String alia : alias){
					tmpArr[i++] = ALIAS_PREV + alia;
				}
				this.alias = tmpArr;
			}else{
				this.alias = null;
			}
		}
		public Map<String, String> getExtras() {
			return extras;
		}
		public void setExtras(Map<String, String> extras) {
			this.extras = extras;
		}
		public JpushType getType() {
			return type;
		}
		public void setType(JpushType type) {
			this.type = type;
		}
		
		@Override
		public String toString() {
			return "content:" + content + ",alias:" + Arrays.toString(alias) 
					+ ",type:" + type.name();
		}
	}
	
	static class JpushThread extends Thread{
		
		public JpushThread(String name){
			super(name);
		}
		
		@Override
		public void run() {
			JpushMessage mess = null;
			while(true){
				if(!queue.isEmpty()){
					mess = queue.poll();
					synchronized (queue) {
						LOG.info("推送消息(begin) :" + mess);
						long startTime = System.currentTimeMillis();
						send(mess);
						LOG.info("推送消息(end) :" + mess + ",耗时:" + (System.currentTimeMillis() - startTime));
					}
				}
			}
		}
		
		private void send(JpushMessage mess) {
			try {
				cn.jpush.api.push.model.PushPayload.Builder builder = PushPayload.newBuilder();
				switch (mess.getType()) {
					case NOTIFY:
						builder.setNotification(buildNotification(mess.getContent(),mess.getExtras()));
						break;
					case MESSAGE:
						builder.setMessage(buildMessage(mess.getContent(), mess.getExtras()));
						break;
					default:
						break;
				}
				
				builder.setPlatform(Platform.all());
				builder.setOptions(Options.newBuilder()
                        .setApnsProduction(true)
                        .build());
				String[] alias = mess.getAlias();
				if(alias != null && alias.length > 0)
					builder.setAudience(Audience.alias(alias));
				else{
					builder.setAudience(Audience.all());
				}
				PushPayload pushPayload = builder.build();
				jpushClient.sendPush(pushPayload);
			} catch (APIConnectionException | APIRequestException e) {
				LOG.error(e.getMessage());
			}
		}
		
		private Notification buildNotification(String content,
				Map<String, String> extras){
			Builder  builder = Notification.newBuilder();
			builder.addPlatformNotification(
					IosNotification.newBuilder()
					.setAlert(content)
					.addExtras(extras)
					.build());
			builder.addPlatformNotification(
					AndroidNotification.newBuilder()
					.setAlert(content)
					.addExtras(extras)
					.build());
			return builder.build();
		}
		
		private Message buildMessage(String content,Map<String, String> extras){
			Message mess = Message.newBuilder()
					.setMsgContent(content)
					.addExtras(extras)
					.build();
			return mess;
		}
	}
	
	public static void main(String[] args) {
		JpushUtils.init();
//		for(int i=0;i<20;i++){
			JpushUtils.sendMessage("aaa" , new String[]{ALIAS_PREV+202}, null, JpushType.NOTIFY);
//		}
	}
}
