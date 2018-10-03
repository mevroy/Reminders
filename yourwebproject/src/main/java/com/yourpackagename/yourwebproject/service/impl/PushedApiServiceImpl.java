/**
 * 
 */
package com.yourpackagename.yourwebproject.service.impl;

import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.yourpackagename.yourwebproject.model.entity.GroupPushNotificationAccount;
import com.yourpackagename.yourwebproject.service.PushedApiService;

/**
 * @author mevan.d.souza
 *
 */
@Service
@Transactional
public class PushedApiServiceImpl implements PushedApiService{

	public static final String REST_SERVICE_URI = "https://api.pushed.co/1/push";
	public static final String EMAIL_URL = "http://reminders-mrdapp.rhcloud.com/app/loadEmailWebversion/";

	/* POST */
	public String sendPushedNotification(String content, String groupEmailId) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setMessageConverters(Arrays.asList(
				new StringHttpMessageConverter(),
				new FormHttpMessageConverter()));
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.set("app_key", "9StMOb1K8uVsWMYGk7cA");
		params.set("app_secret",
				"ZNwhHoX3KMWWVAkYhed5gnq3wt8YWFX4vi17BElCC0hyKr8wqyfpFrxHvVvfP3rG");
		params.set("content", content);
		params.set("target_type", "app");
		params.set("content_type","url");
		params.set("content_extra", "http://mevroy-reminders.1d35.starter-us-east-1.openshiftapps.com/app/loadEmailWebversion/"+groupEmailId);

		ResponseEntity<String> st = restTemplate.postForEntity(
				REST_SERVICE_URI, params, String.class);

		return st.getBody();
		// User user = new User(0,"Sarah",51,134);
		// URI uri = restTemplate.postForLocation(REST_SERVICE_URI+"/user/",
		// user, User.class);
		// System.out.println("Location : "+uri.toASCIIString());
	}
	
	/* POST */
	public String sendPushedNotification(String content, GroupPushNotificationAccount groupPushNotficationAccount) {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setMessageConverters(Arrays.asList(
				new StringHttpMessageConverter(),
				new FormHttpMessageConverter()));
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.set("app_key", groupPushNotficationAccount.getAppKey());
		params.set("app_secret",
				groupPushNotficationAccount.getAppSecret());
		params.set("content", content);
		params.set("target_type", groupPushNotficationAccount.getPushNotificationsTargetType());

		ResponseEntity<String> st = restTemplate.postForEntity(
				REST_SERVICE_URI, params, String.class);

		return st.getBody();
		// User user = new User(0,"Sarah",51,134);
		// URI uri = restTemplate.postForLocation(REST_SERVICE_URI+"/user/",
		// user, User.class);
		// System.out.println("Location : "+uri.toASCIIString());
	}


}
