package com.albamon.auth.security.authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {

		AuthRequired authRequired = getAuthRequired(handler);
		if (null == authRequired) {
			return true;
		}
		return true;
	}



	private AuthRequired getAuthRequired(Object handler) {
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		return handlerMethod.getMethod().getAnnotation(AuthRequired.class);
	}
}

