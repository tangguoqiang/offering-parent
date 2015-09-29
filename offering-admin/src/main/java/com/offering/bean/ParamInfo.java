package com.offering.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 参数
 * @author gtang
 *
 */
public class ParamInfo {
	
	/**
	 * 参数
	 */
	private List<String> paramList;
	
	/**
	 * 参数类型
	 */
	private List<Integer> typeList;
	
	public ParamInfo(){
		paramList = new ArrayList<String>();
		typeList = new ArrayList<Integer>();
	}

	public void setTypeAndData(int type,String param){
		paramList.add(param);
		typeList.add(type);
	}

	public List<String> getParamList() {
		return paramList;
	}

	public List<Integer> getTypeList() {
		return typeList;
	}
	
	public String[] getParams(){
		String[] params = new String[paramList.size()];
		return paramList.toArray(params);
	}
	
	public int[] getTypes(){
		int[] types = new int[typeList.size()];
		for(int i = 0,len = typeList.size();i < len;i++)
			types[i] = typeList.get(i);
		return types;
	}
}
