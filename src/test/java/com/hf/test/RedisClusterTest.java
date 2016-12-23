package com.hf.test;


import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.RedisKeyValueTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hf.config.redis.RedisClusterConfig;
import com.hf.config.redis.RedisConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RedisClusterConfig.class)
//@TestPropertySource("classpath:redis-test.conf")
public class RedisClusterTest {
	
	
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Test
	public void redisTest() throws SQLException{
		redisTemplate.opsForValue().set("myid", "我的贝贝呢4d");
		System.out.println(redisTemplate.opsForValue().get("myid"));
		
	}

}

