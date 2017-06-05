package com.yourpackagename.yourwebproject.scheduler.processors;

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
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInviteRSVP;
import com.yourpackagename.yourwebproject.model.entity.GroupEventPass;
import com.yourpackagename.yourwebproject.model.entity.GroupEvents;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.entity.enums.EmailActivity;
import com.yourpackagename.yourwebproject.service.GroupEmailActivityService;
import com.yourpackagename.yourwebproject.service.GroupEmailTemplateService;
import com.yourpackagename.yourwebproject.service.GroupEmailsService;
import com.yourpackagename.yourwebproject.service.GroupEventInviteRSVPService;
import com.yourpackagename.yourwebproject.service.GroupEventInviteService;
import com.yourpackagename.yourwebproject.service.GroupEventPassesService;
import com.yourpackagename.yourwebproject.service.GroupEventsService;

/**
 * EmailRetrievalItemProcessor
 * 
 */
@Component("processor")
@Scope("step")
public class GenericItemProcessor implements
		ItemProcessor<GroupMember, GroupEmail>, ItemStream {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(GenericItemProcessor.class);

	private @Autowired MailSenderUntypedActor mailSenderActor;
	private @Autowired GroupEmailTemplateService groupEmailTemplateService;
	private @Autowired GroupEventsService groupEventsService;
	private @Autowired GroupEventInviteService groupEventInviteService;
	private @Autowired GroupEventInviteRSVPService groupEventInviteRSVPService;
	private @Autowired GroupEmailsService groupEmailsService;
	private @Autowired GroupEventPassesService groupEventPassesService;
	private @Autowired GroupEmailActivityService groupEmailActivityService;

	@Value("#{jobParameters['templateName']}")
	private String templateName;

	@Value("#{jobParameters['runId']}")
	private String runId;

	@Value("#{jobParameters['jobCode']}")
	private String jobCode;

	@Value("#{jobParameters['groupEventCode']}")
	private String groupEventCode;

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
	public GroupEmail process(GroupMember groupMember) throws Exception {
		logger.trace("process() : ENTER");
		logger.info("Processing-->" + groupMember.getFirstName() + " "
				+ groupMember.getLastName() + ": Birthday:"
				+ groupMember.getBirthday());
		Map<String, Object> model = new HashMap<String, Object>();
		boolean isInvHeld = false;
		model.put("groupMember", groupMember);
		if (StringUtils.isBlank(templateName)) {
			throw new Exception(
					"Template Name is required. Please pass it as a job param 'templateName'"
							+ templateName);
		}
		GroupEmailTemplate gEmailTemplate = groupEmailTemplateService
				.findbyTemplateName(templateName);
		if (gEmailTemplate == null)
			throw new Exception(
					"Unable to locate a template for Template Name:"
							+ templateName);
		GroupEmail groupEmail = new GroupEmail();
		String eventCode = gEmailTemplate.getGroupEventCode();
		if (!StringUtils.isBlank(groupEventCode)) {
			eventCode = groupEventCode;
		}
		if (!StringUtils.isBlank(eventCode)) {
			GroupEvents gevents = groupEventsService
					.findByGroupEventCode(eventCode);
			if (gevents != null) {
				GroupEventInvite gei = groupEventInviteService
						.findByGroupMemberAndGroupEvent(groupMember, gevents);
				if (gei != null) {
					isInvHeld = gei.isInviteHeld();
					groupEmail.setGroupEventInviteId(gei
							.getGroupEventInviteId());
					model.put("groupEventInvite", gei);
					List<GroupEventPass> geps = groupEventPassesService.findByGroupEventInvite(gei);
					model.put("groupEventPasses", geps);
					
					List<GroupEventInviteRSVP> tempList = groupEventInviteRSVPService
							.findByGroupEventInvite(gei);
					if (tempList != null && tempList.size() > 0) {
						model.put("groupEventInviteRSVP", tempList.get(0));
					}
				}
			}
			model.put("groupEvent", gevents);
		}

		groupEmail.setEmailAddress(groupMember.getPrimaryEmail());
		groupEmail.setBccEmailAddress(groupMember.getOtherEmail());
		String ccEmail = "";
		for (GroupDependents groupDependents : groupMember.getGroupDependents()) {
			if (StringUtils.isNotBlank(groupDependents.getEmail()))
				ccEmail += groupDependents.getEmail() + ",";
		}
		if (StringUtils.isNotBlank(ccEmail)) {
			ccEmail = ccEmail.substring(0, ccEmail.length() - 1);
			groupEmail.setCcEmailAddress(ccEmail);
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
		groupEmail.setGroupMember(groupMember);
		groupEmail.setCreatedBy(jobCode);
		groupEmail.setCreatedAt(new Date());
		/*
		 * intermittently set the held to true so that other batches cannot pick
		 * this email until the body is actually updated
		 */
		groupEmail.setEmailHeld(true);
		groupEmail.setExpressEmail(gEmailTemplate.isExpressEmail() && StringUtils.isBlank(gEmailTemplate.getAttachments()));
		groupEmail.setBody(templateName);
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
		newEmail.setEmailHeld(isInvHeld || !groupMember.isPrimaryEmailVerified());
		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put(groupMember.getSerialNumber(), newEmail);

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
