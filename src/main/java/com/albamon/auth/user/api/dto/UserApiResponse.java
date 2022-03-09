package com.albamon.auth.user.api.dto;


import com.albamon.auth.user.domain.User;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class UserApiResponse {

	private long id;
	private String userId;
	private String nickname;
	private String profileUrl;

	@Builder
	public UserApiResponse(long id, String userId, String nickname, String profileUrl) {
		this.id = id;
		this.userId = userId;
		this.nickname = nickname;
		this.profileUrl = profileUrl;
	}

	public static UserApiResponse of(User user) {
		return new UserApiResponse(user.getId(), user.getUserId(), user.getNickname(), user.getProfileUrl());
	}

}
