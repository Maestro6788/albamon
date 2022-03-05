package com.albamon.auth.auth.seeker.application;

import com.albamon.auth.auth.dto.AuthApiResponse;
import com.albamon.auth.auth.dto.TokenRequestDto;
import com.albamon.auth.auth.dto.request.EmailSMSRequest;
import com.albamon.auth.auth.dto.request.FindPasswordByPhoneRequest;
import com.albamon.auth.auth.dto.request.PhoneSMSRequest;
import com.albamon.auth.auth.dto.request.UpdatePasswordByChangeRequest;
import com.albamon.auth.auth.dto.request.UserLoginRequest;
import com.albamon.auth.auth.dto.request.UserSignUpRequest;

public interface SeekerAuthService {
  void signup(UserSignUpRequest userSignUpRequest);
  AuthApiResponse login(UserLoginRequest userLoginRequest);
  AuthApiResponse reissue(TokenRequestDto tokenRequestDto);
  boolean checkDuplicateNickname(String nickname);
  boolean checkDuplicateUserId(String userId);

  void certifiedPhoneNumber(String phoneNumber, String cerNum);
  void checkPhoneNumber(PhoneSMSRequest dto);

  String sendSimpleMessage(String to)throws Exception;
  void checkMessage(EmailSMSRequest dto);


  void findPasswordByPhoneNumber(FindPasswordByPhoneRequest request, String cerNum);
  void changePassword(long id, UpdatePasswordByChangeRequest request);



}
