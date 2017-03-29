package com.spring.test.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.spring.test.entity.TPlatformInfoType;

public interface TPlatformInfoTypeDao extends JpaSpecificationExecutor<TPlatformInfoType>, JpaRepository<TPlatformInfoType, String>{
	@Query("select type from TPlatformInfoType type where type.status=1 order by type.sort")
	List<TPlatformInfoType> getNormalType();
}
