package com.offering.core.service;

import java.util.List;

import com.offering.bean.sys.AppVersion;
import com.offering.bean.sys.Comcode;
import com.offering.bean.sys.School;
import com.offering.bean.sys.Suggest;

public interface SystemService {

	List<School> listSchools(String province);
	
	List<Comcode> getComcodeByGroup(String group);
	
	void insertSuggest(Suggest s);
	
	AppVersion getCurrentVersion(String deviceType);
}
