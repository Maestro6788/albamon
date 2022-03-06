package com.albamon.auth.auth.api.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordByChangeRequest {

	@NotBlank(message = "Input Your Password")
	private String password;

	@NotBlank(message = "Input Your Password")
	private String checkPassword;




	/*public ModifyUserTarget toParam() {
		return ModifyUserTarget.builder()
			.password(password)
			.build();
	}*/







}
