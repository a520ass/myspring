package com.hf.config.zk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.hf.config.thrif.zookeeper.ZookeeperFactory;

//@Configuration
public class ZKConfig {
	
	@Autowired Environment environment;
	
	@Bean(name="curatorFramework",destroyMethod="close")
	public ZookeeperFactory zookeeperFactory(){
		ZookeeperFactory zk=new ZookeeperFactory();
		zk.setZkHosts(environment.getProperty("zkHosts"));
		return zk;
	}
}
