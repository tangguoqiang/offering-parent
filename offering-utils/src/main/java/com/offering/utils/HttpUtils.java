package com.offering.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

public class HttpUtils {
	
	private final static Logger LOG = Logger.getLogger(HttpUtils.class);
	
//	private final static String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

	public static void post(String url,Map<String, String> requestParams){
		LOG.info("请求路径:" + url);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url); 
		if(requestParams != null){
			// 创建参数队列  
		    List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
			for(Entry<String, String> entry : requestParams.entrySet()){
				formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));  
			}
			UrlEncodedFormEntity reqEntity;
			try {
				reqEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
				post.setEntity(reqEntity);
				
			} catch (UnsupportedEncodingException e) {
				LOG.error(e);
			}  
		}
		
		try {
			HttpResponse response = httpclient.execute(post);
			LOG.info("请求返回码:" + response.getStatusLine().getStatusCode());
			LOG.info("请求结果:" + EntityUtils.toString(response.getEntity(), "UTF-8"));
		} catch (IOException e) {
			LOG.error(e);
		}
	}
	
	/**
	 * 以xml的形式请求
	 * @param url
	 * @param requestParams
	 */
	public static void xmlPost(String url,Map<String, String> requestParams){
		LOG.info("请求路径:" + url);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url); 
		if(requestParams != null){
			// 创建参数队列  
		    StringBuilder str = new StringBuilder();
		    String key = null;
		    str.append("<xml>");
			for(Entry<String, String> entry : requestParams.entrySet()){
				key = entry.getKey();
				str.append("<").append(key).append(">")
				   .append(entry.getValue())
				   .append("</").append(key).append(">");
			}
			str.append("</xml>");
			StringEntity reqEntity = new StringEntity(str.toString(), "UTF-8");
			post.setEntity(reqEntity);
		}
		
		try {
			HttpResponse response = httpclient.execute(post);
			LOG.info("请求返回码:" + response.getStatusLine().getStatusCode());
			LOG.info("请求结果:" + EntityUtils.toString(response.getEntity(), "UTF-8"));
		} catch (IOException e) {
			LOG.error(e);
		}
	}
}
