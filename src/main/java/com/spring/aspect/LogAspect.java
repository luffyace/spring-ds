package com.spring.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.spring.util.StringHelper;

@Aspect
@Order(-5)
@Component
public class LogAspect {
	private Logger logger =  LoggerFactory.getLogger(this.getClass());
	
	@Before("@within(logMsg)||@annotation(logMsg)")
	public void before(JoinPoint joinPoint, LogMsg logMsg) throws Throwable {
		if(null == logMsg)
			logMsg = getClassLogMsg(joinPoint);
		if(null != logMsg) {
			String desc = logMsg.desc();
			if(StringHelper.isEmpty(desc))
				desc = joinPoint.getSignature().getName();
			logger.info(">>>>>> start {}", desc);
		}
	}

	@After("@within(logMsg)||@annotation(logMsg)")
	public void end(JoinPoint joinPoint, LogMsg logMsg) {
		if(null == logMsg)
			logMsg = getClassLogMsg(joinPoint);
		if(null != logMsg) {
			String desc = logMsg.desc();
			if(StringHelper.isEmpty(desc))
				desc = joinPoint.getSignature().getName();
			logger.info(">>>>>> end {}" , desc);
		}
	}
	
	protected LogMsg getClassLogMsg(JoinPoint point){
		try {
			String targetName = point.getTarget().getClass().getName(); 
			Class targetClass = Class.forName(targetName);
			if(targetClass.isAnnotationPresent(LogMsg.class))
				return (LogMsg) targetClass.getAnnotation(LogMsg.class);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}
}
