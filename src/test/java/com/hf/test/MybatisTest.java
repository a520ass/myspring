package com.hf.test;


import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hf.config.custom.SpringContextHolder;
import com.hf.config.jpa.DataSourceConfig;
import com.hf.config.orm.MybatisConfig;
import com.hf.config.redis.RedisConfig;
import com.hf.config.transaction.DataSourceTransactionConfig;
import com.mybatis.mapper.UserMapper;
import com.mybatis.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataSourceConfig.class,MybatisConfig.class,DataSourceTransactionConfig.class,RedisConfig.class, SpringContextHolder.class})
@TestPropertySource({"classpath:jpa-test.conf","classpath:redis-test.conf"})
public class MybatisTest extends AbstractTransactionalJUnit4SpringContextTests{
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	UserMapper userMapper;
	
	@Test
	@Commit
	public void test1() throws SQLException{
		/*PageHelper.startPage(1, 1);
		List<User> lists = userMapper.findAll();*/
		
		List<User> lists = userMapper.selectAll();
		
		System.out.println(lists.toString());
		//throw new RuntimeException("error");	//错误未回滚
		//List<User> lists2 = userMapper.selectAll();
	}

}

