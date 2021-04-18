package com.url.shortner.config;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

	@Bean
	public UrlValidator urlValidator() {
		return new UrlValidator(new String[] {"http","https"}); // accepts only http and https but not ftp.
	}

}
