package com.albamon.auth.auth.dto.request;

import javax.validation.constraints.NotBlank;

import com.albamon.auth.user.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindPasswordByPhoneRequest {


	@NotBlank(message = "Input Your ID")
	private String userId;
	@NotBlank(message = "Input Your Name")
	private String userName;
	@NotBlank(message = "Input Your PhoneNumber")
	private String userPhoneNumber;


	public User toEntity() {
		return User.builder()
			.userId(userId)
			.userName(userName)
			.userPhoneNumber(userPhoneNumber)
			.build();
	}

}
