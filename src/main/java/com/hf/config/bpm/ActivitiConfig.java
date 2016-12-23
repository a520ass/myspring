package com.hf.config.bpm;

import javax.sql.DataSource;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ActivitiConfig {
	
	@Bean
	public SpringProcessEngineConfiguration processEngineConfiguration(DataSource dataSource,PlatformTransactionManager transactionManager){
		SpringProcessEngineConfiguration spec=new SpringProcessEngineConfiguration();
		spec.setDataSource(dataSource);
		spec.setTransactionManager(transactionManager);
		spec.setActivityFontName("宋体");
		spec.setLabelFontName("宋体");
		spec.setDatabaseSchemaUpdate("true");
		spec.setJobExecutorActivate(false);
		return spec;
	}
	
	@Bean
	public ProcessEngineFactoryBean processEngine(SpringProcessEngineConfiguration processEngineConfiguration){
		ProcessEngineFactoryBean pefb=new ProcessEngineFactoryBean();
		pefb.setProcessEngineConfiguration(processEngineConfiguration);
		return pefb;
	}
	
	
	@Bean
	public RepositoryService repositoryService(ProcessEngine processEngine) throws Exception{
		return processEngine.getRepositoryService();
	}
	
	@Bean
	public RuntimeService runtimeService(ProcessEngine processEngine) throws Exception{
		return processEngine.getRuntimeService();
	}
	
	@Bean
	public TaskService taskService(ProcessEngine processEngine) throws Exception{
		return processEngine.getTaskService();
	}
	
	@Bean
	public HistoryService historyService(ProcessEngine processEngine) throws Exception{
		return processEngine.getHistoryService();
	}
	
	@Bean
	public ManagementService managementService(ProcessEngine processEngine) throws Exception{
		return processEngine.getManagementService();
	}
}
