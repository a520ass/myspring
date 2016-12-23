package com.hf.config.rpc.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;

import com.hf.config.rpc.AccountService;

@Configuration
public class HttpInvokerConfig {
	
	@Autowired AccountService accountService;
	
	@Bean
	public HttpInvokerServiceExporter httpInvokerServiceExporter(){
		HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();
		exporter.setService(accountService);
		exporter.setServiceInterface(AccountService.class);
		return exporter;
	}
	
	/**
	 * 	下列方式是通过	BeanNameUrlHandlerMapping 实现的
	 * 	查找spring容器中和请求的url同名的bean.（即以下例子中。name属性设置的/userService,此为别名 ，与请求的路径对应的话，即可匹配到）
	 * 	这个映射器不需要配置,因为spring在找不到handlerMapping的情况下会使用BeanNameUrlHandlerMapping
	 * 	<!-- 通过Spring HttpInvoker机制暴露远程访问服务 -->    
	    <bean name="/userService" id="userServiceExporter"  
	        class="org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter">  
	        <property name="service" ref="userServiceimpl"/>  
	        <property name="serviceInterface" value="cn.slimsmart.spring.httpinvoker.demo.UserService" />  
	    </bean> 
	 */
	@Bean({"httpInvokerServiceExporterByBeanNameUHM","/httpinvokerrpc1.service"})
	public HttpInvokerServiceExporter httpInvokerServiceExporterByBeanNameUHM(){
		HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();
		exporter.setService(accountService);
		exporter.setServiceInterface(AccountService.class);
		return exporter;
	}
}
