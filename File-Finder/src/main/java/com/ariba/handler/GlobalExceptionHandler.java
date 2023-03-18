package com.ariba.handler;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Shyam
 *
 */
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	//@ExceptionHandler(value = Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody Exception handleException(Exception ex) {
		return new Exception(ex.getMessage());
	}

	@ExceptionHandler(Exception.class)
	public String handleException(HttpServletRequest request, Exception ex) {
		logger.info("Exception Occured:: URL=" + request.getRequestURL());
		return "search";
	}
}
