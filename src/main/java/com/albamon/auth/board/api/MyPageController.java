package com.albamon.auth.board.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DuplicateKeyException;
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

import com.albamon.auth.auth.application.SeekerAuthService;
import com.albamon.auth.common.AuthInfo;
import com.albamon.auth.common.response.ErrorCode;
import com.albamon.auth.common.session.SessionManager;
import com.albamon.auth.security.authentication.SessionUsed;
import com.albamon.auth.user.domain.User;
import com.albamon.auth.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class MyPageController {

	private final UserRepository userRepository;


	// //게시글 작성 세션 생성
	// @GetMapping("/{user-no}/post")
	// public ResponseEntity<?> startPost(@PathVariable("user-no") long shopId, HttpServletResponse response) {
	//
	//
	// 	sessionManager.createSession(AuthInfo.companyOf(shopId,"d"), response);
	//
	// 	return ResponseEntity.status(HttpStatus.OK).body("성공!");
	// }


	//게시글 작성 세션 생성
	@PutMapping("/{user-no}/nickname")
	public ResponseEntity<?> savePost(@PathVariable("user-no") long shopId, @RequestBody ChangeNicknameRequest request){


		User user = userRepository.findById(shopId).orElseThrow();

		if (userRepository.existsByNickname(request.getNickname())) {
			throw new DuplicateKeyException(ErrorCode.NICKNAME_ALREADY_EXIST.getMessage());
		}

		user.setNickname(request.getNickname());
		userRepository.save(user);
		// User user1 = userRepository.findById(shopId).orElseThrow();


		return ResponseEntity.status(HttpStatus.OK).body("닉네임 변경완료 : " + user.getNickname());
	}



}