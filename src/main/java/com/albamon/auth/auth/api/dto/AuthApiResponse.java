package com.albamon.auth.auth.api.dto;


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
public class AuthApiResponse {

  private long id;
  private String userId;
  private String nickname;
  private String profileUrl;
  private String deviceToken;
  private String grantType;
  private String accessToken;
  private String refreshToken;
  private Long accessTokenExpiresIn;

  @Builder
  public AuthApiResponse(long id, String userId, String nickname, String profileUrl, String deviceToken,
      String grantType, String accessToken, String refreshToken, Long accessTokenExpiresIn) {
    this.id = id;
    this.userId = userId;
    this.nickname = nickname;
    this.profileUrl = profileUrl;
    this.deviceToken = deviceToken;
    this.grantType = grantType;
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.accessTokenExpiresIn = accessTokenExpiresIn;
  }

  public static AuthApiResponse of(User user, TokenDto tokenDto) {
    return new AuthApiResponse(user.getId(),user.getUserId(),user.getNickname(), user.getProfileUrl(),user.getDeviceToken(), tokenDto.getGrantType(), tokenDto.getAccessToken(),
        tokenDto.getRefreshToken(), tokenDto.getAccessTokenExpiresIn());
  }

}
