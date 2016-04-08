/**
 * 
 */
package com.yourpackagename.yourwebproject.scheduler;

import java.text.ParseException;

import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author mevan.d.souza
 *
 */
public class CronBatchesTiggerer {
/*
	protected @Autowired static Job processorJob;
	protected @Autowired static JobLauncher jobLauncher;
	protected @Autowired static JobLocator jobLocator;
	private static Logger logger = LoggerFactory.getLogger(CronBatchesTiggerer.class);
	*//**
	 * @param args
	 * @throws ParseException
	 *//*
	public static void main(String[] args) throws ParseException {
		logger.info("Starting in Main");;
		JobDetail job = new JobDetail();
		job.setName("processorJob");
		job.setJobClass(GenericJobLauncher.class);
		job.setGroup("quartz-batch");

		CronTrigger trigger = new CronTrigger();
		trigger.setName("dummyTriggerName");
		try {
			trigger.setCronExpression("0/30 * * * * ?");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// schedule it
		Scheduler scheduler;
		try {
			scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();
			scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}*/

}
