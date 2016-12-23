package com.hf.config.jpa;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceConfig {
	
	@Resource
	private Environment environment;
	
	@Bean
	public DataSource dataSource(){
		HikariDataSource ds=new HikariDataSource();
		ds.setDriverClassName(environment.getProperty("jdbc.driver"));
		ds.setJdbcUrl(environment.getProperty("jdbc.url"));
		ds.setUsername(environment.getProperty("jdbc.username"));
		ds.setPassword(environment.getProperty("jdbc.password"));
		
		return ds;
	}
}
