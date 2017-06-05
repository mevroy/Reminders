package com.yourpackagename.yourwebproject.scheduler.processors;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

import com.yourpackagename.yourwebproject.actor.MailSenderActor;
import com.yourpackagename.yourwebproject.actor.MailSenderUntypedActor;
import com.yourpackagename.yourwebproject.model.entity.GroupDependents;
import com.yourpackagename.yourwebproject.model.entity.GroupEmail;
import com.yourpackagename.yourwebproject.model.entity.GroupEmailActivity;
import com.yourpackagename.yourwebproject.model.entity.GroupEmailTemplate;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.entity.enums.EmailActivity;
import com.yourpackagename.yourwebproject.service.GroupEmailActivityService;
import com.yourpackagename.yourwebproject.service.GroupEmailTemplateService;
import com.yourpackagename.yourwebproject.service.GroupEmailsService;
import com.yourpackagename.yourwebproject.service.GroupEventsService;
import com.yourpackagename.yourwebproject.service.PushedApiService;

/**
 * EmailRetrievalItemProcessor
 * 
 */
@Component("listProcessor")
@Scope("step")
public class GenericListProcessor implements
		ItemProcessor<List<GroupMember>, GroupEmail>, ItemStream {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(GenericListProcessor.class);

	private @Autowired MailSenderUntypedActor mailSenderActor;
	private @Autowired GroupEmailTemplateService groupEmailTemplateService;
	private @Autowired GroupEventsService groupEventsService;
	private @Autowired GroupEmailsService groupEmailsService;
	private @Autowired PushedApiService pushedApiService;
	private @Autowired GroupEmailActivityService groupEmailActivityService;

	@Value("#{jobParameters['templateName']}")
	private String templateName;

	@Value("#{jobParameters['runId']}")
	private String runId;

	@Value("#{jobParameters['jobCode']}")
	private String jobCode;

	@Value("#{jobParameters['emailAddress']}")
	private String emailAddress;

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
	public GroupEmail process(List<GroupMember> groupMembers) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("groupMembers", groupMembers);
		if (StringUtils.isBlank(templateName)
				|| StringUtils.isBlank(emailAddress)) {
			throw new Exception(
					"Template Name and Email Address are required. Please pass it as a job param 'templateName' and 'emailAddress'");
		}
		GroupEmailTemplate gEmailTemplate = groupEmailTemplateService
				.findbyTemplateName(templateName);
		if (gEmailTemplate == null)
			throw new Exception(
					"Unable to locate a template for Template Name:"
							+ templateName);
		if (!StringUtils.isBlank(gEmailTemplate.getGroupEventCode())) {
			model.put("groupEvent", groupEventsService
					.findByGroupEventCode(gEmailTemplate.getGroupEventCode()));
		}
		GroupEmail groupEmail = new GroupEmail();
		if (StringUtils.isNotBlank(emailAddress)) {
			String[] cCArray = emailAddress.trim().split(",");
			if (cCArray != null && cCArray.length > 1) {
				groupEmail.setEmailAddress(cCArray[0]);
				groupEmail.setCcEmailAddress(emailAddress.trim());
			} else {
				groupEmail.setEmailAddress(emailAddress.trim());
			}
		}

		groupEmail.setSubject(gEmailTemplate.getSubject());
		groupEmail.setFromAlias(gEmailTemplate.getFromAlias());
		groupEmail.setFromAliasPersonalString(gEmailTemplate
				.getFromAliasPersonalString());
		groupEmail.setHtml(gEmailTemplate.isHtml());
		groupEmail.setReplyToEmail(gEmailTemplate.getReplyToEmail());
		groupEmail.setEmailAccountCode(gEmailTemplate.getEmailAccountCode());
		/* If there are any attachments, just add it to the email Object now */
		groupEmail.setAttachments(gEmailTemplate.getAttachments());
		groupEmail.setCreatedBy(jobCode);
		groupEmail.setCreatedAt(new Date());
		/*
		 * intermittently set the held to true so that other batches cannot pick
		 * this email until the body is actually updated
		 */
		groupEmail.setEmailHeld(true);
		groupEmail.setBody(templateName);
		groupEmail.setExpressEmail(gEmailTemplate.isExpressEmail()
				&& StringUtils.isBlank(gEmailTemplate.getAttachments()));
		/*
		 * Insert email here so that Email ID is obtained which can be used for
		 * email Tracking purpose
		 */

		GroupEmail newEmail = groupEmailsService.insert(groupEmail);
		GroupEmailActivity groupEmailActivity = new GroupEmailActivity();
		groupEmailActivity.setEmailActivity(EmailActivity.CREATE);
		groupEmailActivity.setActivityTime(groupEmail.getCreatedAt());
		groupEmailActivity.setActivityBy(jobCode);
		groupEmailActivity.setGroupEmail(newEmail);
		groupEmailActivityService.insert(groupEmailActivity);
		model.put("groupEmail", newEmail);
		newEmail.setBody(mailSenderActor.prepareEmailBody(templateName, model));
		newEmail.setEmailHeld(false);
		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put(groupMember.getSerialNumber(), newEmail);

		try {
			// String pushNotifBody =
			// mailSenderActor.preparePushNotificationsBody(templateName,
			// model);
			String pushNotifBody = "";
			int i = 1;
			for (GroupMember gm : groupMembers) {
				pushNotifBody += i
						+ ") "
						+ (StringUtils.isNotBlank(gm.getAliasName()) ? gm
								.getAliasName() : gm.getFirstName() + " "
								+ gm.getLastName())+", ";
				i++;
			}
			if (StringUtils.isNotBlank(pushNotifBody)) {
				logger.info(pushNotifBody);
				if(pushNotifBody.length()>140)
				{
					pushNotifBody = pushNotifBody.substring(0, 136)+"...";
				}
				pushedApiService.sendPushedNotification(pushNotifBody, newEmail.getGroupEmailId());
				
			}
				
		} catch (Exception e) {
			logger.info("Exception while doing Push:"+e);
		}

		return newEmail;
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
