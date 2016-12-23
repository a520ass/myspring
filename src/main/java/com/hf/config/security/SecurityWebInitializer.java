package com.hf.config.security;

import org.springframework.core.annotation.Order;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

@Order(101)
public class SecurityWebInitializer extends AbstractSecurityWebApplicationInitializer {

	public SecurityWebInitializer() {
		super();
	}
	
}
