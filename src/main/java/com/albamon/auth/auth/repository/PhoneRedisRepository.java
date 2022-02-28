package com.albamon.auth.auth.repository;

import org.springframework.data.repository.CrudRepository;

import com.albamon.auth.auth.domain.PhoneSMS;

public interface PhoneRedisRepository extends CrudRepository<PhoneSMS,String> {
}
