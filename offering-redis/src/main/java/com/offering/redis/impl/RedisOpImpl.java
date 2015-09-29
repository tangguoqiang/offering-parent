package com.offering.redis.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.offering.redis.RedisOp;

@Repository
public class RedisOpImpl implements RedisOp{

	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Override
	public void set(String key,String value) {
		set(key, value, 0);
	}
	
	@Override
	public void set(String key, String value, long timeOut) {
		ValueOperations<String, String> vp = redisTemplate.opsForValue();
		if(timeOut == 0)
			vp.set(key, value);
		else
			vp.set(key, value, timeOut, TimeUnit.MILLISECONDS);
	}

	@Override
	public <T> void set(String key,T t,Class<T> cls) {
		set(key, t, cls, 0);
	}
	
	@Override
	public <T> void set(String key, T t, Class<T> cls, long timeOut) {
		HashOperations<String, String, Object>  hashOp = redisTemplate.opsForHash();
		Map<String, Object> valueMap = new HashMap<String, Object>();
		Field[] fields = cls.getDeclaredFields();
		String name = null,methodName = null;
		Object value = null;
		Method m = null;
		for(Field f : fields){
			name = f.getName();
			if("serialVersionUID".equals(name))
				continue;
			methodName = "get" + name.substring(0,1).toUpperCase() + name.substring(1);
			try {
				m = cls.getMethod(methodName);
				value = m.invoke(t);
				if(value != null)
					valueMap.put(name, value);
			} catch (NoSuchMethodException | SecurityException 
					| IllegalAccessException | IllegalArgumentException 
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		
		
		hashOp.putAll(key, valueMap);
		if(timeOut != 0)
			redisTemplate.expire(key, timeOut, TimeUnit.MILLISECONDS);
	}

	@Override
	public String get(String key) {
		ValueOperations<String, String> vp = redisTemplate.opsForValue();
		return vp.get(key);
	}

	@Override
	public <T> T get(String key, Class<T> cls) {
		HashOperations<String, String, Object>  hashOp = redisTemplate.opsForHash();
		Map<String, Object> valueMap = hashOp.entries(key);
		if(valueMap != null && !valueMap.isEmpty())
		{
			T t = null;;
			try {
				t = cls.newInstance();
				Field[] fields = cls.getDeclaredFields();
				String name = null,methodName = null;
				Method m = null;
				for(Entry<String, Object> entry : valueMap.entrySet())
				{
					name = entry.getKey();
					methodName = "set" + name.substring(0,1).toUpperCase() + name.substring(1);
					for(Field field : fields)
					{
						if(name != null && name.equals(field.getName())){
							methodName = "set" + name.substring(0,1).toUpperCase() + name.substring(1);
							m = cls.getMethod(methodName, field.getType());
							m.invoke(t, entry.getValue());
							break;
						}
					}
				}
			} catch (InstantiationException | IllegalAccessException 
					| IllegalArgumentException | InvocationTargetException 
					| NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
			return t;
		}
		return null;
	}

	@Override
	public void delete(String key) {
		redisTemplate.delete(key);
	}
	
	public void increase(String key,long step)
	{
		ValueOperations<String, String> vp = redisTemplate.opsForValue();
		vp.increment(key, step);
	}
}
