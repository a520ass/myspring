package org.hf.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.google.common.collect.Lists;
import com.hf.config.jpa.JpaConfig;
import com.hf.config.mongo.MongoConfig;
import com.hf.config.redis.RedisSentinelConfig;

@Configuration
//@ImportResource("classpath:/com/acme/properties-config.xml")	导入xml配置文件
//@PropertySource("classpath:application.properties")
@Import(value={MongoConfig.class,JpaConfig.class,RedisSentinelConfig.class})
public class AppConfig {
	
	@Bean
	public EmailService emailService(){
		EmailService es=new EmailService();
		List<String> blackList=Lists.newArrayList();
		blackList.add("known.spammer@example.org");
		blackList.add("known.hacker@example.org");
		blackList.add("john.doe@example.org");
		es.setBlackList(blackList);
		return es;
	}
	
	@Bean
	public BlackListNotifier blackListNotifier(){
		BlackListNotifier bln=new BlackListNotifier();
		bln.setNotificationAddress("blacklist@example.org");
		return bln;
		
	}
}
