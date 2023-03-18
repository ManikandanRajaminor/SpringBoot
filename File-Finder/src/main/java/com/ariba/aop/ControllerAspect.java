package com.ariba.aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Shyam
 *
 */
@Aspect
@Component
public class ControllerAspect {

	/**
	 * mapper
	 */
	@Autowired
	private ObjectMapper mapper;

	/**
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(ControllerAspect.class);

	/**
	 * @param joinPoint
	 */
	@Before("execution(* com.ariba.*.*.*(..))")
	public void before(JoinPoint joinPoint) {
		// Advice
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		//RequestMapping mapping = signature.getMethod().getAnnotation(RequestMapping.class);

		try {
			Map<String, Object> parameters = getParameters(joinPoint);
			logger.info("==> method(s): {}, arguments: {} ", signature.getMethod().getName(), parameters);
		} catch (Exception e) {
			logger.error("Error Occurred in before", e);
		}
	}

	/**
	 * @param joinPoint
	 */
	@After(value = "execution(* com.ariba.controller.*.*(..))")
	public void after(JoinPoint joinPoint) {
		logger.info("after execution of {}", joinPoint);
	}

	/**
	 * @param joinPoint
	 * @param result
	 */
	@AfterReturning(value = "execution(* com.ariba.controller.*.*(..))", returning = "result")
	public void afterReturning(JoinPoint joinPoint, Object result) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		// RequestMapping mapping =
		// signature.getMethod().getAnnotation(RequestMapping.class);

		try {
			logger.info("<== method(s): {}, returning: {}", signature.getMethod().getName(), result);
		} catch (Exception e) {
			logger.error("Error Occurred in afterReturning ", e);
		}
	}

	/**
	 * @param joinPoint
	 * @return
	 */
	private Map<String, Object> getParameters(JoinPoint joinPoint) {
		CodeSignature signature = (CodeSignature) joinPoint.getSignature();

		HashMap<String, Object> map = new HashMap<>();

		String[] parameterNames = signature.getParameterNames();

		for (int i = 0; i < parameterNames.length; i++) {
			map.put(parameterNames[i], joinPoint.getArgs()[i]);
		}

		return map;
	}

}
