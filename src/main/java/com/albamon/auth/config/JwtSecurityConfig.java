package com.albamon.auth.config;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.albamon.auth.security.authentication.AuthFilter;
import com.albamon.auth.security.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
	private final TokenProvider tokenProvider;

	@Override
	public void configure(HttpSecurity http) {
		AuthFilter customFilter = new AuthFilter(tokenProvider);
		http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
