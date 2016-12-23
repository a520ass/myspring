package com.hf.config.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessageConfig {
	
	@Bean
	public MessageSource messageSource(){
		ResourceBundleMessageSource rbms=new ResourceBundleMessageSource();
		rbms.addBasenames("i18n/messages","org/springframework/security/messages");
		//rbms.addBasenames();
		return rbms;
	}
}
