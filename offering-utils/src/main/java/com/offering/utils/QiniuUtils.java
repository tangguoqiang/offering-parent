package com.offering.utils;

import java.io.File;

import org.apache.log4j.Logger;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

/**
 * 七牛存储共通类
 * @author surfacepro3
 *
 */
public class QiniuUtils {
	
	private final static Logger LOG = Logger.getLogger(QiniuUtils.class);
	
	private final static String AK = "dpEdYwYE1wM3nQhiCsAYjFdrvz7TDxm7B6Ex0ERX";
	private final static String SK = "_JArBlnqSnjVaEHQ1pBzEs9WvcIvchIfblZaPxpy";
	
	private final static Auth AUTH = Auth.create(AK, SK);
	
	private final static String BUCKET = "offering";
	
	private final static String KEY_PREV = "test/";
	
	private static UploadManager uploadManager = new UploadManager();
	
	private QiniuUtils(){
		
	}
	
	private static String getUpToken(){
		return AUTH.uploadToken(BUCKET);
	}
	
	public static void upload(byte[] bytes,String fileName)
	{
		try {
			Response res = uploadManager.put(bytes, KEY_PREV + fileName, getUpToken());
			StringMap m = res.jsonToMap();
			System.out.println(m.formString());
		} catch (QiniuException e) {
			LOG.error(e);
		}
	}
	
	public static void upload(File f,String fileName)
	{
		try {
			Response res = uploadManager.put(f, KEY_PREV + fileName, getUpToken());
			StringMap m = res.jsonToMap();
			System.out.println(m.formString());
		} catch (QiniuException e) {
			LOG.error(e);
		}
	}
	
	public static void upload(String filePath,String fileName)
	{
		try {
			Response res = uploadManager.put(filePath, KEY_PREV + fileName, getUpToken());
			StringMap m = res.jsonToMap();
			System.out.println(m.formString());
		} catch (QiniuException e) {
			LOG.error(e);
		}
	}
	
	public static void main(String[] args) {
		upload("C:\\Users\\surfacepro3\\Desktop\\test.jpg", "test/test.jpg");
	}
	
}
