/**
 * 
 */
package com.yourpackagename.yourwebproject.service;

import com.yourpackagename.yourwebproject.model.entity.GroupPushNotificationAccount;

/**
 * @author mevan.d.souza
 *
 */
public interface PushedApiService {

	public String sendPushedNotification(String content, GroupPushNotificationAccount groupPushNotficationAccount);
	public String sendPushedNotification(String content, String groupEmailId);
}
