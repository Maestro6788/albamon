package com.albamon.auth.auth.application;

import com.albamon.auth.auth.api.dto.AuthApiResponse;
import com.albamon.auth.auth.api.dto.TokenRequestDto;
import com.albamon.auth.auth.api.dto.UserLoginRequest;
import com.albamon.auth.auth.api.dto.UserSignUpRequest;

public interface AuthService {
  void signup(UserSignUpRequest userSignUpRequest);
  AuthApiResponse login(UserLoginRequest userLoginRequest);
  AuthApiResponse reissue(TokenRequestDto tokenRequestDto);
  boolean checkDuplicateNickname(String nickname);
  boolean checkDuplicateUserId(String userId);
}
