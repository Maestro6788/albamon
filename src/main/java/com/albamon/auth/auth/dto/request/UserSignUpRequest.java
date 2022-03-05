package com.albamon.auth.auth.dto.request;

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
public class UserSignUpRequest {

	@NotBlank(message = "Input Your ID")
	private String userId;
	@NotBlank(message = "Input Your Password")
	private String password;
	@NotBlank(message = "Input Your Nickname")
	private String nickname;
	@NotBlank(message = "Input Your Nickname")
	private String userName;
	@NotBlank(message = "Input Your Nickname")
	private String userPhoneNumber;


	public User toEntity(PasswordEncoder passwordEncoder) {
		return User.builder()
			.userId(userId)
			.password(passwordEncoder.encode(password))
			.nickname(nickname)
			.userName(userName)
			.userPhoneNumber(userPhoneNumber)
			.authority(Authority.COMPANY)
			.build();
	}

	public UsernamePasswordAuthenticationToken toAuthentication() {
		return new UsernamePasswordAuthenticationToken(userId, password);
	}
}
