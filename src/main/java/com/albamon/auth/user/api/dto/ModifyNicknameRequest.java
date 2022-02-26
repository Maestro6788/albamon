package com.albamon.auth.user.api.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ModifyNicknameRequest {

	@NotBlank(message = "Input Your Nickname")
	private String nickname;

	public ModifyNicknameTarget toParam(Long userId) {
		return ModifyNicknameTarget.builder()
			.id(userId)
			.nickname(nickname)
			.build();
	}
}
