package com.albamon.auth.auth.application;


import com.albamon.auth.auth.api.dto.AuthApiResponse;
import com.albamon.auth.auth.api.dto.request.FindPasswordByPhoneRequest;
import com.albamon.auth.auth.api.dto.request.UserLoginRequest;
import com.albamon.auth.auth.api.dto.request.UserSignUpRequest;

public interface SeekerAuthService {
  void signup(UserSignUpRequest userSignUpRequest);
  AuthApiResponse login(UserLoginRequest userLoginRequest);
  boolean checkDuplicateNickname(String nickname);
  boolean checkDuplicateUserId(String userId);


  String sendSimpleMessage(String to)throws Exception;


  void findPasswordByPhoneNumber(FindPasswordByPhoneRequest request, String cerNum);



}
