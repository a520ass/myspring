package com.hf.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hf.config.quartz.QuartzConfig;
import com.hf.config.quartz.QuartzManager;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {QuartzConfig.class,QuartzManager.class})
public class QuartzTest {
	
	@Autowired
	private Scheduler scheduler;
	
	@Autowired(required=false)
	QuartzManager quartzManager;
	
	
	
	@Test
	public void testq() throws InterruptedException{
		quartzManager.removeJob("exampleJob", "DEFAULT", "simpleTrigger", "DEFAULT");
		quartzManager.removeJob("testjob1");
		//quartzManager.addJob("testjob1", ExampleJob.class,"*/5 * * * * ?");
		
		Thread.sleep(60*1000L);
	}
	
	
	@Test
	public void test() throws SchedulerException, InterruptedException{
		scheduler.start();
		//Thread.sleep(50000L);
		//scheduler.shutdown(true);
		//这里获取任务信息数据
        /*List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();
        
        for (int i = 0; i < 3; i++) {
            ScheduleJob job = new ScheduleJob();
            job.setJobId("10001" + i);
            job.setJobName("JobName_" + i);
            job.setJobGroup("dataWork");
            job.setJobStatus("1");
            job.setCronExpression("0/5 * * * * ?");
            job.setDesc("数据导入任务");
            jobList.add(job);
        }

        for (ScheduleJob job : jobList) {
         
            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
         
            //获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            //不存在，创建一个
            if (null == trigger) {
                JobDetail jobDetail = JobBuilder.newJob(QuartzJobFactoryImpl.class)
                    .withIdentity(job.getJobName(), job.getJobGroup()).build();
                jobDetail.getJobDataMap().put("scheduleJob", job);
         
                //表达式调度构建器
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job
                    .getCronExpression());
         
                //按新的cronExpression表达式构建一个新的trigger
                trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup()).withSchedule(scheduleBuilder).build();
                scheduler.scheduleJob(jobDetail, trigger);
            } else {
                // Trigger已存在，那么更新相应的定时设置
                //表达式调度构建器
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job
                    .getCronExpression());
         
                //按新的cronExpression表达式重新构建trigger
                trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                    .withSchedule(scheduleBuilder).build();
         
                //按新的trigger重新设置job执行
                scheduler.rescheduleJob(triggerKey, trigger);
            }
        }*/
		//Thread.sleep(500000L);
	}
	
}

