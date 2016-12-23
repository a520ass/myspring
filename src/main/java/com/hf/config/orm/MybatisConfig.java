package com.hf.config.orm;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.hf.config.GlobalConfig;

@Configuration
@MapperScan("com.mybatis.mapper")
public class MybatisConfig {

	@Bean
	public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource)
			throws Exception {
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
	
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();        
	    //将加载多个绝对匹配的所有Resource  
	    //将首先通过ClassLoader.getResources("META-INF")加载非模式路径部分  
	    //然后进行遍历模式匹配  
	    Resource[] resources=resolver.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX+"mapper/**/*Mapper.xml");
		sessionFactory.setMapperLocations(resources);
		return sessionFactory;
	}

}
