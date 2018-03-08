package com.yourpackagename.yourwebproject.scheduler.processors;


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
import org.springframework.stereotype.Component;

import com.yourpackagename.commons.util.CommonUtils;
import com.yourpackagename.framework.exception.validation.PhoneNumberValidationException;
import com.yourpackagename.framework.exception.validation.ValidationException;
import com.yourpackagename.yourwebproject.model.entity.GroupSMS;
import com.yourpackagename.yourwebproject.model.entity.SmsApiResponseEntity;
import com.yourpackagename.yourwebproject.service.SmsApiService;

/**
 * SMSItemProcessor
 * 
 */
@Component("smsProcessor")
@Scope("step")
public class SMSOutItemProcessor implements ItemProcessor<GroupSMS, GroupSMS>,
		ItemStream {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(SMSOutItemProcessor.class);

	@Value("#{jobParameters['runId']}")
	private String runId;

	@Value("#{jobParameters['jobCode']}")
	private String jobCode;

	private @Autowired SmsApiService SmsApiService;


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
	public GroupSMS process(GroupSMS groupSMS) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		groupSMS.setUpdatedBy(jobCode);

		try {
			if(!CommonUtils.isValidPhoneNumber(groupSMS.getMobileNumber(), "AU"))
			{
				
				throw new PhoneNumberValidationException("Invalid Phone number - "+groupSMS.getMobileNumber());
			}
			SmsApiResponseEntity response = SmsApiService.sendSmsNotification(groupSMS.getMobileNumber(), groupSMS.getBody());
			if(response!=null && response.getMessages()!=null && response.getMessages().length>0)
			{
				groupSMS.setProviderMessageId(response.getMessages()[0].getMessageId());
				groupSMS.setSmsedDate(new Date());
			}
			
			if(response.getStatus()>0){
			groupSMS.setSmsError(response.getStatus()+"-"+response.getMessage());}
			
		} 

		catch (PhoneNumberValidationException e) {
			groupSMS.setSmsHeld(true);
			logger.error("Could not send sms for " + groupSMS.getMobileNumber()+" and hence its HELD");
			groupSMS.setSmsError(StringUtils.isNotBlank(e.getMessage())
					&& e.getMessage().length() > 210 ? e.getMessage()
					.substring(0, 209) : e.getMessage());

		}
		
		catch (Exception e) {
			groupSMS.setSmsHeld(true);
			logger.error("Could not send sms for " + groupSMS.getMobileNumber());
			groupSMS.setSmsError(StringUtils.isNotBlank(e.getMessage())
					&& e.getMessage().length() > 210 ? e.getMessage()
					.substring(0, 209) : e.getMessage());

		}
		map.put(String.valueOf(groupSMS.getGroupSmsId()), groupSMS);
		// return map;
		return groupSMS;
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
