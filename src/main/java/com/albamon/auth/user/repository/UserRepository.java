package com.albamon.auth.user.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.albamon.auth.user.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByNickname(String nickname);
    Optional<User> findByUserId(String userId);
    boolean existsByUserId(String userId);

    @Modifying
    @Transactional
    @Query("UPDATE User u set u.profileUrl = :imgUrl where u.id = :id")
    void saveUserProfileImg(@Param("id") long id,@Param("imgUrl") String imgUrl);
}
