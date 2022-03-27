package com.albamon.auth.board.api;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.albamon.auth.common.response.ApiResponse;
import com.albamon.auth.common.response.ErrorCode;
import com.albamon.auth.common.response.StatusCode;

import com.albamon.auth.security.jwt.TokenProvider;
import com.albamon.auth.user.domain.User;
import com.albamon.auth.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class MyPageController {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

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
    public ResponseEntity<?> savePost(@PathVariable("user-no") long shopId, @RequestBody ChangeNicknameRequest request,HttpServletRequest request1){

        String jwt = resolveToken(request1);


        if (!StringUtils.hasText(jwt) || !tokenProvider.validateToken(jwt)) {
            throw new NullPointerException("토큰 오류");
        }


        User user = userRepository.findById(shopId).orElseThrow();

        if (userRepository.existsByNickname(request.getNickname())) {
            throw new DuplicateKeyException(ErrorCode.NICKNAME_ALREADY_EXIST.getMessage());
        }

        user.setNickname(request.getNickname());
        userRepository.save(user);
        // User user1 = userRepository.findById(shopId).orElseThrow();



        ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS,
                "닉네임 변경 성공",user.getNickname());

        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}