/**
 * 
 */
package com.yourpackagename.yourwebproject.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yourpackagename.framework.data.BaseJpaServiceImpl;
import com.yourpackagename.yourwebproject.actor.MailSenderUntypedActor;
import com.yourpackagename.yourwebproject.model.entity.GroupDependents;
import com.yourpackagename.yourwebproject.model.entity.GroupEmail;
import com.yourpackagename.yourwebproject.model.entity.GroupEmailActivity;
import com.yourpackagename.yourwebproject.model.entity.GroupEmailTemplate;
import com.yourpackagename.yourwebproject.model.entity.GroupEventInvite;
import com.yourpackagename.yourwebproject.model.entity.GroupEvents;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.entity.User;
import com.yourpackagename.yourwebproject.model.entity.enums.EmailActivity;
import com.yourpackagename.yourwebproject.model.repository.GroupEmailsRepository;
import com.yourpackagename.yourwebproject.service.GroupEmailActivityService;
import com.yourpackagename.yourwebproject.service.GroupEmailsService;
import com.yourpackagename.yourwebproject.service.GroupEventInviteService;

/**
 * @author mevan.d.souza
 *
 */
@Service
@Transactional
public class GroupEmailsServiceImpl extends
		BaseJpaServiceImpl<GroupEmail, String> implements GroupEmailsService {

	private @Autowired GroupEmailsRepository groupEmailsRepository;
	private @Autowired MailSenderUntypedActor mailSenderUntypedActor;
	private @Autowired GroupEventInviteService groupEventInviteService;
	private @Autowired GroupEmailActivityService groupEmailActivityService;
	private Logger log = LoggerFactory.getLogger(GroupEmailsServiceImpl.class);

	@PostConstruct
	public void setupService() {
		this.baseJpaRepository = groupEmailsRepository;
		this.entityClass = GroupEmail.class;
		this.baseJpaRepository.setupEntityClass(GroupEmail.class);

	}

	public List<GroupEmail> findEmailsToBeSent(String conversionToTimeZone) {

		return groupEmailsRepository.findEmailsToBeSent(conversionToTimeZone);
	}

	public List<GroupEmail> findEmailsByGroupCode(String groupCode) {
		return groupEmailsRepository.findEmailsByGroupCode(groupCode);
	}

	public List<GroupEmail> findEmailsByMemberCategoryCodeAndGroupEventCode(
			String memberCategoryCode, String groupEventCode) {
		return groupEmailsRepository
				.findEmailsByMemberCategoryCodeAndGroupEventCode(
						memberCategoryCode, groupEventCode);
	}

	public List<GroupEmail> findEmailsByGroupEventCode(String groupEventCode) {
		return groupEmailsRepository.findEmailsByGroupEventCode(groupEventCode);
	}

	public List<GroupEmail> findUnassignedEmailsByGroupCode(String groupCode) {

		return groupEmailsRepository.findUnassignedEmailsByGroupCode(groupCode);
	}

	public GroupEmail createEmail(GroupEmail groupEmail,
			Map<String, Object> modelMap) throws Exception {

		if (modelMap == null || modelMap.isEmpty()) {
			throw new Exception(
					"You have requested to create an email but have not passed the model map at all");
		}

		/* Get All Available object from the HashMap */
		GroupMember groupMember = (GroupMember) modelMap.get("groupMember");
		GroupEventInvite groupEventInvite = (GroupEventInvite) modelMap
				.get("groupEventInvite");
		GroupEvents groupEvent = (GroupEvents) modelMap.get("groupEvent");
		GroupEmailTemplate groupEmailTemplate = (GroupEmailTemplate) modelMap
				.get("groupEmailTemplate");
		User user = (User) modelMap.get("user");
		if (groupEmailTemplate == null || groupMember == null) {
			throw new Exception(
					"Both GroupMember and Email Template object should be present to send an email");
		}

		if (groupEventInvite != null
				&& (groupEventInvite.isInviteHeld() || !groupEventInvite
						.getGroupMember().isPrimaryEmailVerified())) {
			log.info("Skipping this invite "
					+ groupEventInvite.getGroupEventCode()
					+ " as it was marked as held or sender email was not verified: Invite Held-"
					+ groupEventInvite.isInviteHeld()
					+ " && Email verified-"
					+ groupEventInvite.getGroupMember()
							.isPrimaryEmailVerified() + " for Member - "
					+ groupEventInvite.getGroupMember().getFirstName() + " "
					+ groupEventInvite.getGroupMember().getLastName());
			return null;
		}

		// Handle if null email object is being passed
		if (groupEmail == null) {
			groupEmail = new GroupEmail();
		}
		groupEmail.setEmailAddress(groupMember.getPrimaryEmail());
		String ccEmail = "";
		if(groupMember.getGroupDependents()!=null){
		for (GroupDependents groupDependents : groupMember.getGroupDependents()) {
			if (StringUtils.isNotBlank(groupDependents.getEmail()))
				ccEmail += groupDependents.getEmail() + ",";
		}
		}
		if (StringUtils.isNotBlank(ccEmail)) {
			ccEmail = ccEmail.substring(0, ccEmail.length() - 1);
			groupEmail.setCcEmailAddress(ccEmail);
		}
		groupEmail.setBccEmailAddress(groupMember.getOtherEmail());
		groupEmail.setSubject(groupEmailTemplate.getSubject());
		groupEmail.setFromAlias(groupEmailTemplate.getFromAlias());
		groupEmail.setFromAliasPersonalString(groupEmailTemplate
				.getFromAliasPersonalString());
		groupEmail.setHtml(groupEmailTemplate.isHtml());
		groupEmail.setReplyToEmail(groupEmailTemplate.getReplyToEmail());
		groupEmail
				.setEmailAccountCode(groupEmailTemplate.getEmailAccountCode());

		/*
		 * If there are any attachments, just add it to the email Object now
		 */
		groupEmail.setAttachments(groupEmailTemplate.getAttachments());
		if (groupEmailTemplate.isPrefillAttachments()
				&& StringUtils.isNotBlank(groupEmailTemplate.getAttachments())) {
			try {
				ObjectMapper om = new ObjectMapper();
				groupEmail.setPrefillData(om.writeValueAsString(groupMember));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		// groupEmail.setCreatedBy(jobCode);
		groupEmail.setCreatedAt(new Date());
		// set the body to Template name intermittently
		groupEmail.setBody(groupEmailTemplate.getTemplateName());
		if(StringUtils.isNotBlank(groupMember.getSerialNumber())){
		groupEmail.setGroupMember(groupMember);}

		// Only the below when GroupEventInvite is not NULL
		boolean inviteHeld = false;
		if (groupEventInvite != null) {
			inviteHeld = groupEventInvite.isInviteHeld();
			groupEmail.setGroupEventInviteId(groupEventInvite
					.getGroupEventInviteId());
			Date emailExpdate = groupEvent != null ? groupEvent.getExpiryDate()
					: groupEventInvite.getInviteExpiryDate();
			if (groupEventInvite.getInviteExpiryDate() != null
					&& emailExpdate != null
					&& emailExpdate.after(groupEventInvite
							.getInviteExpiryDate())) {
				emailExpdate = groupEventInvite.getInviteExpiryDate();
			}
			groupEmail.setEmailExpirydate(emailExpdate);
			Date emailStartDate = groupEventInvite.getInviteStartDate();
			if (emailStartDate != null && emailExpdate != null
					&& emailStartDate.after(emailExpdate)) {
				emailStartDate = new DateTime(emailExpdate.getTime())
						.minusDays(45).toDate();
			}
			groupEmail.setEmailingDate(emailStartDate);
		}

		/*
		 * Intermittently set the hold email to true so that Other batches dont
		 * pick the email when the body is actually set as the template name
		 */

		groupEmail.setEmailHeld(true);
		groupEmail.setExpressEmail(groupEmailTemplate.isExpressEmail()
				&& StringUtils.isBlank(groupEmailTemplate.getAttachments()));
		GroupEmail newEmail = this.insert(groupEmail);
		GroupEmailActivity groupEmailActivity = new GroupEmailActivity();
		groupEmailActivity.setEmailActivity(EmailActivity.CREATE);
		groupEmailActivity.setActivityTime(groupEmail.getCreatedAt());
		groupEmailActivity.setActivityBy(user != null ? user.getUserName()
				: null);
		groupEmailActivity.setGroupEmail(newEmail);
		groupEmailActivityService.insert(groupEmailActivity);

		modelMap.put("groupEmail", newEmail);
		newEmail.setBody(mailSenderUntypedActor.prepareEmailBody(
				groupEmailTemplate.getTemplateName(), modelMap));
		newEmail.setEmailHeld(inviteHeld
				|| !groupMember.isPrimaryEmailVerified());
		this.insertOrUpdate(newEmail);

		GroupEmailActivity groupEmailActivity2 = new GroupEmailActivity();
		groupEmailActivity2.setEmailActivity(EmailActivity.UPDATE);
		groupEmailActivity2.setActivityTime(Calendar.getInstance().getTime());
		groupEmailActivity2.setActivityBy(user != null ? user.getUserName()
				: null);
		groupEmailActivity2.setGroupEmail(newEmail);
		groupEmailActivityService.insert(groupEmailActivity2);

		if (groupEventInvite != null) {
			// Need to update the emailing count
			if (!newEmail.isEmailHeld()) {
				try {
					groupEventInvite.setInviteSent(true);
					groupEventInvite.setUpdatedAt(Calendar.getInstance()
							.getTime());
					groupEventInvite.setInviteEmailCount(groupEventInvite
							.getInviteEmailCount() + 1);
					groupEventInvite =	groupEventInviteService.update(groupEventInvite);
				}

				catch (Exception e) {
					log.error("Error Updating the invitation Emailing Count");
				}
			}
		}
		return newEmail;
	}

}
