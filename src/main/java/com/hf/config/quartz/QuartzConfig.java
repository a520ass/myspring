package com.hf.config.quartz;

import org.springframework.beans.support.ArgumentConvertingMethodInvoker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfig {
	
	/*@Autowired
	@Qualifier("jobDetail")
	private JobDetail jobDetail;*/
	
	/*@Autowired
	@Qualifier("exampleJob")
	private JobDetail exampleJob;
	
	@Autowired
	@Qualifier("simpleTrigger")
	private Trigger simpleTrigger;*/
	
	/*@Autowired
	@Qualifier("cronTrigger")
	private Trigger cronTrigger;*/
	
	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(){
		SchedulerFactoryBean schedulerFactoryBean=new SchedulerFactoryBean();
		schedulerFactoryBean.setOverwriteExistingJobs(true);
		schedulerFactoryBean.setStartupDelay(30);
		schedulerFactoryBean.setAutoStartup(true);
		//schedulerFactoryBean.setTriggers(simpleTrigger);
		schedulerFactoryBean.setApplicationContextSchedulerContextKey("applicationContext");
		schedulerFactoryBean.setConfigLocation(new ClassPathResource("quartz-main.properties"));
		return schedulerFactoryBean;
	}
	
	/*@Bean
	public JobDetailFactoryBean exampleJob(){
		JobDetailFactoryBean jobDetailFactoryBean=new JobDetailFactoryBean();
		jobDetailFactoryBean.setJobClass(ExampleJob.class);
		jobDetailFactoryBean.setDurability(true);
		Map<String, String> jobDataAsMap = new HashMap<String, String>();
		jobDataAsMap.put("timeout", "5");
		jobDetailFactoryBean.setJobDataAsMap(jobDataAsMap);
		return jobDetailFactoryBean;
	}*/
	
	/**
	 * MethodInvokingJobDetailFactoryBean 继承了ArgumentConvertingMethodInvoker （间接继承MethodInvoker）
	 * MethodInvoker 没有实现序列化接口。所以会报错
	 * 
	 * 
	 * 
	 * MethodInvokingJob 
	 * StatefulMethodInvokingJob 为quartz中的job封装
	 * （上述两个类中context.setResult(this.methodInvoker.invoke()); ）
	 * this.methodInvoker 也就是MethodInvokingJobDetailFactoryBean本身。
	 * 因为在MethodInvokingJobDetailFactoryBean初始化的时候 调用了jdi.getJobDataMap().put("methodInvoker", this);
	 * 所以this.methodInvoker.invoke() 的调用也就是MethodInvokingJobDetailFactoryBean中调用invoke() 方法（此方法写在父类MethodInvoker中）
	 */
	/*@Bean		此方法不能序列化存储到数据库
	public MethodInvokingJobDetailFactoryBean jobDetail(){
		MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean=new MethodInvokingJobDetailFactoryBean();
		methodInvokingJobDetailFactoryBean.setTargetObject(exampleBusinessObject());
		methodInvokingJobDetailFactoryBean.setTargetMethod("doIt");
		methodInvokingJobDetailFactoryBean.setConcurrent(false);
		return methodInvokingJobDetailFactoryBean;
	}
	
	@Bean
	public ExampleBusinessObject exampleBusinessObject(){
		return new ExampleBusinessObject();
	}*/
	
	/*@Bean
	public SimpleTriggerFactoryBean simpleTrigger(){
		SimpleTriggerFactoryBean simpleTriggerFactoryBean=new SimpleTriggerFactoryBean();
		simpleTriggerFactoryBean.setJobDetail(exampleJob);
		simpleTriggerFactoryBean.setStartDelay(10000);
		simpleTriggerFactoryBean.setRepeatInterval(50000);
		return simpleTriggerFactoryBean;
	}*/
	
	/*@Bean
	public CronTriggerFactoryBean cronTrigger(){
		CronTriggerFactoryBean cronTriggerFactoryBean=new CronTriggerFactoryBean();
		cronTriggerFactoryBean.setJobDetail(exampleJob);
		cronTriggerFactoryBean.setCronExpression("0 0 6 * * ?");
		return cronTriggerFactoryBean;
	}*/
	
}
