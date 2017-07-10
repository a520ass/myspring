package com.hf.config.custom.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.stereotype.Component;

@Component
public class SessionDestroyedListener implements ApplicationListener<HttpSessionDestroyedEvent>{
	
	private static Logger logger = LoggerFactory
			.getLogger(MyServletListener.class);
	
	@Override
	public void onApplicationEvent(HttpSessionDestroyedEvent event) {
		logger.info("HttpSessionDestroyedEvent。。销毁session");
	}

}
