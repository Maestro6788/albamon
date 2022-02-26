package com.albamon.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.albamon.auth.security.jwt.JwtAccessDeniedHandler;
import com.albamon.auth.security.jwt.JwtAuthenticationEntryPoint;
import com.albamon.auth.security.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final TokenProvider tokenProvider;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// h2 database 테스트가 원활하도록 관련 API 들은 전부 무시
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			.antMatchers(HttpMethod.OPTIONS, "*/**")
			.antMatchers("/", "/h2-console/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests()
			.antMatchers("/users/**").permitAll()
			.antMatchers("/auth/**").permitAll()
			.antMatchers("/user-boards/**").permitAll()
			.antMatchers("/**").permitAll()
			.antMatchers("/h2-console/**/**").permitAll()
			.anyRequest().authenticated();
		http.exceptionHandling().accessDeniedPage("/loginss");
		http.apply(new JwtSecurityConfig(tokenProvider));
	}
}
