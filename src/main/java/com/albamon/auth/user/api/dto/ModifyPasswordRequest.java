package com.albamon.auth.user.api.dto;

import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.repository.Modifying;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ModifyPasswordRequest {

	@NotBlank(message = "Input Your Password")
	private String password;

	public ModifyPasswordTarget toParam(Long userId) {
		return ModifyPasswordTarget.builder()
			.id(userId)
			.password(password)
			.build();
	}


	/*public ModifyUserTarget toParam() {
		return ModifyUserTarget.builder()
			.password(password)
			.build();
	}*/







}
