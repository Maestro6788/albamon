package com.albamon.auth.auth.api.dto;

import javax.validation.constraints.NotBlank;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.albamon.auth.user.domain.Authority;
import com.albamon.auth.user.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhoneSMSRequest {

    @NotBlank(message = "Input Your ID")
    private String phoneNumber;
    @NotBlank(message = "Input Your Password")
    private String email;



}
