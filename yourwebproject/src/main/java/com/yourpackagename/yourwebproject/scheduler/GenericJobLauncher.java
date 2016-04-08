package com.yourpackagename.yourwebproject.scheduler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yourpackagename.yourwebproject.model.entity.GroupCronJob;
import com.yourpackagename.yourwebproject.service.GroupCronJobService;

@Service
public class GenericJobLauncher extends QuartzJobBean {

	private static Logger logger = LoggerFactory
			.getLogger(GenericJobLauncher.class);

	// Date Format for generating and tracking runId
	private static DateFormat RUNID_DATEFORMAT = new SimpleDateFormat(
			"dd-MM-yyyy-HHmmss");

	static final String JOB_NAME = "jobName";

	/*
	 * @Override protected void executeInternal(JobExecutionContext context)
	 * throws JobExecutionException {
	 * logger.trace("enter EmailProcessorTimer.run()"); try { String runId =
	 * RUNID_DATEFORMAT.format(Calendar.getInstance() .getTime()); JobParameters
	 * jobParameters = new JobParametersBuilder().addString( "runId",
	 * runId).toJobParameters();
	 * 
	 * JobExecution execution = jobLauncher.run(processorJob, jobParameters);
	 * logger.info(runId + ": Exit status=" +
	 * execution.getExitStatus().getExitCode()); } catch (Exception e) {
	 * e.printStackTrace(); } logger.trace("exit EmailProcessorTimer.run()"); }
	 */

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		logger.trace("enter EmailProcessorTimer.run()");
		String runId = RUNID_DATEFORMAT
				.format(Calendar.getInstance().getTime());
		Map<String, Object> jobDataMap = (Map<String, Object>) context
				.getMergedJobDataMap();
		JobLocator jobLocator = (JobLocator) jobDataMap.get("jobLocator");
		JobLauncher jobLauncher = (JobLauncher) jobDataMap.get("jobLauncher");

		String jobCode = (String) jobDataMap.get("jobCode");
		String jobName = (String) jobDataMap.get(JOB_NAME);
		GroupCronJobService groupCronJobService = (GroupCronJobService) jobDataMap
				.get("groupCronJobService");
		GroupCronJob gcj = null;
		if (groupCronJobService != null) {
			gcj = groupCronJobService.findByJobCode(jobCode);

			if(gcj!=null){
			jobDataMap.put("runId", jobCode + "-" + runId);
			jobDataMap.put(
					"queryString",
					this.getQueryString(gcj.getJobQueryString(),
							gcj.getGroupCode(), gcj.getMemberCategoryCode()));
			jobDataMap.put("templateName", gcj.getTemplateName());
			jobDataMap.put("jobCode", gcj.getJobCode());
			jobDataMap.put("emailAddress", gcj.getJobStatusContactEmail());
			jobDataMap.put("groupEventCode", gcj.getGroupEventCode());
			}
		}
		JobParameters jobParameters = getJobParametersFromJobMap(jobDataMap);

		try {

			if (gcj != null) {
				if (gcj.isDisabled()) {
					logger.info("Skipping execution of job :" + jobName
							+ " with run id (" + jobCode + "-" + runId
							+ " as it seems to be disabled now!");
				} else {
					jobLauncher.run(jobLocator.getJob(jobName), jobParameters);
					gcj.setLastExecutionDate(Calendar.getInstance().getTime());
					gcj.setUpdatedAt(Calendar.getInstance().getTime());
					try {
						if(groupCronJobService!=null)
						groupCronJobService.update(gcj);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						logger.error("Unable to update Last Exceution Date for job:"+jobName
								+ " with run id (" + jobCode + "-" + runId);
						e.printStackTrace();
					}
				}
			}

		} catch (JobExecutionAlreadyRunningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobRestartException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobInstanceAlreadyCompleteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JobParametersInvalidException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchJobException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.trace("exit EmailProcessorTimer.run()");
	}

	private JobParameters getJobParametersFromJobMap(
			Map<String, Object> jobDataMap) {

		JobParametersBuilder builder = new JobParametersBuilder();

		for (Entry<String, Object> entry : jobDataMap.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value instanceof String && !key.equals(JOB_NAME)) {
				builder.addString(key, (String) value);
			} else if (value instanceof Float || value instanceof Double) {
				builder.addDouble(key, ((Number) value).doubleValue());
			} else if (value instanceof Integer || value instanceof Long) {
				builder.addLong(key, ((Number) value).longValue());
			} else if (value instanceof Date) {
				builder.addDate(key, (Date) value);
			} else {
				// JobDataMap contains values which are not job parameters
				// (ignoring)
			}
		}

		return builder.toJobParameters();
	}

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
				&& queryString.indexOf("GroupMember") != -1
				&& andClause.trim().length() > 0) {
			if (query.indexOf(" where ") != -1) {
				query = queryString + " and " + andClause;
			} else {
				query = queryString + " where " + andClause;
			}
		}
		return query;
	}
}