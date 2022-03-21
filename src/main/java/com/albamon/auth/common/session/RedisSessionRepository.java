package com.albamon.auth.common.session;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.albamon.auth.common.AuthInfo;

@Component
public class RedisSessionRepository {

	private final RedisTemplate<String, AuthInfo> redisTemplate;

	public RedisSessionRepository(
		RedisTemplate<String, AuthInfo> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public void save(String sessionId, AuthInfo authInfo) {
		redisTemplate.opsForValue().set(sessionId, authInfo);
	}

	public AuthInfo findBySessionId(String sessionId) {
		return redisTemplate.opsForValue().get(sessionId);
	}

	public void remove(String sessionId) {
		redisTemplate.delete(sessionId);
	}

}
