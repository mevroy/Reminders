/**
 * 
 */
package com.yourpackagename.yourwebproject.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;

import javax.activation.URLDataSource;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.yourpackagename.commons.util.CommonUtils;
import com.yourpackagename.yourwebproject.model.entity.GroupEmail;
import com.yourpackagename.yourwebproject.model.entity.GroupEmailAccount;
import com.yourpackagename.yourwebproject.model.entity.GroupMember;
import com.yourpackagename.yourwebproject.model.entity.User;
import com.yourpackagename.yourwebproject.service.GroupMembersService;
import com.yourpackagename.yourwebproject.service.MailSenderWebAPIService;

/**
 * @author mevan.d.souza
 *
 */
@Service
@Transactional
public class MailSenderWebAPIServiceImpl implements MailSenderWebAPIService {

	private @Autowired GroupMembersService groupMembersService;
	public static final String REST_SERVICE_URI = "https://api.sendgrid.com/api/mail.send.json";

	public String sendEmail(GroupEmail groupEmail,
			GroupEmailAccount groupEmailAccount) throws Exception,
			MailAuthenticationException {
		
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
		
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setMessageConverters(Arrays.asList(
				new StringHttpMessageConverter(),
				new FormHttpMessageConverter(),
				new MappingJacksonHttpMessageConverter(),
				new ByteArrayHttpMessageConverter()));
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
		params.set("api_user", groupEmailAccount.getUsername());
		params.set("api_key", groupEmailAccount.getPassword());
		if (StringUtils.isNotBlank(groupEmail.getEmailAddress())) {
			for (String email : groupEmail.getEmailAddress().split(",")) {
				params.set("to[]", email);
			}
		}
		if (StringUtils.isNotBlank(groupEmail.getCcEmailAddress())) {
			for (String email : groupEmail.getCcEmailAddress().split(",")) {
				params.set("cc[]", email);
			}
		}

		if (StringUtils.isNotBlank(groupEmail.getBccEmailAddress())) {
			for (String email : groupEmail.getBccEmailAddress().split(",")) {
				params.set("bcc[]", email);
			}
		}
		// params.set("toname",);
		params.set("subject", groupEmail.getSubject());
		if (groupEmail.isHtml()) {
			params.set("html", groupEmail.getBody());
		} else {
			params.set("text", groupEmail.getBody());
		}

		if (StringUtils.isNotBlank(groupEmail.getFromAlias())) {
			params.set("from", groupEmail.getFromAlias());
			if (StringUtils.isNotBlank(groupEmail.getFromAliasPersonalString())) {
				params.set("fromname", groupEmail.getFromAliasPersonalString());
			}
		}

		if (StringUtils.isNotBlank(groupEmail.getReplyToEmail())) {
			params.set("replyto", groupEmail.getReplyToEmail());
		}
		
		if(StringUtils.isNotBlank(groupEmail.getAttachments()))
		{
			String[] files = groupEmail.getAttachments().split(",");
			for(String file : files)
			{
				String fileName = file.substring(file.lastIndexOf("/")+1);
				if(StringUtils.isNotBlank(groupEmail.getPrefillData())){
					
					try {
					ObjectMapper om = new ObjectMapper();
					GroupMember gm = om.readValue(groupEmail.getPrefillData().trim(), GroupMember.class);
					params.set("files["+fileName+"]", groupMembersService.prefillDefinedPDF(gm, file).toByteArray());
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				else 
				{
					params.set("files["+fileName+"]", CommonUtils.readFiletoOPStreamFromURL(file));
				}
			}
		}

		params.set("x-smtpapi", "{ \"unique_args\": {\"groupEmailId\": \""
				+ groupEmail.getGroupEmailId() + "\"} }");
		try {
			ResponseEntity<String> st = restTemplate.postForEntity(
					groupEmailAccount.getHost(), params, String.class);
			return st.getBody();
		} catch (Exception e) {
		}
		return "error";
	}

	public String sendTestEmail(User user, GroupEmailAccount groupEmailAccount)
			throws Exception, MailAuthenticationException {

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setMessageConverters(Arrays.asList(
				new StringHttpMessageConverter(),
				new FormHttpMessageConverter()));
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
		params.set("api_user", groupEmailAccount.getUsername());
		params.set("api_key", groupEmailAccount.getPassword());

		params.set("to", user.getEmail());

		// params.set("toname",);
		params.set("subject", "Test Email for Email Account with Code "
				+ groupEmailAccount.getEmailAccountCode() + " of Group "
				+ groupEmailAccount.getGroupCode());

		params.set(
				"text",
				"Dear "
						+ user.getUserName()
						+ ", \nThis is a test message confirming the success of new email account that you just set up for Send Grid Web API.");

		params.set("from", groupEmailAccount.getUsername());
		ResponseEntity<String> st = restTemplate.postForEntity(
				groupEmailAccount.getHost(), params, String.class);

		return st.getBody();

	}

	public String sendSmapleEmail(User users, GroupEmailAccount groupEmailAccount)
			throws Exception, MailAuthenticationException {

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setMessageConverters(Arrays.asList(
				new StringHttpMessageConverter(),
				new FormHttpMessageConverter()));
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<String, Object>();
		params.set("api_user", groupEmailAccount.getUsername());
		params.set("api_key", groupEmailAccount.getPassword());

		params.set("to", "mevroy@gmail.com");

		// params.set("toname",);
		params.set("subject", "Test Email for Email Account with Code "
				+ groupEmailAccount.getEmailAccountCode() + " of Group "
				+ groupEmailAccount.getGroupCode());

		params.set(
				"text",
				"Dear "
						
						+ ", \nThis is a test message confirming the success of new email account that you just set up for Send Grid Web API.");

		params.set("from", groupEmailAccount.getUsername());
		String file = "http://reminders-mrdapp.rhcloud.com/app/res/custom/files/Membership_Form_2015-16.pdf";
		params.set("files["+file.substring(file.lastIndexOf("/")+1)+"]", CommonUtils.readFiletoOPStreamFromURL(file));
		ResponseEntity<String> st = restTemplate.postForEntity(
				groupEmailAccount.getHost(), params, String.class);

		return st.getBody();

	}
	
}
