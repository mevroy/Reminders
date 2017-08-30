package com.yourpackagename.yourwebproject.common;

import com.yourpackagename.yourwebproject.api.common.ApiRoute;
import com.yourpackagename.yourwebproject.webapp.common.Route;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ImportResource;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

/**
 * Loaded from the property files
 *
 * @author: Y Kamesh Rao
 * @created: 4/14/12 3:07 PM
 * @company: &copy; 2012, Kaleidosoft Labs
 */
@ImportResource("classpath:config/spring/applicationContext-properties.xml")
public class Props {
    public @Value("http://localhost:8080/ywp/") String fHost;
    public @Value("/ywp/api") String fApiPath;
    public @Value("/ywp/") String fWebPath;
    public @Value("Australia") String fUserCountry;

    public @Value("mevroy@gmail.com") String fromAddress;
    public @Value("Please verify your email address.") String subVerificationEmail;
    public @Value("Welcome! Your email is successfully confirmed.") String subConfirmationEmail;
    public @Value("verifyMerchantEmail?token=") String verifyUrl;

    public List<String> webAuthRoutes;
    public List<String> apiAuthRoutes;

    public @Value("${jdbc.serverTimeZone}") String dbServerTimeZone;
    public @Value("${jdbc.timeZoneDifferenceinHoursWithRespectToAppServer}") String timeZoneDifferenceInHours;
    public @Value("${membershipInfo.responder.template}") String membershipResponderTemplate;
    public @Value("${membershipInfo.responder.template.excludeCategories}") String membershipExcludeCategories;
    public @Value("${registerInterest.invite.excludeCategories}") String registerInterestExcludeCategories;
    public @Value("${registerInterest.defaultResponse.template}") String registerInterestDefaultTemplate;
    
    public @Value("${cssSelectorValues}") String cssSelectorValues;
    public @Value("${eventTypeCategories}") String eventTypeCategories;
    public @Value("${homeTimeZone}") String homeTimeZone;
    public @Value("${hostTimeZone}") String hostTimeZone;
    public @Value("${membershipFilePath}") String membershipFilePath;
    public @Value("${application.url}") String applicationUrl;
    public @Value("${application.project}") String applicationProject;
    @PostConstruct
    public void init() {
        webAuthRoutes = Arrays.asList(
                fWebPath + Route.dashboard
        );

        apiAuthRoutes = Arrays.asList(
                fApiPath + ApiRoute.userController + ApiRoute.uRegister,
                fApiPath + ApiRoute.userController + ApiRoute.uLogin
        );
    }
}
