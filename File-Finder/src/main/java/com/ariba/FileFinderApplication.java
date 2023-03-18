package com.ariba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

/**
 * @author Shyam
 *
 */
@SpringBootApplication
@EnableEncryptableProperties
public class FileFinderApplication {

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FileFinderApplication.class);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(FileFinderApplication.class, args);
	}

}
