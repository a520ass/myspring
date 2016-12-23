package org.springframework.security.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class CustomAuthenticationEventPublisher implements AuthenticationEventPublisher{
	
	private ApplicationContext applicationContext;
	private static final Logger log =LoggerFactory.getLogger(CustomAuthenticationEventPublisher.class);
	
	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		//applicationContext.publishEvent();
		log.info("认证成功。。");
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception,
			Authentication authentication) {
		
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

}
