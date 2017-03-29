package com.spring.test.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.spring.test.service.TPlatformChannelService;
import com.spring.test.service.TPlatformInfoTypeService;

@Component
@Configurable
@EnableScheduling
public class TestTask {
	@Autowired
	private TPlatformInfoTypeService tPlatformInfoTypeService; 
	@Autowired
	private TPlatformChannelService tPlatformChannelService; 
	
	//@Scheduled(cron = "*/5 * * * * * ")
	public synchronized void testTaskLog(){
		tPlatformInfoTypeService.getNormalType();
		tPlatformChannelService.findByOid("8a9b1188598c660e01599b52caa90001");
	}
}
