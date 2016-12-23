package com.hf.config.rpc.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpComponentsHttpInvokerRequestExecutor;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.remoting.httpinvoker.HttpInvokerRequestExecutor;

import com.hf.config.GlobalConfig;
import com.hf.config.rpc.AccountService;

@Configuration
public class HttpInvokerClientConfig {
	
	@Bean
	public HttpInvokerProxyFactoryBean httpInvokerProxyFactoryBean(){
		HttpInvokerProxyFactoryBean proxy = new HttpInvokerProxyFactoryBean();
		proxy.setServiceUrl(GlobalConfig.CTXLOCAL+"/httpinvokerrpc.service");
		proxy.setServiceInterface(AccountService.class);
		return proxy;
	}
	
	/**
	 * 默认HttpInvokerProxy使用了JDK的HTTP功能，也可以使用Apache的HttpComponents客户端来设置httpInvokerRequestExecutor属性  
	 * @return
	 */
	//@Bean
	public HttpInvokerRequestExecutor httpInvokerRequestExecutor(){
		HttpComponentsHttpInvokerRequestExecutor executor = new HttpComponentsHttpInvokerRequestExecutor();
		executor.setConnectTimeout(3*1000);
		executor.setReadTimeout(60*1000);
		return executor;
	}
}
