
package com.albamon.auth.security.authentication;

import javax.servlet.http.HttpServletRequest;
import com.albamon.auth.common.AuthInfo;


public class AuthInfoAttributeUtils {

	public static void setAuthInfoAttributes(HttpServletRequest request, AuthInfo authInfo) {
		request.setAttribute(AuthInfo.AUTH_KEY, authInfo);
	}

	public static AuthInfo getAuthInfoAttributes(HttpServletRequest request) {
		return (AuthInfo)request.getAttribute(AuthInfo.AUTH_KEY);
	}
}
