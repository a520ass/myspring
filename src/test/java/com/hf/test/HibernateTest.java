package com.hf.test;


import com.hf.config.custom.SpringContextHolder;
import com.hf.config.jpa.DataSourceConfig;
import com.hf.config.orm.HibernateConfig;
import com.hf.config.orm.MybatisConfig;
import com.hf.config.redis.RedisConfig;
import com.hf.config.transaction.DataSourceTransactionConfig;
import com.hf.config.transaction.HibernateTransactionConfig;
import com.mybatis.mapper.UserMapper;
import com.mybatis.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataSourceConfig.class, HibernateConfig.class, HibernateTransactionConfig.class})
@TestPropertySource({"classpath:jpa-test.conf"})
public class HibernateTest {
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Test
	public void test1() throws SQLException{
		Session session = sessionFactory.openSession();
		boolean open = session.isOpen();
	}

}

