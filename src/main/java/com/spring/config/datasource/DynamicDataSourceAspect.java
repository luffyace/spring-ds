package com.spring.config.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.spring.util.ReflectHelper;

@Aspect
@Order(-10)
@Component
public class DynamicDataSourceAspect {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Before("@within(targetDataSource)||@annotation(targetDataSource)")
	public void changeDataSource(JoinPoint point, TargetDataSource targetDataSource) throws Throwable {
		if(null == targetDataSource)
			targetDataSource = (TargetDataSource) ReflectHelper.getClassAnnotation(TargetDataSource.class, point.getTarget());
		if(null != targetDataSource) {
			String dsId = targetDataSource.value();
			if (!DynamicDataSourceContextHolder.containsDataSource(dsId)) {
				logger.error("DataSource[{}]不存在，使用默认DataSource : {}", dsId, point.getSignature());
			} else {
				logger.info("Use DataSource : {} : {}", dsId, point.getSignature());
				DynamicDataSourceContextHolder.setDataSource(targetDataSource.value());
			}
		}
	}
	
	@After("@within(targetDataSource)||@annotation(targetDataSource)")
	public void restoreDataSource(JoinPoint point, TargetDataSource targetDataSource) {
		if(null == targetDataSource)
			targetDataSource = (TargetDataSource) ReflectHelper.getClassAnnotation(TargetDataSource.class, point.getTarget());
		if(null != targetDataSource) {
			logger.info("Revert DataSource : {} : {}", targetDataSource.value(), point.getSignature());
			DynamicDataSourceContextHolder.clearDataSource();
		}
	}
}
