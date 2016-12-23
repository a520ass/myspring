package com.hf.config.rpc.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

import com.hf.config.rpc.AccountService;

@Configuration
public class RmiConfig {
	
	@Autowired AccountService accountService;
	
	/**
	 * spring封装的rmi导出器。接口不需要继承romote
	 * @return
	 */
	@Bean
	public RmiServiceExporter rmiServiceExporter(){
		RmiServiceExporter exporter = new RmiServiceExporter();
		exporter.setServiceName("AccountService");
		exporter.setService(accountService);
		exporter.setServiceInterface(AccountService.class);
		
		//exporter.setRegistryHost("127.0.0.1");
		exporter.setRegistryPort(1099);
		return exporter;
	}
}
