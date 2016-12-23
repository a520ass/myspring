package com.hf.config.quartz;

import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


/**
 * @Description: 定时任务管理类
 * 
 * @ClassName: QuartzManager
 */
@Component
public class QuartzManager {
	//private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
	
	@Autowired
	@Qualifier("schedulerFactoryBean")
	private Scheduler scheduler;
	
	private static String JOB_GROUP_NAME = "MYPROJECT_JOBGROUP_NAME";
	private static String TRIGGER_GROUP_NAME = "MYPROJECT_TRIGGERGROUP_NAME";

	/**
	 * @Description 添加一个定时任务
	 * @param jobName
	 * @param jobClass
	 * @param time
	 * @author hefeng
	 */
	public void addJob(String jobName, Class<? extends Job> jobClass, String time) {
		try {
			
			JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, JOB_GROUP_NAME).build();// 任务名，任务组，任务执行类
			// 触发器
			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, TRIGGER_GROUP_NAME).withSchedule(CronScheduleBuilder.cronSchedule(time)).build();// 触发器名,触发器组
			scheduler.scheduleJob(jobDetail, trigger);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Description 添加一个定时任务
	 * @param jobName
	 * @param jobGroupName
	 * @param triggerName
	 * @param triggerGroupName
	 * @param jobClass
	 * @param time
	 * @author hefeng
	 */
	public void addJob(String jobName, String jobGroupName,
			String triggerName, String triggerGroupName, Class<? extends Job> jobClass,
			String time) {
		try {
			JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();// 任务名，任务组，任务执行类
			// 触发器
			CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroupName).withSchedule(CronScheduleBuilder.cronSchedule(time)).build();// 触发器名,触发器组
			
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Description 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
	 * @param jobName
	 * @param time
	 * @author hefeng
	 */
	public void modifyJobTime(String jobName, String time) {
		try {
			@SuppressWarnings("unchecked")
			List<CronTrigger> triggers = (List<CronTrigger>) scheduler.getTriggersOfJob(JobKey.jobKey(jobName,JOB_GROUP_NAME));
			
			for (CronTrigger trigger : triggers) {
				if (trigger == null) {
					return;
				}
				String oldTime = trigger.getCronExpression();
				if (!oldTime.equalsIgnoreCase(time)) {
					CronTrigger newTrigger = TriggerBuilder.newTrigger().withIdentity(trigger.getKey().getName(), trigger.getKey().getGroup()).withSchedule(CronScheduleBuilder.cronSchedule(time)).build();
					// 修改触发器
					scheduler.rescheduleJob(trigger.getKey(), newTrigger);
				}
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Description 修改一个任务的触发时间
	 * @param triggerName
	 * @param triggerGroupName
	 * @param time
	 * @author hefeng
	 */
	public void modifyJobTime(String triggerName,
			String triggerGroupName, String time) {
		try {
			TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			if (trigger == null) {
				return;
			}
			String oldTime = trigger.getCronExpression();
			if (!oldTime.equalsIgnoreCase(time)) {
				CronTrigger newTrigger = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroupName).withSchedule(CronScheduleBuilder.cronSchedule(time)).build();
				// 修改触发器
				scheduler.rescheduleJob(triggerKey, newTrigger);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Description 移除一个任务(使用默认的 任务组名，触发器名，触发器组名)
	 * @param jobName
	 * @author hefeng
	 */
	public void removeJob(String jobName) {
		try {
			List<? extends Trigger> triggers = scheduler.getTriggersOfJob(JobKey.jobKey(jobName,JOB_GROUP_NAME));
			for (int i = 0; i < triggers.size(); i++) {
				scheduler.pauseTrigger(triggers.get(i).getKey());// 停止触发器
				scheduler.unscheduleJob(triggers.get(i).getKey());// 移除触发器
			}
			scheduler.deleteJob(JobKey.jobKey(jobName,JOB_GROUP_NAME));// 删除任务
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Description 移除一个任务
	 * @param jobName
	 * @param jobGroupName
	 * @param triggerName
	 * @param triggerGroupName
	 * @author hefeng
	 */
	public void removeJob(String jobName, String jobGroupName,
			String triggerName, String triggerGroupName) {
		try {
			scheduler.pauseTrigger(TriggerKey.triggerKey(triggerName, triggerGroupName));// 停止触发器
			scheduler.unscheduleJob(TriggerKey.triggerKey(triggerName, triggerGroupName));// 移除触发器
			scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Description 启动所有定时任务
	 * @author hefeng
	 */
	public void startJobs() {
		try {
			scheduler.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Description 关闭所有定时任务
	 * @author hefeng
	 */
	public void shutdownJobs() {
		try {
			if (!scheduler.isShutdown()) {
				scheduler.shutdown();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

