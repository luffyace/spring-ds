package com.spring.test.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Component;

import com.spring.test.entity.TPlatformChannel;

@Component
public interface TPlatformChannelDao extends JpaSpecificationExecutor<TPlatformChannel>, JpaRepository<TPlatformChannel, String>{
	public abstract TPlatformChannel findByOid(String oid);
}
