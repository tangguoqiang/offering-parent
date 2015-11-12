package com.offering.core.dao.impl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.offering.annotation.Column;
import com.offering.bean.PageInfo;
import com.offering.bean.ParamInfo;
import com.offering.common.mapper.CommonRowMapper;
import com.offering.core.dao.BaseDao;
import com.offering.utils.Utils;

/**
 * 公共dao的实现
 * 
 * @author gtang
 * 
 */
@Repository
public class BaseDaoImpl<T> implements BaseDao<T> {

	@Autowired
	public JdbcTemplate jdbcTemplate;

	private static Log log = LogFactory.getLog(BaseDaoImpl.class);

	/**
	 * 获取总数 暂不支持with
	 * 
	 * @param sql
	 * @return
	 */
	public long getCount(String sql, ParamInfo paramInfo,int type) {
		log.debug(sql);
		if (paramInfo == null) {
			return jdbcTemplate.queryForObject(sql, Long.class);
		} else {
			return jdbcTemplate.queryForObject(sql, paramInfo.getParams(),
					paramInfo.getTypes(), Long.class);
		}
	}
	
	/**
	 * 获取总数 暂不支持with
	 * 
	 * @param sql
	 * @return
	 */
	public long getCount(String sql, ParamInfo paramInfo) {
		String[] arr = sql.split("[f,F][r,R][o,O][m,M]");
		if (arr != null && arr.length > 1) {
			if (sql.toLowerCase().indexOf("with") == -1) {
				sql = sql.replace(arr[0], "SELECT COUNT(1) ");
			} else {
				sql = "select count(1) from (" + sql + ")";
			}
		}
		sql = sql.replaceAll("limit \\d* offset \\d*", "");
		log.debug(sql);
		if (paramInfo == null) {
			return jdbcTemplate.queryForObject(sql, Long.class);
		} else {
			return jdbcTemplate.queryForObject(sql, paramInfo.getParams(),
					paramInfo.getTypes(), Long.class);
		}
	}

	public List<T> getRecords(String sql, ParamInfo paramInfo, Class<T> cls) {
		return getRecords(sql, paramInfo, null, cls);
	}

	public List<T> getRecords(String sql, ParamInfo paramInfo, PageInfo page,
			Class<T> cls) {
		if (page != null) {
			StringBuilder sb = new StringBuilder(sql);
			sb.append(" limit ").append(page.getPageSize()).append(" offset ")
					.append(page.getPageSize() * (page.getCurrentPage() -1));
			sql = sb.toString();
		}
		log.debug(sql);
		if (paramInfo == null)
			return jdbcTemplate.query(sql, new CommonRowMapper<T>(cls));
		return jdbcTemplate.query(sql, paramInfo.getParams(),
				paramInfo.getTypes(), new CommonRowMapper<T>(cls));
	}

	public T getRecord(String sql, ParamInfo paramInfo, Class<T> cls) {
		if (!StringUtils.isEmpty(sql)) {
			List<T> l = null;
			if (paramInfo == null) {
				l = jdbcTemplate.query(sql, new CommonRowMapper<T>(cls));
			} else {
				l = jdbcTemplate.query(sql, paramInfo.getParams(),
						paramInfo.getTypes(), new CommonRowMapper<T>(cls));
			}

			if (l != null && l.size() > 0)
				return l.get(0);
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public long insertRecord(final T t, String tableName) {
		final Class cls = t.getClass();
		final Field[] fields = cls.getDeclaredFields();
		final StringBuilder sql = new StringBuilder();
		final Map<String, Object> valueMap = new HashMap<String, Object>();
		sql.append(" INSERT INTO ").append(tableName).append(" (");
		Object value = null;
		String name;
		for (Field field : fields) {
			name = field.getName();
			if(field.getAnnotation(Column.class) == null)
				continue;
			try {
				value = cls.getMethod(
						"get" + name.substring(0, 1).toUpperCase()
								+ name.substring(1)).invoke(t);
			} catch (Exception e) {
				log.error(e);
				continue;
			}
			if(value != null && !Utils.isEmpty(value.toString()))
			{
				sql.append(field.getName()).append(",");
				valueMap.put(name, value);
			}
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		sql.append(" VALUES (");
		for (int i = 0, len = valueMap.size(); i < len; i++) {
			sql.append("?,");
		}
		sql.replace(sql.length() - 1, sql.length(), ")");
		log.debug(sql.toString());
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(
					Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql.toString(),
						PreparedStatement.RETURN_GENERATED_KEYS);  
				int i = 1;
				for (Field field : fields) {
					if(valueMap.containsKey(field.getName()))
						ps.setObject(i++, valueMap.get(field.getName()));
				}
	            return ps;  
			}
		}, keyHolder);  
	  
		if(keyHolder.getKey() == null)
			return -1;
	     return keyHolder.getKey().longValue();
	}
	
	/**
	 * 插入数据
	 * @param sql
	 * @param paramInfo
	 * @return
	 */
	public long insertRecord(final String sql,final ParamInfo paramInfo)
	{
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(
					Connection con) throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql,
						PreparedStatement.RETURN_GENERATED_KEYS);  
				int i = 1;
				if(paramInfo != null)
				{
					for(String param : paramInfo.getParams())
					{
						ps.setObject(i++, param);
					}
				}
	            return ps;  
			}
		}, keyHolder);  
	  
	     return keyHolder.getKey().longValue();
	}

	public void updateRecord(String sql, ParamInfo paramInfo) {
		if(paramInfo == null){
			jdbcTemplate.update(sql);
		}else{
			jdbcTemplate.update(sql, paramInfo.getParams(), paramInfo.getTypes());
		}
	}
	
	public void delRecord(String sql,ParamInfo paramInfo){
		updateRecord(sql, paramInfo);
	}
	
	public void batchExcute(String sql,List<ParamInfo> paramList){
		List<Object[]> l = new ArrayList<Object[]>();
		for(ParamInfo paramInfo : paramList){
			l.add(paramInfo.getParams());
		}
		jdbcTemplate.batchUpdate(sql, l);
	}
}
