package com.albamon.auth.board.api;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

import com.albamon.auth.auth.api.dto.AuthApiResponse;
import com.albamon.auth.auth.api.dto.request.EmailRequest;
import com.albamon.auth.auth.api.dto.request.FindPasswordByPhoneRequest;
import com.albamon.auth.auth.api.dto.request.UserLoginRequest;
import com.albamon.auth.auth.api.dto.request.UserSignUpRequest;
import com.albamon.auth.auth.application.SeekerAuthService;
import com.albamon.auth.common.AuthInfo;
import com.albamon.auth.common.response.ApiResponse;
import com.albamon.auth.common.response.StatusCode;
import com.albamon.auth.common.response.SuccessCode;
import com.albamon.auth.common.session.SessionManager;
import com.albamon.auth.security.authentication.SessionUsed;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class BoardController {

	private final SeekerAuthService seekerAuthService;
	private final SessionManager sessionManager;


	//게시글 작성 세션 생성
	@GetMapping("/{user-no}/post")
	public ResponseEntity<?> startPost(@PathVariable("user-no") long shopId, HttpServletResponse response) {


		sessionManager.createSession(AuthInfo.companyOf(shopId,"d"), response);

		return ResponseEntity.status(HttpStatus.OK).body("성공!");
	}

	//게시글 작성 세션 생성
	@PostMapping("/{user-no}/post")
	@SessionUsed
	public ResponseEntity<?> savePost(@PathVariable("user-no") long shopId, HttpServletRequest request) throws
		Exception {

		AuthInfo authInfo = sessionManager.getSession(request);

		System.out.println(authInfo);

		return ResponseEntity.status(HttpStatus.OK).body(" ");
	}



}