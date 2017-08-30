package com.yourpackagename.yourwebproject.actor.impl;

import com.yourpackagename.framework.exception.service.NotYetImplementedException;
import com.yourpackagename.yourwebproject.actor.MailSenderUntypedActor;
import com.yourpackagename.yourwebproject.common.Props;
import com.yourpackagename.yourwebproject.model.entity.GroupEmail;
import com.yourpackagename.yourwebproject.model.entity.GroupEmailAccount;
import com.yourpackagename.yourwebproject.model.entity.User;
import com.yourpackagename.yourwebproject.service.MailSenderWebAPIService;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.MathTool;
import org.joda.time.DateTimeUtils;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.activation.URLDataSource;
import javax.mail.internet.MimeMessage;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

/**
 * Mail sender actor impl
 *
 * @author: Y Kamesh Rao
 * @created: 4/21/12 5:29 PM
 * @company: &copy; 2012, Kaleidosoft Labs
 */
public class MailSenderUntypedActorImpl implements MailSenderUntypedActor {
	private VelocityEngine velocityEngine;
	// private VelocityEngine velocityEnginePushNotifications;
	private JavaMailSenderImpl javaMailSender;
	private Props props;
	private MailSenderWebAPIService mailSenderWebAPIService;

	public void sendUserEmailIdVerificationMail(User user)
			throws NotYetImplementedException {
		throw new NotYetImplementedException("email verification");
	}

	public void sendUserEmailIdConfirmationMail(final User user) {
		Map model = new HashMap();
		model.put("user", user);

		MimeMessagePreparator preparator = prepareMail(user.getEmail(),
				props.fromAddress, props.subConfirmationEmail,
				// "velocity/confirmation.vm", model);
				"confirmation.vm", model);
		this.javaMailSender.send(preparator);
	}

	public void sendEmail(final GroupEmail groupEmail) {
		MimeMessagePreparator preparator = prepareMailAdvanced(
				groupEmail.getEmailAddress(), groupEmail.getFromAlias(),
				groupEmail.getFromAliasPersonalString(),
				groupEmail.getSubject(), groupEmail.getCcEmailAddress(),
				groupEmail.getBody(), groupEmail.getBccEmailAddress(),
				groupEmail.isHtml(), groupEmail.getReplyToEmail(),
				groupEmail.getAttachments());
		this.javaMailSender.send(preparator);

	}

	public void sendEmail(final GroupEmail groupEmail,
			GroupEmailAccount groupEmailAccount)
			throws MailAuthenticationException, Exception {

		if (isWebAPIEmail(groupEmailAccount.getHost())) {
			mailSenderWebAPIService.sendEmail(groupEmail, groupEmailAccount);
		} else {
			//Move this to a different Impl class
			this.sendSMTPEmail(groupEmail, groupEmailAccount);
		}

	}

