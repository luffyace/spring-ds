package com.spring.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.aspect.LogMsg;
import com.spring.test.dao.TPlatformChannelDao;
import com.spring.test.entity.TPlatformChannel;

@Service
public class TPlatformChannelService {
	@Autowired
	private TPlatformChannelDao tPlatformChannelDao;
	
	@LogMsg(desc="测试--默认ds")
	public TPlatformChannel findByOid(String oid){
		return tPlatformChannelDao.findByOid(oid);
	}
}
