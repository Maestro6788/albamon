package com.albamon.auth.auth.api;

import java.util.Random;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.albamon.auth.auth.api.dto.AuthApiResponse;
import com.albamon.auth.auth.api.dto.EmaiRequest;
import com.albamon.auth.auth.api.dto.EmailSMSRequest;
import com.albamon.auth.auth.api.dto.PhoneRequest;
import com.albamon.auth.auth.api.dto.PhoneSMSRequest;
import com.albamon.auth.auth.api.dto.TokenRequestDto;
import com.albamon.auth.auth.api.dto.UserLoginRequest;
import com.albamon.auth.auth.api.dto.UserSignUpRequest;
import com.albamon.auth.auth.application.AuthService;
import com.albamon.auth.common.response.ApiResponse;
import com.albamon.auth.common.response.StatusCode;
import com.albamon.auth.common.response.SuccessCode;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

	//회원가입
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@Valid @RequestBody UserSignUpRequest userSignUpRequest) {
		authService.signup(userSignUpRequest);
		ApiResponse apiResponse = ApiResponse.responseMessage(StatusCode.SUCCESS,
			SuccessCode.USER_SIGN_UP_SUCCESS.getMessage());
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequest userLoginRequest) {
		AuthApiResponse tokenDto = authService.login(userLoginRequest);
		ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS,
			SuccessCode.USER_LOGIN_SUCCESS.getMessage(),tokenDto);
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}

	@PostMapping("/reissue")
	public ResponseEntity<?> reissue(@Valid @RequestBody TokenRequestDto tokenRequestDto) {
		AuthApiResponse tokenDto = authService.reissue(tokenRequestDto);
		ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS,
			SuccessCode.USER_REFRESH_SUCCESS.getMessage(), tokenDto);
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}

	@GetMapping("/check-duplicate-user-id/{user-id}")
	public ResponseEntity<?> checkDuplicateId(@Valid @PathVariable("user-id") String userId) {
		boolean result = authService.checkDuplicateUserId(userId);

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
		boolean result = authService.checkDuplicateNickname(nickname);

		if (result) {
			ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS,
				SuccessCode.NICKNAME_ALREADY_EXIST.getMessage(), !result);
			return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
		}

		ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS,
			SuccessCode.NICKNAME_REGISTER_POSSIBLE.getMessage(), !result);
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}


	@PostMapping("/sendSMS")
	public ResponseEntity<?> sendSMS(@Valid @RequestBody PhoneRequest phoneNumber) {


		Random rand  = new Random();
		String numStr = "";
		for(int i=0; i<4; i++) {
			String ran = Integer.toString(rand.nextInt(10));
			numStr+=ran;
		}

		System.out.println("수신자 번호 : " + phoneNumber);
		System.out.println("인증번호 : " + numStr);
		authService.certifiedPhoneNumber(phoneNumber.getPhoneNumber(),numStr);

		return ResponseEntity.status(HttpStatus.OK).body("sms code 전송 성공");
	}

	@PostMapping("/check/sendSMS")
	public ResponseEntity<?> checkSendSMS(@Valid @RequestBody PhoneSMSRequest request) {


		authService.checkPhoneNumber(request);

		return ResponseEntity.status(HttpStatus.OK).body("sms code 확인 성공");
	}

	@PostMapping("/sendEmail")
	public ResponseEntity<?> sendEmail(@Valid @RequestBody EmaiRequest email) throws Exception {


		String confirm = authService.sendSimpleMessage(email.getEmail());

		return ResponseEntity.status(HttpStatus.OK).body("email code 전송 성공");
	}

	@PostMapping("/check/sendEmail")
	public ResponseEntity<?> checkSendEmail(@Valid @RequestBody EmailSMSRequest email) {


		authService.checkMessage(email);

		return ResponseEntity.status(HttpStatus.OK).body("email code 확인 성공");
	}

}