package com.hf.config.rpc.server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.thrift.ThriftHttpServiceExporter;

import com.hf.config.rpc.HelloWorld;
import com.hf.config.rpc.HelloWorldImpl;

@Configuration
public class ThriftConfig {
	
	@Bean
	public HelloWorld.Iface helloWorld(){
		return new HelloWorldImpl();
	}
	
	@Bean
	public ThriftHttpServiceExporter thriftHttpServiceExporter(){
		ThriftHttpServiceExporter exporter = new ThriftHttpServiceExporter();
		exporter.setService(helloWorld());
		exporter.setServiceInterface(HelloWorld.Iface.class);
		return exporter;
	}
}
