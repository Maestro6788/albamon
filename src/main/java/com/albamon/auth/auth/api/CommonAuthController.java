package com.albamon.auth.auth.api;

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

import com.albamon.auth.auth.application.CommonAuthService;
import com.albamon.auth.auth.api.dto.AuthApiResponse;
import com.albamon.auth.auth.api.dto.TokenRequestDto;
import com.albamon.auth.auth.api.dto.request.EmailSMSRequest;
import com.albamon.auth.auth.api.dto.request.PhoneSMSRequest;
import com.albamon.auth.auth.api.dto.request.UpdatePasswordByChangeRequest;
import com.albamon.auth.common.response.ApiResponse;
import com.albamon.auth.common.response.StatusCode;
import com.albamon.auth.common.response.SuccessCode;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth/common")
@RequiredArgsConstructor
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class CommonAuthController {
	private final CommonAuthService commonAuthService;



	@PostMapping("/reissue")
	public ResponseEntity<?> reissue(@Valid @RequestBody TokenRequestDto tokenRequestDto) {
		AuthApiResponse tokenDto = commonAuthService.reissue(tokenRequestDto);
		ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS,
			SuccessCode.USER_REFRESH_SUCCESS.getMessage(), tokenDto);
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}



	//@GetMapping("/check/sendSMS")
	@PostMapping("/check/sendSMS")
	public ResponseEntity<?> checkSendSMS(@Valid @RequestBody PhoneSMSRequest request) {


		commonAuthService.checkPhoneNumber(request);

		return ResponseEntity.status(HttpStatus.OK).body("sms code 확인 성공");
	}


//	@GetMapping ("/check/sendEmail")
	@PostMapping ("/check/sendEmail")
	public ResponseEntity<?> checkSendEmail(@Valid @RequestBody EmailSMSRequest email) {


		commonAuthService.checkMessage(email);

		return ResponseEntity.status(HttpStatus.OK).body("email code 확인 성공");
	}



	@PutMapping("/user/{id}/password")
	public ResponseEntity<?> modifyPassword(@PathVariable long id, @Valid @RequestBody UpdatePasswordByChangeRequest dto) {
		AuthApiResponse authApiResponse = commonAuthService.changePassword(id,dto);

		ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS,
			"로그인 변경 성공 > 로그인 페이지로 이동", authApiResponse);

		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}

}