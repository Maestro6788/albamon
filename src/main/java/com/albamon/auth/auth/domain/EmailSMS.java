package com.albamon.auth.auth.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
@RedisHash(value = "sms", timeToLive = 240)
public class EmailSMS {

	@Id
	private String email;
	private String code;


}
