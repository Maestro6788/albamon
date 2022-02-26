package com.albamon.auth.user.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ReSaveDeviceTokenTarget {

	private long id;
	private String deviceToken;

}
