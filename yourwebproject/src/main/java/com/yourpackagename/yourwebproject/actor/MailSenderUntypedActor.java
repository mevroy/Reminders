package com.yourpackagename.yourwebproject.actor;

import java.util.Map;

import org.springframework.mail.MailAuthenticationException;

import com.yourpackagename.framework.exception.service.NotYetImplementedException;
import com.yourpackagename.yourwebproject.model.entity.GroupEmail;
import com.yourpackagename.yourwebproject.model.entity.GroupEmailAccount;
import com.yourpackagename.yourwebproject.model.entity.User;

/**
 * Typed Actor to offload the mail sending activity
 * of the current thread
 *
 * @author: Y Kamesh Rao
 * @created: 4/21/12 5:27 PM
 * @company: &copy; 2012, Kaleidosoft Labs
 */
public interface MailSenderUntypedActor {
    /**
     * Sends a mail to the user asking him/her to
     * verify his email id.
     *
     * @param user
     */
    public void sendUserEmailIdVerificationMail(final User user) throws NotYetImplementedException;


    /**
     * Sends a mail to the user informing him/her of successful
     * confirmation of their email address.
     *
     * @param user
     */
    public void sendUserEmailIdConfirmationMail(final User user);
    
    
	public void sendEmail(final GroupEmail groupEmail);
	
	public void sendEmail(final GroupEmail groupEmail, GroupEmailAccount groupEmailAccount) throws Exception, MailAuthenticationException;
	
	public void sendTestEmail(final User user, GroupEmailAccount groupEmailAccount) throws Exception, MailAuthenticationException;
	
	public String prepareEmailBody(final String templateName, Map<String, Object> model);
	
/*	public String preparePushNotificationsBody(final String templateName, Map<String, Object> model);*/
}
