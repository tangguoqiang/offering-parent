package com.offering.redis;

public interface RedisOp {

	/**
	 * 设置值接口  
	 * 		接口1：普通设值  
	 * 		接口2：设置值过期时间(毫秒)  
	 * 		接口3：设置对象为值
	 * @param key
	 * @param value
	 */
	void set(String key,String value);
	void set(String key,String value,long timeOut);
	<T> void set(String key,T t,Class<T> cls);
	<T> void set(String key,T t,Class<T> cls,long timeOut);
	
	String get(String key);
	<T> T get(String key,Class<T> cls);
	
	void delete(String key);
	
	void increase(String key,long step);
}
