package com.albamon.auth.user.api.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ReSaveDeviceTokenRequest {

	@NotBlank(message = "Input Your DeviceToken")
	private String deviceToken;

	public ReSaveDeviceTokenTarget toParam(long userId) {
		return ReSaveDeviceTokenTarget.builder()
			.id(userId)
			.deviceToken(deviceToken)
			.build();
	}
}
