package com.albamon.auth.auth.api;

import java.util.Random;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.albamon.auth.auth.api.dto.AuthApiResponse;
import com.albamon.auth.auth.api.dto.TokenRequestDto;
import com.albamon.auth.auth.api.dto.request.EmailRequest;
import com.albamon.auth.auth.api.dto.request.EmailSMSRequest;
import com.albamon.auth.auth.api.dto.request.FindPasswordByPhoneRequest;
import com.albamon.auth.auth.api.dto.request.PhoneRequest;
import com.albamon.auth.auth.api.dto.request.PhoneSMSRequest;
import com.albamon.auth.auth.api.dto.request.UpdatePasswordByChangeRequest;
import com.albamon.auth.auth.api.dto.request.UserLoginRequest;
import com.albamon.auth.auth.api.dto.request.UserSignUpRequest;
import com.albamon.auth.common.UserType;
import com.albamon.auth.common.response.ApiResponse;
import com.albamon.auth.common.response.StatusCode;
import com.albamon.auth.common.response.SuccessCode;

import com.albamon.auth.auth.application.SeekerAuthService;
import com.albamon.auth.security.authentication.RequireNoneSignIn;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth/job-seeker")
@RequiredArgsConstructor
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class SeekerAuthController {
	private final SeekerAuthService seekerAuthService;

	//회원가입
	@PostMapping("/signup")
	@RequireNoneSignIn(role = UserType.JOB_SEEKER)
	public ResponseEntity<?> signup(@Valid @RequestBody UserSignUpRequest userSignUpRequest) {
		seekerAuthService.signup(userSignUpRequest);
		ApiResponse apiResponse = ApiResponse.responseMessage(StatusCode.SUCCESS,
			SuccessCode.USER_SIGN_UP_SUCCESS.getMessage());
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequest userLoginRequest) {
		AuthApiResponse tokenDto = seekerAuthService.login(userLoginRequest);
		ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS,
			SuccessCode.USER_LOGIN_SUCCESS.getMessage(),tokenDto);
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}



	@GetMapping("/check-duplicate-user-id/{user-id}")
	public ResponseEntity<?> checkDuplicateId(@Valid @PathVariable("user-id") String userId) {
		boolean result = seekerAuthService.checkDuplicateUserId(userId);

		if (result) {
			ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS,
				SuccessCode.USER_ID_ALREADY_EXIST.getMessage(), !result);
			return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
		}

		ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS,
			SuccessCode.USER_ID_REGISTER_POSSIBLE.getMessage(), !result);
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}

	@GetMapping("/check-duplicate-nickname/{nickname}")
	public ResponseEntity<?> checkNickname(@Valid @PathVariable String nickname) {
		boolean result = seekerAuthService.checkDuplicateNickname(nickname);

		if (result) {
			ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS,
				SuccessCode.NICKNAME_ALREADY_EXIST.getMessage(), !result);
			return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
		}

		ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS,
			SuccessCode.NICKNAME_REGISTER_POSSIBLE.getMessage(), !result);
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}


/*	@PostMapping("/sendSMS")
	public ResponseEntity<?> sendSMS(@Valid @RequestBody PhoneRequest phoneNumber) {


		Random rand  = new Random();
		String numStr = "";
		for(int i=0; i<4; i++) {
			String ran = Integer.toString(rand.nextInt(10));
			numStr+=ran;
		}

		System.out.println("수신자 번호 : " + phoneNumber);
		System.out.println("인증번호 : " + numStr);
		seekerAuthService.certifiedPhoneNumber(phoneNumber.getPhoneNumber(),numStr);

		return ResponseEntity.status(HttpStatus.OK).body("sms code 전송 성공");
	}*/



	@PostMapping("/sendEmail")
	public ResponseEntity<?> sendEmail(@Valid @RequestBody EmailRequest email) throws Exception {


		AuthApiResponse authApiResponse = seekerAuthService.sendSimpleMessage(email);

		ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS,
			"email code 전송 성공 ", authApiResponse);

		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}



	@PostMapping("/check-pw-by-phone")
	public ResponseEntity<?> checkPasswordByPhone(@Valid @RequestBody FindPasswordByPhoneRequest dto) {


		Random rand  = new Random();
		String numStr = "";
		for(int i=0; i<4; i++) {
			String ran = Integer.toString(rand.nextInt(10));
			numStr+=ran;
		}

		seekerAuthService.findPasswordByPhoneNumber(dto,numStr);


		return ResponseEntity.status(HttpStatus.OK).body("비밀번호 찾기 - 휴대폰 인증 전송 ");
	}



}