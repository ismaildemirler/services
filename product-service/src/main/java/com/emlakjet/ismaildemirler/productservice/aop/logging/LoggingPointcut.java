package com.emlakjet.ismaildemirler.productservice.aop.logging;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingPointcut {
	
	@Pointcut("execution(* com.emlakjet.ismaildemirler.productservice..*(..))")
	public void appPointcut() {
	}
	
	@Pointcut("within(@org.springframework.stereotype.Service *)")
	public void servicePointcut() {
	}
	
	@Pointcut("within(@org.springframework.stereotype.Repository *)")
	public void repositoryPointcut() {
	}
	
	@Pointcut("appPointcut() && (servicePointcut() || repositoryPointcut())")
	public void mainPointcut() {
	}
}
