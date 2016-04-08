package com.yourpackagename.yourwebproject.common;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.yourpackagename.yourwebproject.model.entity.enums.Role;


/**
 * @author Mevan D Souza
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckPermission {
	Role[] allowedRoles();
}
