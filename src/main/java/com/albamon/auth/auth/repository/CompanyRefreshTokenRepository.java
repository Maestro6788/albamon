package com.albamon.auth.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.albamon.auth.auth.domain.CompanyRefreshToken;
import com.albamon.auth.auth.domain.RefreshToken;

@Repository
public interface CompanyRefreshTokenRepository extends JpaRepository<CompanyRefreshToken, String> {
    //Optional<RefreshToken> findByKey(String key);
}
