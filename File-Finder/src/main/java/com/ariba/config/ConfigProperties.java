package com.ariba.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author Shyam
 *
 */
@Component
@PropertySource("classpath:application.properties")
public class ConfigProperties {

	/**
	 * Environment
	 */
	@Autowired
	private Environment env;


	/**
	 * @param configKey
	 * @return
	 */
	public String getConfigValue(String configKey) {
		// TODO Auto-generated method stub
		return env.getProperty(configKey);
	}
}