	private void sendSMTPEmail(final GroupEmail groupEmail,
			GroupEmailAccount groupEmailAccount)
			throws MailAuthenticationException, Exception {

			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			if (groupEmailAccount == null)
				throw new Exception("Email Account :"
						+ groupEmail.getEmailAccountCode() + " not found");

			if (groupEmailAccount.isHoldEmailsOut()
					|| groupEmailAccount.isLoginFailed())
				throw new Exception(
						"Email Account barred from Sending Emails: HoldEmails-"
								+ groupEmailAccount.isHoldEmailsOut()
								+ " LoginFailed-"
								+ groupEmailAccount.isLoginFailed());

			javaMailSender.setProtocol(groupEmailAccount.getProtocol());
			javaMailSender.setHost(groupEmailAccount.getHost());
			javaMailSender.setUsername(groupEmailAccount.getUsername());
			javaMailSender.setPassword(groupEmailAccount.getPassword());
			javaMailSender.setPort(groupEmailAccount.getPort());
			javaMailSender.setJavaMailProperties(props);

			MimeMessagePreparator preparator = prepareMailAdvanced(
					groupEmail.getEmailAddress(), groupEmail.getFromAlias(),
					groupEmail.getFromAliasPersonalString(),
					groupEmail.getSubject(), groupEmail.getCcEmailAddress(),
					groupEmail.getBody(), groupEmail.getBccEmailAddress(),
					groupEmail.isHtml(), groupEmail.getReplyToEmail(),
					groupEmail.getAttachments());
			this.javaMailSender.send(preparator);

	}
	public MimeMessagePreparator prepareMail(final String to,
			final String from, final String subject, final String template,
			final Map model) {
		return new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setTo(to);
				message.setFrom(from);
				message.setSubject(subject);
				String text = VelocityEngineUtils.mergeTemplateIntoString(
						velocityEngine, template, model);
				message.setText(text, true);
			}
		};
	}

	public MimeMessagePreparator prepareMailAdvanced(final String to,
			final String from, final String fromAliasStringPersonal,
			final String subject, final String cc, final String body,
			final String bcc, final boolean html, final String replyTo,
			final String attachments) {
		return new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage,
						true);
				if (!StringUtils.isBlank(to)) {
					message.setTo(to);
				}
				if (!StringUtils.isBlank(to)) {
					String[] toArray = to.split(",");
					if (toArray != null && toArray.length > 0)
						message.setTo(toArray);
				}

				if (!StringUtils.isBlank(from)
						&& !StringUtils.isBlank(fromAliasStringPersonal))
					message.setFrom(from, fromAliasStringPersonal);
				else
					message.setFrom(from);
				message.setSubject(subject);
				if (cc != null && cc.trim().length() > 0) {
					String[] cCArray = cc.split(",");
					if (cCArray != null && cCArray.length > 0)
						message.setCc(cCArray);
				}
				if (bcc != null && bcc.trim().length() > 0) {
					String[] bCcArray = bcc.split(",");
					if (bCcArray != null && bCcArray.length > 0)
						message.setBcc(bCcArray);
				}
				if (replyTo != null && replyTo.trim().length() > 0)
					message.setReplyTo(replyTo);
				message.setText(body, html);
				if (StringUtils.isNotBlank(attachments)) {
					String[] attachmentsArray = attachments.split(",");
					if (attachmentsArray != null && attachmentsArray.length > 0) {
						for (String attachment : attachmentsArray) {
							URLDataSource file = new URLDataSource(new URL(
									attachment));
							message.addAttachment(file.getName(), file);
						}
					}
				}
			}
		};
	}

	public String prepareEmailBody(String templateName,
			Map<String, Object> model) {

		/* Adding some helping classes into the classes for use in the templates */
		if (model != null) {
			model.put("DateTool", new DateTool());
			model.put("MathTool", new MathTool());
			model.put("StringUtils", StringUtils.class);
			model.put("homeTimeZone", TimeZone.getTimeZone(props.homeTimeZone));
			model.put("hostTimeZone", TimeZone.getTimeZone(props.hostTimeZone));
			model.put("url",props.applicationUrl);
			model.put("project", props.applicationProject);
			// model.put("DateTimeUtils", DateTimeUtils.class);
		}
		String text = VelocityEngineUtils.mergeTemplateIntoString(
				velocityEngine, templateName, model);
		return text;
	}

	private boolean isWebAPIEmail(String hostName) {
		if (StringUtils.isNotBlank(hostName)
				&& StringUtils.startsWithIgnoreCase(hostName, "http")) {
			return true;
		}
		return false;
	}

	/*
	 * public String preparePushNotificationsBody(String templateName,
	 * Map<String, Object> model) {
	 * 
	 * Adding some helping classes into the classes for use in the templates
	 * if(model!=null) { model.put("DateTool", new DateTool());
	 * model.put("MathTool", new MathTool()); model.put("StringUtils",
	 * StringUtils.class); model.put("homeTimeZone",
	 * TimeZone.getTimeZone(props.homeTimeZone)); model.put("hostTimeZone",
	 * TimeZone.getTimeZone(props.hostTimeZone)); //model.put("DateTimeUtils",
	 * DateTimeUtils.class); } String text =
	 * VelocityEngineUtils.mergeTemplateIntoString(
	 * velocityEnginePushNotifications, templateName, model); return text; }
	 */
	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	/*
	 * public JavaMailSender getJavaMailSender() { return javaMailSender; }
	 * 
	 * public void setJavaMailSender(JavaMailSender javaMailSender) {
	 * this.javaMailSender = javaMailSender; }
	 */

	public Props getProps() {
		return props;
	}

	public void setProps(Props props) {
		this.props = props;
	}

	/**
	 * @return the javaMailSender
	 */
	public JavaMailSenderImpl getJavaMailSender() {
		return javaMailSender;
	}

	/**
	 * @param javaMailSender
	 *            the javaMailSender to set
	 */
	public void setJavaMailSender(JavaMailSenderImpl javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	/**
	 * @return the mailSenderWebAPIService
	 */
	public MailSenderWebAPIService getMailSenderWebAPIService() {
		return mailSenderWebAPIService;
	}

	/**
	 * @param mailSenderWebAPIService
	 *            the mailSenderWebAPIService to set
	 */
	public void setMailSenderWebAPIService(
			MailSenderWebAPIService mailSenderWebAPIService) {
		this.mailSenderWebAPIService = mailSenderWebAPIService;
	}

	public void sendTestEmail(User user, GroupEmailAccount groupEmailAccount)
			throws Exception, MailAuthenticationException {

		if (isWebAPIEmail(groupEmailAccount.getHost())) {
			mailSenderWebAPIService.sendTestEmail(user, groupEmailAccount);
		} else {
			if (groupEmailAccount == null)
				throw new Exception("Email Account not found");

			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			javaMailSender.setProtocol(groupEmailAccount.getProtocol());
			javaMailSender.setHost(groupEmailAccount.getHost());
			javaMailSender.setUsername(groupEmailAccount.getUsername());
			javaMailSender.setPassword(groupEmailAccount.getPassword());
			javaMailSender.setPort(groupEmailAccount.getPort());
			javaMailSender.setJavaMailProperties(props);

			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setFrom(groupEmailAccount.getUsername());
			simpleMailMessage.setTo(user.getEmail());
			simpleMailMessage
					.setSubject("Test Email for Email Account with Code "
							+ groupEmailAccount.getEmailAccountCode()
							+ " of Group " + groupEmailAccount.getGroupCode());
			simpleMailMessage
					.setText("Dear "
							+ user.getUserName()
							+ ", \nThis is a test message confirming the success of new email account that you just set up.");
			javaMailSender.send(simpleMailMessage);
		}
	}

	/*	*//**
	 * @return the velocityEnginePushNotifications
	 */
	/*
	 * public VelocityEngine getVelocityEnginePushNotifications() { return
	 * velocityEnginePushNotifications; }
	 *//**
	 * @param velocityEnginePushNotifications
	 *            the velocityEnginePushNotifications to set
	 */
	/*
	 * public void setVelocityEnginePushNotifications( VelocityEngine
	 * velocityEnginePushNotifications) { this.velocityEnginePushNotifications =
	 * velocityEnginePushNotifications; }
	 */

}
