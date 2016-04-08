package com.yourpackagename.yourwebproject.scheduler.processors;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.stereotype.Component;

import com.yourpackagename.yourwebproject.actor.MailSenderActor;
import com.yourpackagename.yourwebproject.actor.MailSenderUntypedActor;
import com.yourpackagename.yourwebproject.model.entity.GroupEmail;
import com.yourpackagename.yourwebproject.model.entity.GroupEmailAccount;
import com.yourpackagename.yourwebproject.service.GroupEmailAccountService;

/**
 * EmailRetrievalItemProcessor
 * 
 */
@Component("emailProcessor")
@Scope("step")
public class EmailOutItemProcessor implements
		ItemProcessor<GroupEmail, GroupEmail>, ItemStream {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(EmailOutItemProcessor.class);

	@Value("#{jobParameters['runId']}")
	private String runId;

	@Value("#{jobParameters['jobCode']}")
	private String jobCode;

	//private @Autowired MailSenderActor mailSenderActor;
	private @Autowired MailSenderUntypedActor mailSenderUntypedActor;
	private @Autowired GroupEmailAccountService groupEmailAccountService;
	/**
	 * Process an email message
	 *
	 * @param message
	 *            the message
	 * @return JPA entities map
	 * @throws Exception
	 *             the exception from either being unable to read headers or
	 *             when there is no Sender identified
	 */
	public GroupEmail process(GroupEmail groupEmail) throws Exception {
	//	public Map<String, Object> process(GroupEmail groupEmail) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		groupEmail.setUpdatedBy(jobCode);
		GroupEmailAccount groupEmailAccount = groupEmailAccountService.findByEmailAccountCode(groupEmail.getEmailAccountCode());
		try {
			mailSenderUntypedActor.sendEmail(groupEmail,groupEmailAccount);
			groupEmail.setEmailedDate(new Date());
			groupEmail.setEmailingError(null);
		} 
		catch(MailAuthenticationException me){
			logger.error("Authentication failed for Email Account Code - "+groupEmail.getEmailAccountCode());
			groupEmail.setEmailingError(StringUtils.isNotBlank(me.getMessage()) && me.getMessage().length()>210?me.getMessage().substring(0, 209):me.getMessage());
			groupEmailAccount.setLoginFailed(true);
			groupEmailAccount.setUpdatedAt(Calendar.getInstance().getTime());
			groupEmailAccount.setUpdatedBy(jobCode);
			groupEmailAccountService.update(groupEmailAccount);

		}
		catch (Exception e) {
			logger.error("Could not send email for "+groupEmail.getEmailAddress());
			groupEmail.setEmailingError(StringUtils.isNotBlank(e.getMessage()) && e.getMessage().length()>210?e.getMessage().substring(0, 209):e.getMessage());

		}
		map.put(String.valueOf(groupEmail.getGroupEmailId()), groupEmail);
		//return map;
		return groupEmail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.ItemStream#open(org.springframework.batch
	 * .item.ExecutionContext)
	 */
	public void open(ExecutionContext executionContext)
			throws ItemStreamException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.batch.item.ItemStream#update(org.springframework.
	 * batch.item.ExecutionContext)
	 */
	public void update(ExecutionContext executionContext)
			throws ItemStreamException {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.batch.item.ItemStream#close()
	 */
	public void close() throws ItemStreamException {
	}
}
