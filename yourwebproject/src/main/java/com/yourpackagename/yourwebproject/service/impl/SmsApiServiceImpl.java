/**
 * 
 */
package com.yourpackagename.yourwebproject.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.yourpackagename.commons.util.CommonUtils;
import com.yourpackagename.yourwebproject.model.entity.SmsApiResponseEntity;
import com.yourpackagename.yourwebproject.model.entity.SmsMessageEntity;
import com.yourpackagename.yourwebproject.service.SmsApiService;

/**
 * @author mevan.d.souza
 *
 */
@Service
@Transactional
public class SmsApiServiceImpl implements SmsApiService {

	public static final String O_AUTH_URL_SMS = "https://api.telstra.com/v1/oauth/token";
	public static final String SEND_SMS = "https://api.telstra.com/v1/sms/messages";
	public static final String GET_SMS = "https://api.telstra.com/v1/sms/messages/";
	public static final String CLIENT_ID = "26Mtv5hgQnJKK2OlJwI5J0WIcXYAHxNr";
	public static final String CLIENT_SECRET = "VVwgmq9uGzIGCTxD";

	// public static final String EMAIL_URL =
	// "http://reminders-mrdapp.rhcloud.com/app/loadEmailWebversion/";

	public SmsApiResponseEntity sendSmsNotification(String phoneNumber,
			String content) throws Exception {
		// TODO Auto-generated method stub
		String token = this.obtainAuthorisationToken();
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setMessageConverters(Arrays.asList(
				new StringHttpMessageConverter(),
				new FormHttpMessageConverter(),
				new MappingJacksonHttpMessageConverter(),
				new ByteArrayHttpMessageConverter()));

		if (content.length() > 160) {
			content = content.substring(0, 157) + "...";
		}
		SmsMessageEntity sms = new SmsMessageEntity(phoneNumber, content);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + token);
		headers.setContentType(MediaType.APPLICATION_JSON);
		// HttpEntity<MultiValueMap<String, String>> request = new
		// HttpEntity<MultiValueMap<String, String>>(params, headers);
		HttpEntity<SmsMessageEntity> request = new HttpEntity<SmsMessageEntity>(
				sms, headers);
		ResponseEntity<SmsApiResponseEntity> st = restTemplate.exchange(
				SEND_SMS, HttpMethod.POST, request, SmsApiResponseEntity.class);
		//this.getSmsNotificationStatus(st.getBody().getMessageId());
		if(st!=null && st.getStatusCode().equals(HttpStatus.TOO_MANY_REQUESTS))
		{
			throw new Exception("SMS Quota reached. Please try again once the quota is reset!");
		}
		return st.getBody();
	}

	private String obtainAuthorisationToken() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setMessageConverters(Arrays.asList(
				new StringHttpMessageConverter(),
				new FormHttpMessageConverter(),
				new MappingJacksonHttpMessageConverter(),
				new ByteArrayHttpMessageConverter()));
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.set("client_id", CLIENT_ID);
		params.set("client_secret", CLIENT_SECRET);
		params.set("grant_type", "client_credentials");
		params.set("scope", "SMS");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
				params, headers);
		ResponseEntity<SmsApiResponseEntity> st = restTemplate.exchange(
				O_AUTH_URL_SMS, HttpMethod.POST, request,
				SmsApiResponseEntity.class);
		return st.getBody().getAccess_token();
	}

	public SmsApiResponseEntity getSmsNotificationStatus(String messageId) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setMessageConverters(Arrays.asList(
				new StringHttpMessageConverter(),
				new FormHttpMessageConverter(),
				new MappingJacksonHttpMessageConverter(),
				new ByteArrayHttpMessageConverter()));

		String token = this.obtainAuthorisationToken();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + token);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<SmsMessageEntity> request = new HttpEntity<SmsMessageEntity>(
				headers);
		ResponseEntity<SmsApiResponseEntity> st = restTemplate.exchange(GET_SMS
				+ messageId, HttpMethod.GET, request,
				SmsApiResponseEntity.class);
		return st.getBody();
	}

	public SmsApiResponseEntity sendSmsNotification(List<String> phoneNumbers,
			String content) throws Exception {

		for (String phoneNumber : phoneNumbers) {
			if (CommonUtils.isValidPhoneNumber(phoneNumber, "AU")) {
				this.sendSmsNotification(phoneNumber, content);
			}
		}
		return null;
	}

	/* POST */
	/*
	 * public String sendPushedNotification(String content, String groupEmailId)
	 * { RestTemplate restTemplate = new RestTemplate();
	 * restTemplate.setMessageConverters(Arrays.asList( new
	 * StringHttpMessageConverter(), new FormHttpMessageConverter()));
	 * MultiValueMap<String, String> params = new LinkedMultiValueMap<String,
	 * String>(); params.set("app_key", "9StMOb1K8uVsWMYGk7cA");
	 * params.set("app_secret",
	 * "ZNwhHoX3KMWWVAkYhed5gnq3wt8YWFX4vi17BElCC0hyKr8wqyfpFrxHvVvfP3rG");
	 * params.set("content", content); params.set("target_type", "app");
	 * params.set("content_type","url"); params.set("content_extra",
	 * "http://reminders-mrdapp.rhcloud.com/app/loadEmailWebversion/"
	 * +groupEmailId);
	 * 
	 * ResponseEntity<String> st = restTemplate.postForEntity( REST_SERVICE_URI,
	 * params, String.class);
	 * 
	 * return st.getBody(); // User user = new User(0,"Sarah",51,134); // URI
	 * uri = restTemplate.postForLocation(REST_SERVICE_URI+"/user/", // user,
	 * User.class); // System.out.println("Location : "+uri.toASCIIString()); }
	 * 
	 * POST public String sendPushedNotification(String content,
	 * GroupPushNotificationAccount groupPushNotficationAccount) { RestTemplate
	 * restTemplate = new RestTemplate();
	 * restTemplate.setMessageConverters(Arrays.asList( new
	 * StringHttpMessageConverter(), new FormHttpMessageConverter()));
	 * MultiValueMap<String, String> params = new LinkedMultiValueMap<String,
	 * String>(); params.set("app_key",
	 * groupPushNotficationAccount.getAppKey()); params.set("app_secret",
	 * groupPushNotficationAccount.getAppSecret()); params.set("content",
	 * content); params.set("target_type",
	 * groupPushNotficationAccount.getPushNotificationsTargetType());
	 * 
	 * ResponseEntity<String> st = restTemplate.postForEntity( REST_SERVICE_URI,
	 * params, String.class);
	 * 
	 * return st.getBody(); // User user = new User(0,"Sarah",51,134); // URI
	 * uri = restTemplate.postForLocation(REST_SERVICE_URI+"/user/", // user,
	 * User.class); // System.out.println("Location : "+uri.toASCIIString()); }
	 */

}
