package com.spring.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.aspect.LogMsg;
import com.spring.config.datasource.TargetDataSource;
import com.spring.test.dao.TPlatformInfoTypeDao;
import com.spring.test.entity.TPlatformInfoType;

@Service
public class TPlatformInfoTypeService {
	@Autowired
	private TPlatformInfoTypeDao tPlatformInfoTypeDao;
	
	@LogMsg(desc="测试--ds1")
	@TargetDataSource(value="ds1")
	public List<TPlatformInfoType> getNormalType(){
		return tPlatformInfoTypeDao.getNormalType();
	}
}
