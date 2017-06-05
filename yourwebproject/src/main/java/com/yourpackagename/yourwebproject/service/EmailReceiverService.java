/**
 * 
 */
package com.yourpackagename.yourwebproject.service;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yourpackagename.yourwebproject.actor.MailSenderUntypedActor;
import com.yourpackagename.yourwebproject.common.Props;
import com.yourpackagename.yourwebproject.model.entity.GroupDependents;
import com.yourpackagename.yourwebproject.model.entity.GroupEmail;
import com.yourpackagename.yourwebproject.model.entity.GroupEmailActivity;
import com.yourpackagename.yourwebproject.model.entity.GroupEmailTemplate;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupEvents;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.entity.enums.EmailActivity;

/**
 * @author mevan.d.souza
 *
 */
public class EmailReceiverService {
	private Logger log = LoggerFactory.getLogger(EmailReceiverService.class);
	private boolean textIsHtml = false;
	private @Autowired GroupMembersService groupMemebersService;
	private @Autowired Props props;
	private @Autowired MailSenderUntypedActor mailSenderUntypedActor;
	private @Autowired GroupEmailsService groupEmailsService;
	private @Autowired GroupEmailTemplateService groupEmailTemplateService;
	private @Autowired GroupEmailActivityService groupEmailActivityService;

	/**
	 * Return the primary text content of the message.
	 */
	private String getText(Part p) throws MessagingException, IOException {
		if (p.isMimeType("text/*")) {
			String s = (String) p.getContent();
			textIsHtml = p.isMimeType("text/html");
			return s;
		}

		if (p.isMimeType("multipart/alternative")) {
			// prefer html text over plain text
			Multipart mp = (Multipart) p.getContent();
			String text = null;
			for (int i = 0; i < mp.getCount(); i++) {
				Part bp = mp.getBodyPart(i);
				if (bp.isMimeType("text/plain")) {
					if (text == null)
						text = getText(bp);
					continue;
				} else if (bp.isMimeType("text/html")) {
					String s = getText(bp);
					if (s != null)
						return s;
				} else {
					return getText(bp);
				}
			}
			return text;
		} else if (p.isMimeType("multipart/*")) {
			Multipart mp = (Multipart) p.getContent();
			for (int i = 0; i < mp.getCount(); i++) {
				String s = getText(mp.getBodyPart(i));
				if (s != null)
					return s;
			}
		}

		return null;
	}

	/*
	 * public void receive(MimeMessage mimeMessage) throws IOException,
	 * MessagingException {
	 * System.out.println("Got s message from "+mimeMessage.
	 * getFrom()[0]+" mate!!!!"); Object content = mimeMessage.getContent(); //
	 * Part part = (Part)mimeMessage.getContent(); if (content instanceof
	 * Multipart) { Multipart mp = (Multipart) content; for (int i = 0; i <
	 * mp.getCount(); i++) { BodyPart bp = mp.getBodyPart(i); if (Pattern
	 * .compile(Pattern.quote("text/html"), Pattern.CASE_INSENSITIVE)
	 * .matcher(bp.getContentType()).find()) { // found html part
	 * System.out.println((String) bp.getContent()); } else { // some other
	 * bodypart... } } } // System.out.println(getText(part)); }
	 */

	public void receive(MimeMessage mimeMessage) throws Exception {

		String templateName = props.membershipResponderTemplate;
		List<GroupMember> groupMembers = groupMemebersService
				.findByAssociatedEmailForGroupMember(
						((InternetAddress) mimeMessage.getFrom()[0])
								.getAddress(), "MKC");

		for (GroupMember groupMember : groupMembers) {
			if (StringUtils.isNotBlank(groupMember.getMemberCategoryCode())
					&& StringUtils
							.isNotBlank(props.membershipExcludeCategories)
					&& props.membershipExcludeCategories.indexOf(groupMember
							.getMemberCategoryCode()) > -1) {
				log.info("Skipping " + groupMember.getFirstName() + " "
						+ groupMember.getLastName()
						+ " as it is an excluded member category:"
						+ groupMember.getMemberCategoryCode());
			} else {
				Map<String, Object> model = new HashMap<String, Object>();
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
				// DOnt send membership email to evryone. Just to the one who
				// asked for it.

				groupEmail.setEmailAddress(((InternetAddress) mimeMessage
						.getFrom()[0]).getAddress());
				/*
				 * groupEmail.setBccEmailAddress(groupMember.getOtherEmail());
				 * String ccEmail = ""; for (GroupDependents groupDependents :
				 * groupMember.getGroupDependents()) { if
				 * (StringUtils.isNotBlank(groupDependents.getEmail())) ccEmail
				 * += groupDependents.getEmail() + ","; } if
				 * (StringUtils.isNotBlank(ccEmail)) { ccEmail =
				 * ccEmail.substring(0, ccEmail.length() - 1);
				 * groupEmail.setCcEmailAddress(ccEmail); }
				 */
				groupEmail.setSubject(gEmailTemplate.getSubject());
				groupEmail.setFromAlias(gEmailTemplate.getFromAlias());
				groupEmail.setFromAliasPersonalString(gEmailTemplate
						.getFromAliasPersonalString());
				groupEmail.setHtml(gEmailTemplate.isHtml());
				groupEmail.setReplyToEmail(gEmailTemplate.getReplyToEmail());
				groupEmail.setEmailAccountCode(gEmailTemplate
						.getEmailAccountCode());
				/*
				 * If there are any attachments, just add it to the email Object
				 * now
				 */
				groupEmail.setAttachments(gEmailTemplate.getAttachments());
				groupEmail.setGroupMember(groupMember);
				groupEmail.setCreatedBy("AUTO_MEMBERSHIP_RESPONDER");
				groupEmail.setCreatedAt(new Date());
				/*
				 * intermittently set the held to true so that other batches
				 * cannot pick this email until the body is actually updated
				 */
				groupEmail.setEmailHeld(true);
				groupEmail.setExpressEmail(gEmailTemplate.isExpressEmail() && StringUtils.isBlank(gEmailTemplate.getAttachments()));//Making it a express delivery
				groupEmail.setBody(templateName);
				
				/*
				 * Insert email here so that Email ID is obtained which can be
				 * used for email Tracking purpose
				 */
				GroupEmail newEmail = groupEmailsService.insert(groupEmail);
				
				GroupEmailActivity groupEmailActivity = new GroupEmailActivity();
				groupEmailActivity.setEmailActivity(EmailActivity.CREATE);
				groupEmailActivity.setActivityTime(groupEmail.getCreatedAt());
				groupEmailActivity.setActivityBy(groupMember.getFirstName()+" "+groupMember.getLastName());
				groupEmailActivity.setGroupEmail(newEmail);
				groupEmailActivityService.insert(groupEmailActivity);
				
				model.put("groupEmail", newEmail);
				newEmail.setBody(mailSenderUntypedActor.prepareEmailBody(
						templateName, model));
				newEmail.setEmailHeld(false);
				newEmail = groupEmailsService.update(newEmail);
				GroupEmailActivity groupEmailActivity2 = new GroupEmailActivity();
				groupEmailActivity2.setEmailActivity(EmailActivity.UPDATE);
				groupEmailActivity2.setActivityTime(Calendar.getInstance().getTime());
				groupEmailActivity2.setActivityBy(groupMember.getFirstName()+" "+groupMember.getLastName());
				groupEmailActivity2.setGroupEmail(newEmail);
				groupEmailActivityService.insert(groupEmailActivity2);
				// Map<String, Object> map = new HashMap<String, Object>();
				// map.put(groupMember.getSerialNumber(), newEmail);

			}
		}

	}
}
