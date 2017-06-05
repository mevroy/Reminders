/**
 * 
 */
package com.yourpackagename.yourwebproject.service;

import org.springframework.mail.MailAuthenticationException;

import com.yourpackagename.yourwebproject.model.entity.GroupEmail;
import com.yourpackagename.yourwebproject.model.entity.GroupEmailAccount;
import com.yourpackagename.yourwebproject.model.entity.User;

/**
 * @author mevan.d.souza
 *
 */
public interface MailSenderWebAPIService {

	public String sendEmail(final GroupEmail groupEmail, GroupEmailAccount groupEmailAccount) throws Exception, MailAuthenticationException;
	
	public String sendTestEmail(final User user, GroupEmailAccount groupEmailAccount) throws Exception, MailAuthenticationException;

	public String sendSmapleEmail(User users, GroupEmailAccount groupEmailAccount)
			throws Exception, MailAuthenticationException;
}
