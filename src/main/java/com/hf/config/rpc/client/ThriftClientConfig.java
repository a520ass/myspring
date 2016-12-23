package com.hf.config.rpc.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.thrift.ThriftHttpProxyFactoryBean;

import com.hf.config.GlobalConfig;
import com.hf.config.rpc.HelloWorld;

@Configuration
public class ThriftClientConfig {
	
	@Bean
	public ThriftHttpProxyFactoryBean thriftHttpProxyFactoryBean(){
		ThriftHttpProxyFactoryBean proxy = new ThriftHttpProxyFactoryBean();
		proxy.setServiceUrl(GlobalConfig.CTXLOCAL+"/thriftrpc.service");
		proxy.setServiceInterface(HelloWorld.Iface.class);
		return proxy;
	}
}
