package com.hf.config.rpc.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.remoting.caucho.BurlapServiceExporter;
import org.springframework.remoting.caucho.HessianServiceExporter;

import com.hf.config.rpc.AccountService;

public class BurlapConfig {
	
	@Autowired AccountService accountService;
	
	@Bean
	public BurlapServiceExporter burlapServiceExporter(){
		BurlapServiceExporter exporter = new BurlapServiceExporter();
		exporter.setService(accountService);
		exporter.setServiceInterface(AccountService.class);
		return exporter;
	}
}
