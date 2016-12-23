package com.hf.config.redis;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.google.common.collect.Lists;

//@Configuration
public class RedisClusterConfig {
	
	/**
     * Type safe representation of application.properties
     */
    //@Autowired ClusterConfigurationProperties clusterProperties;
    
    @Bean
	public JedisConnectionFactory jedisConnectionFactory() {
    	String[] nodes = StringUtils.split("192.168.153.130:6380 192.168.153.130:6381 192.168.153.130:6382 192.168.153.130:6383 192.168.153.130:6384 192.168.153.130:6385", null);
    	List<String> nodeLists=Arrays.asList(nodes);
    	return new JedisConnectionFactory(new RedisClusterConfiguration(
    			nodeLists));
	}
    
    @SuppressWarnings("rawtypes")
	@Bean
	public RedisTemplate redisTemplate(JedisConnectionFactory jedisConnectionFactory){
		RedisTemplate redisTemplate=new RedisTemplate();
		redisTemplate.setConnectionFactory(jedisConnectionFactory);
		return redisTemplate;
	}
}
