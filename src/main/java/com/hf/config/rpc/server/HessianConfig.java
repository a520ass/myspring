package com.hf.config.rpc.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;

import com.hf.config.rpc.AccountService;
import com.hf.config.rpc.AccountServiceImpl;

@Configuration
public class HessianConfig {
	
	@Bean
	public AccountService accountService(){
		return new AccountServiceImpl();
	}
	
	//如果有多个服务需要导出，则多加几个HessianServiceExporter？
	//@Bean
	public HessianServiceExporter hessianServiceExporter(){
		HessianServiceExporter exporter = new HessianServiceExporter();
		exporter.setService(accountService());
		exporter.setServiceInterface(AccountService.class);
		return exporter;
	}
}
