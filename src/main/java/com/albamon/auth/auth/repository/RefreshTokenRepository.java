package com.albamon.auth.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.albamon.auth.auth.domain.RefreshToken;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    //Optional<RefreshToken> findByKey(String key);
}
