package com.albamon.auth.auth.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@RedisHash(value = "sms", timeToLive = 240)
public class PhoneSMS {

	@Id
	private String phoneNumber;
	private String code;


}
