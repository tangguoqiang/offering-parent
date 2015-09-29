package com.offering.core.service;

import java.util.List;

import com.offering.bean.AppVersion;
import com.offering.bean.Comcode;
import com.offering.bean.School;
import com.offering.bean.Suggest;

public interface SystemService {

	List<School> listSchools(String province);
	
	List<Comcode> getComcodeByGroup(String group);
	
	void insertSuggest(Suggest s);
	
	AppVersion getCurrentVersion(String deviceType);
}
