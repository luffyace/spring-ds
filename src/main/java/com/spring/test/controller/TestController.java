package com.spring.test.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.spring.config.datasource.DynamicDataSourceContextHolder;
import com.spring.test.service.TPlatformChannelService;
import com.spring.test.service.TPlatformInfoTypeService;

@RestController
public class TestController {
	@Autowired
	private TPlatformInfoTypeService tPlatformInfoTypeService; 
	@Autowired
	private TPlatformChannelService tPlatformChannelService; 
	
	@RequestMapping(value="/test",method = {RequestMethod.GET,RequestMethod.POST})
	public String test(HttpServletResponse response) throws IOException{
		System.out.println(JSON.toJSONString(tPlatformInfoTypeService.getNormalType()));//ds1
		System.out.println(DynamicDataSourceContextHolder.getDataSource());
		System.out.println(JSON.toJSONString(tPlatformChannelService.findByOid("8a9b1188598c660e01599b52caa90001")));
		return "测试";
	}
}
