package com.emlakjet.ismaildemirler.productservice.aop.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.emlakjet.ismaildemirler.productservice.util.logging.LogHelper;

import lombok.RequiredArgsConstructor;
import static com.emlakjet.ismaildemirler.productservice.util.StreamUtils.getStringFromStream;

@Aspect
@Order(0)
@Configuration
@RequiredArgsConstructor
public class LoggingAspect {
	
	private final LogHelper logHelper;
	
	@Around(value = "com.emlakjet.ismaildemirler.productservice.aop.logging.LoggingPointcut.mainPointcut()")
	public Object generalAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
		
		final Logger classLogger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
		if(!classLogger.isDebugEnabled()) {
			return joinPoint.proceed();
		}
		
		try {
			long startTime = System.nanoTime();
			Object result = joinPoint.proceed();				
			long endTime = System.nanoTime();		
			
			logHelper.logDebug(
					classLogger, 
					joinPoint.getTarget().getClass().getName(), 
					((MethodSignature)joinPoint.getSignature()).getMethod().getName(), 
					getStringFromStream(joinPoint.getArgs()), 
					result,
					endTime - startTime
			);
			return result;
			
		} catch (Exception ex) {
			 
			logHelper.logError(
					classLogger, 
					joinPoint.getTarget().getClass().getName(), 
					((MethodSignature)joinPoint.getSignature()).getMethod().getName(), 
					getStringFromStream(joinPoint.getArgs()), 
					ex
			);
			throw new Exception(ex.getMessage());
		}
	}
}