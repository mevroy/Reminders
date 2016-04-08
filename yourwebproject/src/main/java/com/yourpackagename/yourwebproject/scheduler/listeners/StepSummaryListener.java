/**
 * 
 */
package com.yourpackagename.yourwebproject.scheduler.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;



/**
 * @author mevan.d.souza
 *
 */
public class StepSummaryListener implements StepExecutionListener {
	/** Logger for this class. */
	private static final Logger logger = LoggerFactory
			.getLogger(StepSummaryListener.class);
	/* (non-Javadoc)
	 * @see org.springframework.batch.core.StepExecutionListener#afterStep(org.springframework.batch.core.StepExecution)
	 */
	public ExitStatus afterStep(StepExecution arg0) {
		logger.info("Summary for batch:"+arg0.getJobExecutionId()+"  "+arg0.getSummary());
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springframework.batch.core.StepExecutionListener#beforeStep(org.springframework.batch.core.StepExecution)
	 */
	public void beforeStep(StepExecution arg0) {
		// TODO Auto-generated method stub

	}

}
