package com.albamon.auth.auth.api.dto;

import java.util.Random;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.albamon.auth.auth.api.dto.request.EmailRequest;
import com.albamon.auth.auth.api.dto.request.FindPasswordByPhoneRequest;
import com.albamon.auth.auth.api.dto.request.UserLoginRequest;
import com.albamon.auth.auth.api.dto.request.UserSignUpRequest;
import com.albamon.auth.auth.application.SeekerAuthService;
import com.albamon.auth.common.response.ApiResponse;
import com.albamon.auth.common.response.StatusCode;
import com.albamon.auth.common.response.SuccessCode;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class ShopController {
	private final SeekerAuthService seekerAuthService;

	//회원가입
	@PostMapping("/buy-")
	public ResponseEntity<?> signup(@Valid @RequestBody UserSignUpRequest userSignUpRequest) {
		seekerAuthService.signup(userSignUpRequest);
		ApiResponse apiResponse = ApiResponse.responseMessage(StatusCode.SUCCESS,
			SuccessCode.USER_SIGN_UP_SUCCESS.getMessage());
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}









}