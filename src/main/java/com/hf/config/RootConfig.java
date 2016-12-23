package com.hf.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

//@Configuration
@ComponentScan(basePackages = { GlobalConfig.BASEPACKAGES }, excludeFilters = { 
		@Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class),
		@Filter(type = FilterType.ANNOTATION, value = Controller.class),
		@Filter(type = FilterType.ANNOTATION, value = ControllerAdvice.class) })
//@Import(value={JpaConfig.class,RedisSentinelConfig.class,MongoConfig.class})
@PropertySource("classpath:app.conf")
@EnableAspectJAutoProxy(proxyTargetClass=true)
@EnableRetry	//启用spring retry
@Lazy(true)
public class RootConfig {
	
}
