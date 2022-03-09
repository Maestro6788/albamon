package com.albamon.auth.auth.api.dto;


import com.albamon.auth.common.TokenDto;
import com.albamon.auth.user.domain.Authority;
import com.albamon.auth.user.domain.CompanyUser;
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
  private String grantType;
  private String accessToken;
  private String refreshToken;
  private Authority authority;
  private Long accessTokenExpiresIn;
  private String phoneNumber;
  private String name;

  @Builder
  public AuthApiResponse(long id, String userId, String nickname, String profileUrl,
      String grantType, String accessToken, String refreshToken, Authority authority, Long accessTokenExpiresIn,
      String phoneNumber, String name) {
    this.id = id;
    this.userId = userId;
    this.nickname = nickname;
    this.profileUrl = profileUrl;
    this.grantType = grantType;
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.authority = authority;
    this.accessTokenExpiresIn = accessTokenExpiresIn;
    this.phoneNumber = phoneNumber;
    this.name = name;
  }







  public static AuthApiResponse of(User user, TokenDto tokenDto) {
    return new AuthApiResponse(user.getId(),user.getUserId(),user.getNickname(), user.getProfileUrl(), tokenDto.getGrantType(), tokenDto.getAccessToken(),
        tokenDto.getRefreshToken(), user.getAuthority(), tokenDto.getAccessTokenExpiresIn(),user.getUserPhoneNumber(),user.getUserName());
  }

  public static AuthApiResponse ofCompany(CompanyUser user, TokenDto tokenDto) {
    return new AuthApiResponse(user.getId(),user.getUserId(),user.getNickname(), user.getProfileUrl(), tokenDto.getGrantType(), tokenDto.getAccessToken(),
        tokenDto.getRefreshToken(), user.getAuthority(), tokenDto.getAccessTokenExpiresIn(),user.getUserPhoneNumber(),user.getUserName());
  }

  public static AuthApiResponse passwordToRes(User user){
    return AuthApiResponse.builder()
        .id(user.getId())
        .userId(user.getUserId())
        .build();
  }

  public static AuthApiResponse companyPasswordToRes(CompanyUser user){
    return AuthApiResponse.builder()
        .id(user.getId())
        .userId(user.getUserId())
        .build();
  }

  public static AuthApiResponse finalPasswordChangeToRes(User user){
    return AuthApiResponse.builder()
        .build();
  }

}
