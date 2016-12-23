package quartz;

import static org.quartz.JobBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.CronScheduleBuilder.*;
import static org.quartz.CalendarIntervalScheduleBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.DateBuilder.*;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.HolidayCalendar;

public class QuartzTest {
	
	public static void main(String[] args) {
		
        try {
        	// Grab the Scheduler instance from the Factory
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // and start it off
			scheduler.start();
			//doSomething(scheduler);
			doSomething2(scheduler);
			//scheduler.shutdown();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		
	}

	private static void doSomething2(Scheduler sched) throws SchedulerException {
		HolidayCalendar cal = new HolidayCalendar();
		cal.addExcludedDate( new Date() );
		cal.addExcludedDate( new Date() );

		sched.addCalendar("myHolidays", cal, false,false);
		
		// define the job and tie it to our DumbJob class
		  JobDetail myJob = newJob(DumbJob.class)
		      .withIdentity("myJob1", "group1") // name "myJob1", group "group1"
		      .usingJobData("jobSays", "Hello World!")
		      .usingJobData("myFloatValue", 3.141f)
		      .build();

		Trigger t = newTrigger()
		    .withIdentity("myTrigger")
		    .forJob("myJob1","group1")
		    .withSchedule(dailyAtHourAndMinute(15, 5)) // execute job daily at 9:30
		    //.modifiedByCalendar("myHolidays") // but not on holidays
		    .build();

		// .. schedule job with trigger
		sched.scheduleJob(myJob, t);

		/*Trigger t2 = newTrigger()
		    .withIdentity("myTrigger2")
		    .forJob("myJob2")
		    .withSchedule(dailyAtHourAndMinute(10, 29)) // execute job daily at 11:30
		    .modifiedByCalendar("myHolidays") // but not on holidays
		    .build();

		// .. schedule job with trigger2
		sched.scheduleJob(myJob, t2);*/
	}

	private static void doSomething(Scheduler sched) throws SchedulerException {
		// define the job and tie it to our HelloJob class
		  JobDetail job = newJob(HelloJob.class)
		      .withIdentity("myJob", "group1")
		      .build();

		  // Trigger the job to run now, and then every 40 seconds
		  Trigger trigger = newTrigger()
		      .withIdentity("myTrigger", "group1")
		      .startNow()
		      .withSchedule(simpleSchedule()
		          .withIntervalInSeconds(40)
		          .repeatForever())
		      .build();

		  // Tell quartz to schedule the job using our trigger
		  sched.scheduleJob(job, trigger);
		  
	}
}
