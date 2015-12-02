package com.offering.redis;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Hello world!
 *
 */
public class App {
	
	public static void main(String[] args) {
//		ApplicationContext context = new ClassPathXmlApplicationContext(
//				"classpath:applicationContext.xml");
//		StringRedisTemplate redisTemplate = (StringRedisTemplate) context
//				.getBean("redisTemplate");
//		
//		HashOperations<String, String, String>  hashOp = redisTemplate.opsForHash();
//		for(Entry<String, String> entry : hashOp.entries("user").entrySet())
//		{
//			System.out.println(entry.getKey() + ":" + entry.getValue());
//		}
//		
//		ValueOperations<String, String> vp = redisTemplate.opsForValue();
//		System.out.println(vp.get("test"));
//		RedisOp redisOp = (RedisOp) context.getBean("redisOp");
		//redisOp.set("test1", "111");
//		TestBean bean = new TestBean();
//		bean.setId("111");
//		bean.setName("test");
//		redisOp.set("testBean", bean,TestBean.class);
//		System.out.println(redisOp.get("testBean1", TestBean.class));
//		redisOp.delete("user");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		System.out.println(sdf.format(new Date()));;
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, -2);
		System.out.println(sdf.format(cal.getTime()));
	}
	
	public static class TestBean{
		private String id;
		private String name;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		@Override
		public String toString() {
			return "id:" + id + ",name:" + name;
		}
	}
}
