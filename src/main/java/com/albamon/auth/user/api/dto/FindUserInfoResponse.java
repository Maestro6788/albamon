package com.albamon.auth.user.api.dto;


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
public class FindUserInfoResponse {
	private long id;
	private String userId;
	private String nickname;
	private Authority authority;
	private String profileUrl;
	private String deviceToken;

	public static FindUserInfoResponse of(User user) {
		return new FindUserInfoResponse(user.getId(), user.getUserId(), user.getNickname(), user.getAuthority(),
			user.getProfileUrl(), user.getDeviceToken());
	}
}
