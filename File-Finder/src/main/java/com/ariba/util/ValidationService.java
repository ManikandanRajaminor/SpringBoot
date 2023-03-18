package com.ariba.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ariba.config.ConfigProperties;
import com.ariba.model.Finder;

/**
 * @author Shyam
 *
 */
@Service
public class ValidationService {

	/**
	 * configProp
	 */
	@Autowired
	ConfigProperties configProp;

	/**
	 * @param finder
	 * @return
	 */
	public String validateUser(Finder finder) {
		String message = "";
		if (finder.getLine_of_business() == null || finder.getLine_of_business().trim().length() <= 0) {
			message = configProp.getConfigValue("LINE_OF_BUSINESS");
		}
		return message;
	}
}
