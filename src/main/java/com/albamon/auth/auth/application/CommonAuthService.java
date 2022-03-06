package com.albamon.auth.auth.application;


import com.albamon.auth.auth.api.dto.AuthApiResponse;
import com.albamon.auth.auth.api.dto.TokenRequestDto;
import com.albamon.auth.auth.api.dto.request.EmailSMSRequest;
import com.albamon.auth.auth.api.dto.request.PhoneSMSRequest;
import com.albamon.auth.auth.api.dto.request.UpdatePasswordByChangeRequest;

public interface CommonAuthService {

  AuthApiResponse reissue(TokenRequestDto tokenRequestDto);

  void checkPhoneNumber(PhoneSMSRequest dto);

  void checkMessage(EmailSMSRequest dto);


  AuthApiResponse changePassword(long id, UpdatePasswordByChangeRequest request);



}
