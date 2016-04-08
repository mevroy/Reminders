package com.yourpackagename.yourwebproject.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ImportResource;

/**
 * @author: Y Kamesh Rao
 * @created: 4/14/12 3:10 PM
 * @company: &copy; 2012, Kaleidosoft Labs
 */
@ImportResource("classpath:config/spring/applicationContext-properties.xml")
public class Message {
    public @Value("You have successfully registered with <Your-Web-Project>.") String registerSuccess;
    public @Value("Registration failed due to errors.") String registerError;
    public @Value("<strong>Welcome back!</strong> You have successfully logged into your <Your-Web-Project> account.") String loginSuccess;
    public @Value("Login failed due to errors.") String loginError;
    public @Value("Please click the link sent in an email to you for verifying your email id.") String resendEmail;
    public @Value("<strong>Congratulations!</strong> We have successfully verified your email id.") String emailCnfSuccess;
    public @Value("Username already taken") String userExists;
    public @Value("Email already taken.") String emailExists;

    public @Value("User with the entered unique code does not exist.") String aUserNotFound;
    public @Value("4003") int aUserNotFoundCode;
    public @Value("Invalid username & password combination. Authentication failed.") String aFailed;
    public @Value("4001") int aFailedCode;
    public @Value("${auth.parametersMissing}") String aParamMissing;
    public @Value("4002") int aParamMissingCode;

    public static final String invLatitude = "Invalid latitude";
    public static final String invLongitude = "Invalid longitude";
}
