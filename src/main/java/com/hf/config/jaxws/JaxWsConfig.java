package com.hf.config.jaxws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.jaxws.SimpleJaxWsServiceExporter;

//@Configuration
public class JaxWsConfig {
	
	/**
	 * spring ws导出器 
	 * @return
	 */
	@Bean
	public SimpleJaxWsServiceExporter jaxWsServiceExporter(){
		SimpleJaxWsServiceExporter jaxWsServiceExporter = new SimpleJaxWsServiceExporter();
		//jaxWsServiceExporter.setBaseAddress("http://127.0.0.1:8089/myspring/ws");
		return jaxWsServiceExporter;
	}
}
