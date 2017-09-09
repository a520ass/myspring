package com.hf.config.rpc.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import com.hf.config.GlobalConfig;
import com.hf.config.rpc.AccountService;

//@Configuration
public class RmiClientConfig {
	
	@Bean
	public RmiProxyFactoryBean rmiProxyFactoryBean(){
		RmiProxyFactoryBean proxy = new RmiProxyFactoryBean();
		proxy.setServiceUrl(GlobalConfig.RMICTXLOCAL+"/AccountService");
		proxy.setServiceInterface(AccountService.class);
		proxy.setRefreshStubOnConnectFailure(true);
		proxy.setLookupStubOnStartup(false);
		return proxy;
	}
}
