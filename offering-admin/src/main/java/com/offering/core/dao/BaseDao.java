package com.offering.core.dao;

import java.util.List;

import com.offering.bean.PageInfo;
import com.offering.bean.ParamInfo;

public interface BaseDao<T> {

	/**
	 * 获取总数 暂不支持with
	 * 
	 * @param sql
	 * @return
	 */
	public long getCount(String sql, ParamInfo paramInfo,int type);
	
	/**
	 * 获取总数 暂不支持with
	 * @param sql
	 * @return
	 */
	public long getCount(String sql,ParamInfo paramInfo);
	
	/**
	 * 查询多条记录，参数可以为null
	 * @param sql
	 * @param paramInfo
	 * @param cls
	 * @return
	 */
	public List<T> getRecords(String sql,ParamInfo paramInfo,Class<T> cls);
	
	/**
	 * 查询多条记录，带分页
	 * @param sql
	 * @param paramInfo
	 * @param page
	 * @param cls
	 * @return
	 */
	public List<T> getRecords(String sql,ParamInfo paramInfo,PageInfo page,Class<T> cls);
	
	/**
	 * 查询一条记录
	 * @param sql
	 * @param paramInfo
	 * @param cls
	 * @return
	 */
	public T getRecord(String sql,ParamInfo paramInfo,Class<T> cls);
	
	/**
	 * 插入数据
	 * @param t
	 * @param tableName
	 */
	public long insertRecord(T t,String tableName);
	
	/**
	 * 插入数据
	 * @param sql
	 * @param paramInfo
	 * @return
	 */
	public long insertRecord(String sql,ParamInfo paramInfo);
	
	/**
	 * 更新数据
	 * @param sql
	 * @param paramInfo
	 */
	public void updateRecord(String sql,ParamInfo paramInfo);
	
	/**
	 * 删除一条记录
	 * @param sql
	 * @param paramInfo
	 */
	public void delRecord(String sql,ParamInfo paramInfo);
	
	/**
	 * 批量执行
	 * @param sql
	 * @param paramList
	 */
	public void batchExcute(String sql,List<ParamInfo> paramList);
}
