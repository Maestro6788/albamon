package com.albamon.auth.security.authentication;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import com.albamon.auth.common.AuthInfo;

import com.albamon.auth.common.session.SessionManager;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

	private final SessionManager sessionManager;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {

		AuthRequired authRequired = getAuthRequired(handler);
		RequireNoneSignIn requireNoneSignIn = getRequireNoneSignIn(handler);
		AuthUsed authUsed = getAuthUsed(handler);
		SessionUsed sessionUsed = getSessionUsed(handler);


		AuthInfo authInfo = AuthInfoAttributeUtils.getAuthInfoAttributes(request);
		System.out.println(authInfo);


		// 로긍인 회원 - 권한 검사 (ex)회원가입)

		if (sessionUsed != null){
			authInfo = getAuthInfo(request);
			System.out.println("세션 게시판~");
			System.out.println(authInfo);
		}


		if (authInfo != null && requireNoneSignIn != null) {
			throw new Exception("인터셉터 - 권한이 없습니다.  로그인 사용자 - 회원가입 요청");
		}


		if (authRequired == null && authUsed == null) {
			return true;
		}


		validateRole(authRequired, authInfo);
		AuthInfoAttributeUtils.setAuthInfoAttributes(request, authInfo);

		return true;
	}

	private void validateRole(AuthRequired authRequired, AuthInfo authInfo) throws Exception {
		if (authRequired.role() != authInfo.getUserType()) {
			throw new Exception("인터셉터 - 요청 유저 타입이 맞지 않습니다.");
		}
	}


	private AuthInfo getAuthInfo(HttpServletRequest request) throws Exception {
		AuthInfo authInfo = sessionManager.getSession(request);
		if (null == authInfo) {
			throw new Exception("세션 정보가 없습니다.");
		}
		return authInfo;
	}

	private AuthRequired getAuthRequired(Object handler) {
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		return handlerMethod.getMethod().getAnnotation(AuthRequired.class);
	}

	private RequireNoneSignIn getRequireNoneSignIn(Object handler) {
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		return handlerMethod.getMethod().getAnnotation(RequireNoneSignIn.class);
	}

	private AuthUsed getAuthUsed(Object handler) {
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		return handlerMethod.getMethod().getAnnotation(AuthUsed.class);
	}

	private SessionUsed getSessionUsed(Object handler) {
		HandlerMethod handlerMethod = (HandlerMethod)handler;
		return handlerMethod.getMethod().getAnnotation(SessionUsed.class);
	}

}
