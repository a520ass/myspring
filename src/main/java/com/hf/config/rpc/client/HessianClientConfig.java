package com.hf.config.rpc.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;

import com.hf.config.GlobalConfig;
import com.hf.config.rpc.AccountService;

//@Configuration
public class HessianClientConfig {
	
	@Bean
	public HessianProxyFactoryBean hessianProxyFactoryBean(){
		HessianProxyFactoryBean proxy = new HessianProxyFactoryBean();
		proxy.setServiceUrl(GlobalConfig.CTXLOCAL+"/hessianrpc.service");
		proxy.setServiceInterface(AccountService.class);
		return proxy;
	}
}
