package com.albamon.auth.auth.api.dto.request;

import javax.validation.constraints.NotBlank;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.albamon.auth.user.domain.CompanyUser;
import com.albamon.auth.user.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLoginRequest {

    @NotBlank(message = "Input Your ID")
    private String userId;
    @NotBlank(message = "Input Your Password")
    private String password;


    public User toEntity(PasswordEncoder passwordEncoder) {
        return User.builder()
            .userId(userId)
            .password(passwordEncoder.encode(password))
            .build();
    }

    public CompanyUser toCompanyUserEntity(PasswordEncoder passwordEncoder) {
        return CompanyUser.builder()
            .userId(userId)
            .password(passwordEncoder.encode(password))
            .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(userId, password);
    }
}
