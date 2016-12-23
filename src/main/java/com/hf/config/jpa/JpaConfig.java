package com.hf.config.jpa;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.hf.config.GlobalConfig;

@Configuration
@EnableJpaRepositories(basePackages=GlobalConfig.BASEPACKAGES)
public class JpaConfig {
	
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource){
		return new JdbcTemplate(dataSource);
	}
	
	@Bean
	public JpaVendorAdapter jpaVendorAdapter(){
		HibernateJpaVendorAdapter adapter=new HibernateJpaVendorAdapter();
		adapter.setShowSql(false);
		adapter.setGenerateDdl(true);
		adapter.setDatabase(org.springframework.orm.jpa.vendor.Database.MYSQL);
		adapter.setDatabasePlatform("org.hibernate.dialect.MySQL5InnoDBDialect");
		return adapter;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter){
		LocalContainerEntityManagerFactoryBean entityManagerFactory=new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource);
		entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
		entityManagerFactory.setPackagesToScan(GlobalConfig.BASEPACKAGES);
		
		Properties jpaProperties = new Properties();
		jpaProperties.put("hibernate.physical_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
		//jpaProperties.put("hibernate.Implicit_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");
		entityManagerFactory.setJpaProperties(jpaProperties);
		return entityManagerFactory;
	}
	
	/*@Bean
	public DataSource dataSource(){
		org.apache.tomcat.jdbc.pool.DataSource ds=new org.apache.tomcat.jdbc.pool.DataSource();
		ds.setDriverClassName(environment.getProperty("jdbc.driver"));
		ds.setUrl(environment.getProperty("jdbc.url"));
		ds.setUsername(environment.getProperty("jdbc.username"));
		ds.setPassword(environment.getProperty("jdbc.password"));
		ds.setInitialSize(5);
		ds.setMinIdle(5);
		ds.setMaxIdle(environment.getProperty("jdbc.pool.maxIdle", Integer.class));
		ds.setMaxActive(environment.getProperty("jdbc.pool.maxActive", Integer.class));
		return ds;
	}*/
	//jta事务 start
	/*@Bean(initMethod="init",destroyMethod="close")
	public AtomikosDataSourceBean dataSource(){
		AtomikosDataSourceBean ds=new AtomikosDataSourceBean();
		ds.setUniqueResourceName("ds1");
		ds.setXaDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlXADataSource");
		Properties xaProperties=new Properties();
		xaProperties.put("URL", environment.getProperty("jdbc.url"));
		xaProperties.put("user", environment.getProperty("jdbc.username"));
		xaProperties.put("password", environment.getProperty("jdbc.password"));
		ds.setXaProperties(xaProperties);
		ds.setMinPoolSize(5);	//#连接池中保留的最小连接数
		ds.setMaxPoolSize(20);	//#连接池中保留的最大连接数
		ds.setMaxIdleTime(60);	//#最大空闲时间,60秒内未使用则连接被丢弃。若为0则永不丢弃。Default:
		//ds.setTestQuery("select 1");
		return ds;
	}
	
	@Bean(initMethod="init",destroyMethod="shutdownForce")
	public UserTransactionServiceImp userTransactionService(){
		Properties properties=new Properties();
		properties.put("com.atomikos.icatch.service", "com.atomikos.icatch.standalone.UserTransactionServiceFactory");
		return new UserTransactionServiceImp(properties);
	}
	
	@Bean(initMethod="init",destroyMethod="close")
	public UserTransactionManager atomikosTransactionManager(UserTransactionServiceImp userTransactionService){
		UserTransactionManager utm=new UserTransactionManager();
		utm.setForceShutdown(false);
		return utm;
	}
	
	@Bean
	public UserTransactionImp atomikosUserTransaction(UserTransactionServiceImp userTransactionService) throws SystemException{
		UserTransactionImp uti=new UserTransactionImp();
		uti.setTransactionTimeout(300);
		return uti;
	}
	
	@Bean//(name={"jtaTransactionManager","transactionManager"})
	public JtaTransactionManager jtaTransactionManager(UserTransactionManager atomikosTransactionManager, UserTransactionImp atomikosUserTransaction){
		JtaTransactionManager jtm=new JtaTransactionManager();
		jtm.setTransactionManager(atomikosTransactionManager);
		jtm.setUserTransaction(atomikosUserTransaction);
		return jtm;
	}*/
	//jta事务 end
}
