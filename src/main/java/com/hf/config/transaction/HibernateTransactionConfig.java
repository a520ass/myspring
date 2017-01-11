package com.hf.config.transaction;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

//@Configuration
@EnableTransactionManagement
public class HibernateTransactionConfig implements TransactionManagementConfigurer{
	
	@Autowired
	private PlatformTransactionManager transactionManager;
	
	@Bean
	public PlatformTransactionManager transactionManager(SessionFactory sessionFactory){
		return new HibernateTransactionManager(sessionFactory);
	}
	
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return transactionManager;
	}

}
