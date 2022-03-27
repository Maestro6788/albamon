package com.albamon.auth.auth.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@RedisHash(value = "sms", timeToLive = 3600)   // 4: 240
public class PhoneSMS {

	@Id
	private String phoneNumber;
	private String code;
	
}

// 알바몬 회원가입 x > 소셜로그인 성공 > /메인 페이지 /mainPage
// 알바몬 회원가입 o > 소셜로그인 성공 > 메인페이지