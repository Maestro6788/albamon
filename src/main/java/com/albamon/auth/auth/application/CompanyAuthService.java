package com.albamon.auth.auth.application;


import com.albamon.auth.auth.api.dto.AuthApiResponse;
import com.albamon.auth.auth.api.dto.request.EmailRequest;
import com.albamon.auth.auth.api.dto.request.FindPasswordByPhoneRequest;
import com.albamon.auth.auth.api.dto.request.UserLoginRequest;
import com.albamon.auth.auth.api.dto.request.UserSignUpRequest;

public interface CompanyAuthService {
  void signup(UserSignUpRequest userSignUpRequest);
  AuthApiResponse login(UserLoginRequest userLoginRequest);
  // boolean checkDuplicateNickname(String nickname);
  // boolean checkDuplicateUserId(String userId);

  // void certifiedPhoneNumber(String phoneNumber, String cerNum);

  AuthApiResponse sendSimpleMessage(EmailRequest request)throws Exception;


  AuthApiResponse findPasswordByPhoneNumber(FindPasswordByPhoneRequest request, String cerNum);



}
