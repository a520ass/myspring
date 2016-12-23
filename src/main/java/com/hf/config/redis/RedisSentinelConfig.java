package com.hf.config.redis;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

//@Configuration
//@EnableRedisHttpSession
public class RedisSentinelConfig implements EnvironmentAware{
	
	private Environment environment;

	@Bean
	public RedisSentinelConfiguration redisSentinelConfiguration(){
		String host=environment.getProperty("redis.sentinel.host");
		//String host="10.10.3.118:26380,10.10.3.118:26381,10.10.3.118:26382";
		//String host="10.11.82.71:26380,10.11.82.81:26381,10.11.82.82:26382";
		List<String> list = Arrays.asList(host.split(","));
		Set<String> sentinelHostAndPorts=new HashSet<String>(list);
		RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration("mymaster",sentinelHostAndPorts);
		return redisSentinelConfiguration;
	}
	
	@Bean
	public JedisConnectionFactory jedisConnectionFactory(RedisSentinelConfiguration redisSentinelConfiguration){
		return new JedisConnectionFactory(redisSentinelConfiguration);
	}
	
	@SuppressWarnings("rawtypes")
	@Bean
	public RedisTemplate redisTemplate(JedisConnectionFactory jedisConnectionFactory){
		RedisTemplate redisTemplate=new RedisTemplate();
		redisTemplate.setConnectionFactory(jedisConnectionFactory);
		return redisTemplate;
	}
	
	@Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

	@Override
	public void setEnvironment(Environment environment) {
		this.environment=environment;
	}
}
