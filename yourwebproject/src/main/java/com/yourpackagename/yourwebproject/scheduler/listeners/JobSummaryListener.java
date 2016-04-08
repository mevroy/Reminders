/**
 * 
 */
package com.yourpackagename.yourwebproject.scheduler.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

/**
 * @author mevan.d.souza
 *
 */
public class JobSummaryListener implements JobExecutionListener {
	/** Logger for this class. */
	private static final Logger logger = LoggerFactory
			.getLogger(JobSummaryListener.class);

	public void afterJob(JobExecution arg0) {

		logger.info("Job Summary/Status:" + arg0.getStatus());

	}

	public void beforeJob(JobExecution arg0) {
		// TODO Auto-generated method stub

	}

}
