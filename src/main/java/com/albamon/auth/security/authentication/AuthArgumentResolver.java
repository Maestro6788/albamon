package com.albamon.auth.security.authentication;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.albamon.auth.common.response.ErrorCode;

@Component
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		boolean hasAuthInfo = parameter.getParameterType().isAssignableFrom(AuthInfo.class); //두 형식이 같은지 비교(isAssignableForm)
		return hasAuthInfo;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		HttpServletRequest request = (HttpServletRequest)webRequest.getNativeRequest();
		AuthInfo authInfo = AuthInfoAttributeUtils.getAuthInfoAttributes(request);

		boolean hasAuthAnnotation = parameter.hasMethodAnnotation(AuthRequired.class);

		if (!hasAuthAnnotation ) {
			throw new Exception(ErrorCode.AUTH_ANNOTATION_REQUIRED.getMessage());
		}

		return authInfo;
	}
}

