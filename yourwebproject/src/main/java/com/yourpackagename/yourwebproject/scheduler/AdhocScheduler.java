package com.yourpackagename.yourwebproject.scheduler;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.CronTriggerBean;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yourpackagename.yourwebproject.model.entity.GroupCronJob;
import com.yourpackagename.yourwebproject.service.GroupCronJobService;

@Service
public class AdhocScheduler {
	/**
	 * @author mevan.d.souza
	 *
	 */
	private @Autowired JobLauncher jobLauncher;
	private @Autowired JobLocator jobLocator;
	private @Autowired GroupCronJobService groupCronJobService;

	private static Logger logger = LoggerFactory
			.getLogger(AdhocScheduler.class);

	// This is for 30 - 1800*1000
//	 @Scheduled(fixedDelay = 1800*1000)
	 @Scheduled(cron = "0 0/30 * * * *")
	public void run() throws SchedulerException {
		 logger.info("Running Adhoc Scheduler at:"+new Date());
		List<GroupCronJob> cronjobs = groupCronJobService.findGroupCronJobs();
		for (GroupCronJob cronJob : cronjobs) {
			if (cronJob.getLastExecutionDate() == null && cronJob.getJobScheduleDate()==null) {
				logger.info("Entering AdhocScheduler for starting configured cron - "+cronJob.getJobCronName()+ " - "+cronJob.getJobDescription());
				SchedulerFactory sf = new StdSchedulerFactory();
				Scheduler sched = sf.getScheduler();
				sched.start();
				JobDetail jd = new JobDetail(cronJob.getJobCode(),
						Scheduler.DEFAULT_GROUP, GenericJobLauncher.class);
				JobDataMap jdm = new JobDataMap();
				jdm.put("jobName", cronJob.getJobName());
				jdm.put("jobLocator", jobLocator);
				jdm.put("jobLauncher", jobLauncher);
				jdm.put("groupCronJobService", groupCronJobService);
				jdm.put("queryString", this.getQueryString(
						cronJob.getJobQueryString(), cronJob.getGroupCode(),
						cronJob.getMemberCategoryCode()));
				jdm.put("templateName", cronJob.getTemplateName());
				jdm.put("jobCode", cronJob.getJobCode());
				jdm.put("emailAddress", cronJob.getJobStatusContactEmail());
				jdm.put("groupEventCode", cronJob.getGroupEventCode());
				jd.setJobDataMap(jdm);

				if (StringUtils.isNotBlank(cronJob.getJobCronExpression())
						&& cronJob.getLastExecutionDate() == null && cronJob.getJobScheduleDate()==null) {
					CronTriggerBean cronTrigger = new CronTriggerBean();
					cronTrigger.setBeanName(cronJob.getJobCronName());

					// Execute after each 5 second
					String expression = cronJob.getJobCronExpression();// "0 0/1 * 1/1 * ? *";
					if(cronJob.getStartDate()!=null && cronJob.getStartDate().after(Calendar.getInstance().getTime())){
						cronTrigger.setStartTime(cronJob.getStartDate());
					}
					if(cronJob.getExpiryDate()!=null)
					{
						cronTrigger.setEndTime(cronJob.getExpiryDate());
					}
					try {
						cronTrigger.setCronExpression(expression);
						cronTrigger.afterPropertiesSet();
					} catch (Exception e) {

					}

					sched.scheduleJob(jd, cronTrigger);
					logger.info("Cron --> "+cronJob.getJobName() + " : "
							+ cronJob.getJobDescription()+" was successfully scheduled");
					//cronJob.setLastExecutionDate(new Date());
					cronJob.setJobScheduleDate(Calendar.getInstance().getTime());
					cronJob.setUpdatedAt(Calendar.getInstance().getTime());
				} else {
					if (cronJob.getStartDate() != null
							&& cronJob.getLastExecutionDate() == null && cronJob.getJobScheduleDate()==null) {
						SimpleTrigger st = new SimpleTrigger(
								cronJob.getJobCronName(),
								Scheduler.DEFAULT_GROUP,
								cronJob.getStartDate(),
								cronJob.getExpiryDate(), 0, 0);

						sched.scheduleJob(jd, st);
						logger.info("Cron --> "+cronJob.getJobName() + " : "
								+ cronJob.getJobDescription()+" was successfully Triggered");
					//	cronJob.setLastExecutionDate(new Date());
						cronJob.setJobScheduleDate(Calendar.getInstance().getTime());
						cronJob.setUpdatedAt(Calendar.getInstance().getTime());
					}
				}

				try {
					groupCronJobService.update(cronJob);
				} catch (Exception e) {
					e.printStackTrace();
				}
				logger.info("Exiting AdhocScheduler for starting configured crons - "+cronJob.getJobCronName());
			}
		}
	}

	/*
	 * public class MainJob {
	 * 
	 * public static void main(String[] args) { try { CommandLineJobRunner run =
	 * new CommandLineJobRunner(); String[] params = { "itemReaderWriter.xml",
	 * "readerWriterJob" }; run.main(params); } catch (Exception e) {
	 * e.printStackTrace(); }
	 */
	public String getQueryString(String queryString, String groupCode,
			String memberCategoryCode) {
		String andClause = "";
		boolean groupCodePresent = false;
		if (groupCode != null && groupCode.trim().length() > 0
				&& queryString != null
				&& queryString.indexOf("groupCode") == -1
				&& queryString.indexOf("GroupMember") != -1) {
			if (queryString.indexOf("GroupMember gm") == -1) {
				andClause = "groupCode = '" + groupCode + "'";
			} else {
				andClause = "gm.groupCode = '" + groupCode + "'";
			}
			groupCodePresent = true;
		}
		if (memberCategoryCode != null
				&& memberCategoryCode.trim().length() > 0
				&& queryString != null
				&& queryString.indexOf("memberCategoryCode") == -1
				&& queryString.indexOf("GroupMember") != -1) {
			if (queryString.indexOf("GroupMember gm") == -1) {
				andClause += groupCodePresent ? " and " : ""
						+ " memberCategoryCode = '" + memberCategoryCode + "'";
			} else {
				andClause += groupCodePresent ? " and " : ""
						+ " gm.memberCategoryCode = '" + memberCategoryCode
						+ "'";
			}
		}
		String query = queryString;
		if (query != null && query.trim().length() > 0
				&& queryString.indexOf("GroupMember") != -1 && andClause.trim().length()>0) {
			if (query.indexOf(" where ") != -1) {
				query = queryString + " and " + andClause;
			} else {
				query = queryString + " where " + andClause;
			}
		}
		return query;
	}

}