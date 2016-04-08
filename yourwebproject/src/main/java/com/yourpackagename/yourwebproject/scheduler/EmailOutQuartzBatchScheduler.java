package com.yourpackagename.yourwebproject.scheduler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.quartz.JobExecutionContext;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import com.yourpackagename.yourwebproject.common.Props;

@Service
public class EmailOutQuartzBatchScheduler extends QuartzJobBean {

	static final String JOB_NAME = "jobName";
	private static DateFormat RUNID_DATEFORMAT  = new SimpleDateFormat("dd-MM-yyyy-HHmmss");
	private static String jobCode = "EMAIL_OUT";
	private JobLocator jobLocator;

	private JobLauncher jobLauncher;
	public void setJobLocator(JobLocator jobLocator) {
		this.jobLocator = jobLocator;
	}

	public void setJobLauncher(JobLauncher jobLauncher) {
		this.jobLauncher = jobLauncher;
	}

	@SuppressWarnings("unchecked")
	protected void executeInternal(JobExecutionContext context) {

		String runId = RUNID_DATEFORMAT.format(Calendar.getInstance().getTime());
		Map<String, Object> jobDataMap = context.getMergedJobDataMap();
		jobDataMap.put("queryString", "from GroupEmail ge where ge.emailedDate = null and (ge.emailingDate = null or ge.emailingDate<=CONVERT_TZ(now(),'-5:00','+11:00' )) and (ge.emailExpirydate = null or ge.emailExpirydate>=CONVERT_TZ(now(),'-5:00','+11:00' )) and ( ge.emailHeld is null or ge.emailHeld = 0)");
		jobDataMap.put("runId",jobCode+"-"+runId);
		jobDataMap.put("jobCode", jobCode);
		jobDataMap.put("numberOfPartitions", 3);
		String jobName = (String) jobDataMap.get(JOB_NAME);

		JobParameters jobParameters = getJobParametersFromJobMap(jobDataMap);

		try {
			jobLauncher.run(jobLocator.getJob(jobName), jobParameters);
		} catch (JobExecutionException e) {
			e.printStackTrace();
		}
	}

	// get params from jobDataAsMap property, job-quartz.xml
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

		// need unique job parameter to rerun the completed job

		return builder.toJobParameters();

	}

}