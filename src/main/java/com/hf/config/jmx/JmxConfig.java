package com.hf.config.jmx;

import java.util.Map;

import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.jmx.export.assembler.MethodNameBasedMBeanInfoAssembler;
import org.springframework.jmx.support.ConnectorServerFactoryBean;
import org.springframework.jmx.support.MBeanServerFactoryBean;
import org.springframework.jmx.support.RegistrationPolicy;

import com.google.common.collect.Maps;

//@Configuration
//@EnableMBeanExport(server="mbeanServer")
public class JmxConfig {
	
	@Bean
	public ConnectorServerFactoryBean connectorServerFactoryBean() throws MalformedObjectNameException{
		ConnectorServerFactoryBean csfb = new ConnectorServerFactoryBean();
		csfb.setObjectName("connector:name=rmi");
		csfb.setServiceUrl("service:jmx:rmi://localhost/jndi/rmi://localhost:1099/myconnector");
		return csfb;
	}
	
	@Bean
	public MBeanServerFactoryBean mbeanServer(){
		MBeanServerFactoryBean mBeanServerFactoryBean = new MBeanServerFactoryBean();
		mBeanServerFactoryBean.setLocateExistingServerIfPossible(true);
		mBeanServerFactoryBean.setAgentId("MBeanServer_instance_agentId");
		return mBeanServerFactoryBean;
	}
	
	@Bean
	public MethodNameBasedMBeanInfoAssembler assembler(){
		MethodNameBasedMBeanInfoAssembler assembler = new MethodNameBasedMBeanInfoAssembler();
		//assembler.setm
		return assembler;
	}
	
	@Bean
	public MBeanExporter mBeanExporter(MBeanServer mbeanServer){
		MBeanExporter exporter=new MBeanExporter();
		exporter.setServer(mbeanServer);
		exporter.setAutodetect(true);
		exporter.setRegistrationPolicy(RegistrationPolicy.REPLACE_EXISTING);
		Map<String, Object> beans=Maps.newHashMap();
		//beans.put("", "");
		exporter.setBeans(beans);
		return exporter;
	}
}
