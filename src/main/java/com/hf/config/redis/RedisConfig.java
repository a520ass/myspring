package com.hf.config.redis;

import java.lang.reflect.Method;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import com.hf.config.GlobalConfig;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableCaching	//spring缓存启用
@EnableRedisRepositories(basePackages=GlobalConfig.BASEPACKAGES)
public class RedisConfig extends CachingConfigurerSupport{
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Resource
	private Environment environment;
	
	@Bean
	@Override
	public CacheManager cacheManager() {
		return new RedisCacheManager(redisTemplate);
	}

	@Bean
	@Override
	public KeyGenerator keyGenerator(){
		KeyGenerator keyGenerator=new KeyGenerator() {

			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName());
				sb.append(method.getName());
				for (Object obj : params) {
					sb.append(obj.toString());
				}
				return sb.toString();
			}
		};
		return keyGenerator;
	}
	
	@Bean
	public JedisPoolConfig poolConfig(){
		JedisPoolConfig poolConfig=new JedisPoolConfig();
		poolConfig.setMinIdle(environment.getProperty("redis.pool.minIdle", Integer.class));
		poolConfig.setMaxIdle(20);
		poolConfig.setMaxTotal(1024);
		poolConfig.setMaxWaitMillis(100);
		return poolConfig;
	}
	
	@Bean
	public RedisConnectionFactory redisConnectionFactory(JedisPoolConfig poolConfig){
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		jedisConnectionFactory.setHostName(environment.getProperty("redis.host"));
		jedisConnectionFactory.setPort(6379);
		jedisConnectionFactory.setPassword(environment.getProperty("redis.password"));
		jedisConnectionFactory.setUsePool(true);
		jedisConnectionFactory.setPoolConfig(poolConfig);
		return jedisConnectionFactory;
	}
	
	@SuppressWarnings("rawtypes")
	@Bean  
    public RedisTemplate redisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate redisTemplate=new RedisTemplate();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		redisTemplate.setEnableTransactionSupport(true);
		return redisTemplate;
    }
	
	/*@Bean
	public RedisKeyValueAdapter redisKeyValueAdapter(@SuppressWarnings("rawtypes") RedisTemplate redisTemplate){
		RedisKeyValueAdapter redisKeyValueAdapter=new RedisKeyValueAdapter(redisTemplate);
		return redisKeyValueAdapter;
	}*/
	
	//@Bean		自动注入了一个
	/*public RedisKeyValueTemplate redisKeyValueTemplate(RedisKeyValueAdapter redisKeyValueAdapter){
		RedisKeyValueTemplate redisKeyValueTemplate=new RedisKeyValueTemplate(redisKeyValueAdapter, new RedisMappingContext());
		return redisKeyValueTemplate;
	}*/
	
	@Bean
	public StringRedisTemplate stringRedisTemplate(
			RedisConnectionFactory redisConnectionFactory) {
		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(
				redisConnectionFactory);
		stringRedisTemplate.setEnableTransactionSupport(true);
		return stringRedisTemplate;
	}
	
	//redis消息测试
	/*@Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
            MessageListenerAdapter listenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("chat"));

        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    @Bean
    Receiver receiver(CountDownLatch latch) {
        return new Receiver(latch);
    }

    @Bean
    CountDownLatch latch() {
        return new CountDownLatch(1);
    }*/

    
}

