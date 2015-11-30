package com.offering.common.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.offering.utils.Utils;

/**
 * 共通rowMapper
 * @author gtang
 *
 * @param <T>
 */
public class CommonRowMapper<T>  implements RowMapper<T>{

	private Class<T> cls;
	/**
	 * 私有无参构造函数
	 */
	protected CommonRowMapper(){
		
	}
	
	public CommonRowMapper(Class<T> cls){
		this.cls = cls;
	}
	@Override
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {
		T t = null;
		try {
			t = cls.newInstance();
			Field[] fields = cls.getDeclaredFields();
			String name = null,methodName = null,value = null;
			Method m = null;
			ResultSetMetaData rsmd= rs.getMetaData();
			for(int i = 1,len = rsmd.getColumnCount() + 1;i < len;i++){
				name = rsmd.getColumnLabel(i);
				for(Field f : fields){
					if(name != null && name.equals(f.getName())){
						methodName = "set" + name.substring(0,1).toUpperCase() + name.substring(1);
						m = cls.getMethod(methodName, f.getType());
						value = rs.getString(f.getName());
						if(!Utils.isEmpty(value))
							m.invoke(t, value);
						else
							m.invoke(t, "");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return t;
	}

}
