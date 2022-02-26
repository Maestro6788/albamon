package com.albamon.auth.auth.api;

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
}