package com.albamon.auth.user.api.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteUserRequest {

	@NotBlank(message = "Input Your Password")
	private String password;

	public DeleteUserTarget toParam(long id) {
		return DeleteUserTarget.builder()
			.id(id)
			.password(password)
			.build();
	}

	/*public ModifyUserTarget toParam() {
		return ModifyUserTarget.builder()
			.password(password)
			.build();
	}*/







}
