package com.yourpackagename.yourwebproject.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ImportResource;

/**
 * Key constants to be used in the code
 *
 * @author: Y Kamesh Rao
 * @created: 3/23/12 9:00 AM
 * @company: &copy; 2012, Kaleidosoft Labs
 */
@ImportResource("classpath:config/spring/applicationContext-properties.xml")
public class Key {
    public static final String redirect = "redirect:";
    public static final String response = "response";
    public static final String user = "user";
    public static final String role = "role";
    public static final String group = "group";
    public static final String address = "address";
    public static final String isLogin = "ls";
    public static final String isRegister = "rs";
    public static final String isEmailConfirmed = "ecs";

    public static final String regUserForm = "registerUserForm";
    public static final String loginUserForm = "loginUserForm";
    public static final String token = "token";

    //public static final String userInSession = "com.yourpackagename.user";
    public static final String userInSession = "user";
    public static final String formEncoded = "application/x-www-form-urlencoded";

    public static final String groupCode = "groupCode";
    public static final String groupName = "groupName";
    public static final String serialNumber = "serialNumber";
    public static final String groupMember = "groupMember";
    public static final String startTime = "startTime";
    public static final String groupMainLinks = "groupMainLinks";
    public static final String ADD = "add";
    public static final String UPDATE = "update";
    public static final String DELETE = "delete";
    public static final String SUCCESS = "success";
    public static final String barcodeInputConstants = "STRING DOUBLE LONG";
    public static final String input = "input";
    public static final String text = "text";
    public static final String type = "type";
    public @Value("User not found") String unfMsg;

    public @Value("500") int iseCode;
    public @Value("1000") int vdnCode;
    public @Value("1001") int unfCode;
}
