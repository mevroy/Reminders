package com.yourpackagename.yourwebproject.api.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ImportResource;

/**
 * Class to load the property file into
 * class variable using Spring configuration
 *
 * @author: Y Kamesh Rao
 * @created: 3/17/12 10:01 PM
 * @company: &copy; 2012, Kaleidosoft Labs
 */
@ImportResource(value = "classpath:/config/spring/applicationContext-properties.xml")
public class HttpError {

    // Common HTTP errors
    public @Value("Bad Request") String brMsg;
    public @Value("400") int brCode;
    public @Value("Unauthorised") String uaMsg;
    public @Value("401") int uaCode;
    public @Value("Not found") String nfMsg;
    public @Value("404") int nfCode;
    public @Value("HTTP Request Method Not Allowed") String mnaMsg;
    public @Value("405") int mnaCode;
    public @Value("Request Timeout") String rtMsg;
    public @Value("408") int rtCode;
    public @Value("Unsupported Media Type (Your accept/content-type request header and data in the request body do not match)") String umtMsg;
    public @Value("415") int umtCode;
    public @Value("Internal Server error") String iseMsg;
    public @Value("500") int iseCode;
    public @Value("Not Implemented") String niMsg;
    public @Value("501") int niCode;
    public @Value("Service Unavailable") String suMsg;
    public @Value("503") int suCode;
}