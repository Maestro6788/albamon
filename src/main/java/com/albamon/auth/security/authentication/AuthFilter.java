package com.albamon.auth.security.authentication;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import com.albamon.auth.common.AuthInfo;

import com.albamon.auth.security.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        String jwt = resolveToken(request);



        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            System.out.println("여기");
            Authentication authentication = tokenProvider.getAuthentication(jwt);

            System.out.println(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            AuthInfo authInfo = AuthInfo.userOf(Long.parseLong(authentication.getName())); ;
            AuthInfoAttributeUtils.setAuthInfoAttributes(request, authInfo);
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
