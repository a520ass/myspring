package com.hf.config.custom.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.HttpSessionCreatedEvent;
import org.springframework.stereotype.Component;

@Component
public class SessionCreatedListener implements ApplicationListener<HttpSessionCreatedEvent>{
	
	private static Logger logger = LoggerFactory
			.getLogger(MyServletListener.class);
	
	@Override
	public void onApplicationEvent(HttpSessionCreatedEvent event) {
		logger.info("HttpSessionCreatedEvent。。创建session");
	}

}
