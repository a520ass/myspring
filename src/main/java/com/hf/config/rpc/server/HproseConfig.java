package com.hf.config.rpc.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.hprose.HproseHttpServiceExporter;

import com.hf.config.rpc.AccountService;

@Configuration
public class HproseConfig {
	
	@Autowired AccountService accountService;
	
	@Bean("/hproserpc1.service")
	public HproseHttpServiceExporter hproseHttpServiceExporter(){
		HproseHttpServiceExporter exporter = new HproseHttpServiceExporter();
		exporter.setService(accountService);
		exporter.setServiceInterface(AccountService.class);
		return exporter;
	}
}
