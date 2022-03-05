package com.albamon.auth.auth.repository;

import org.springframework.data.repository.CrudRepository;

import com.albamon.auth.auth.domain.EmailSMS;

public interface EmailRedisRepository extends CrudRepository<EmailSMS,String> {
}
