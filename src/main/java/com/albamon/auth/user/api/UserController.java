package com.albamon.auth.user.api;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.albamon.auth.common.response.ApiResponse;
import com.albamon.auth.common.response.StatusCode;
import com.albamon.auth.common.response.SuccessCode;
import com.albamon.auth.security.authentication.AuthInfo;
import com.albamon.auth.security.authentication.AuthRequired;
import com.albamon.auth.user.api.dto.DeleteUserRequest;
import com.albamon.auth.user.api.dto.FindUserInfoResponse;
import com.albamon.auth.user.api.dto.ModifyNicknameRequest;
import com.albamon.auth.user.api.dto.ModifyPasswordRequest;
import com.albamon.auth.user.api.dto.ReSaveDeviceTokenRequest;
import com.albamon.auth.user.api.dto.UserApiResponse;
import com.albamon.auth.user.api.dto.UserResponseDto;
import com.albamon.auth.user.application.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
	private final UserService userService;

	@AuthRequired
	@PutMapping("/password/{id}")
	public ResponseEntity<?> modifyPassword(@PathVariable long id, @Valid @RequestBody ModifyPasswordRequest dto,
		AuthInfo authInfo) {
		UserResponseDto responseDto = userService.changePassword(dto.toParam(id), authInfo);
		ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS,
			SuccessCode.USER_UPDATE_PASSWORD_SUCESS.getMessage(), responseDto);
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}

	@AuthRequired
	@PutMapping("/nickname/{id}")
	public ResponseEntity<?> modifyNickname(@PathVariable long id, @Valid @RequestBody ModifyNicknameRequest dto,
		AuthInfo authInfo) {
		UserResponseDto responseDto = userService.changeNickname(dto.toParam(id), authInfo);
		ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS,
			SuccessCode.USER_UPDATE_NICKNAME_SUCCESS.getMessage(), responseDto);
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}

	@AuthRequired
	@DeleteMapping("/{id}/info")
	public ResponseEntity<?> deleteUserInfo(@PathVariable long id, @Valid @RequestBody DeleteUserRequest dto,
		AuthInfo authInfo) {
		userService.deleteUser(dto.toParam(id), authInfo);
		ApiResponse apiResponse = ApiResponse.responseMessage(StatusCode.SUCCESS,
			SuccessCode.USER_DELETE_SUCCESS.getMessage());
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}

	// @AuthRequired
	// @PostMapping("/profile/{id}")
	// public ResponseEntity<?> uploadUserProfile(@PathVariable long id,
	// 	@RequestParam("images") MultipartFile multipartFile) throws IOException {
	// 	userService.saveProfileImg(id, multipartFile);
	// 	ApiResponse apiResponse = ApiResponse.responseMessage(StatusCode.SUCCESS,
	// 		SuccessCode.USER_PROFILE_UPLOAD_SUCCESS.getMessage());
	// 	return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	// }

	@AuthRequired
	@PostMapping("/device/{id}")
	public ResponseEntity<?> reSaveDviceToken(@PathVariable long id, @RequestBody ReSaveDeviceTokenRequest dto) {
		UserApiResponse reponseDto = userService.saveDeviceToken(dto.toParam(id));
		ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS,
			SuccessCode.USER_RESAVE_DEVICE_TOKEN_SUCCESS.getMessage(), reponseDto);
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}

	@AuthRequired
	@GetMapping("/{id}/info")
	public ResponseEntity<?> findUserInfo(@PathVariable long id, AuthInfo authInfo) {
		FindUserInfoResponse responseDto = userService.getMyInfo(id, authInfo);
		ApiResponse apiResponse = ApiResponse.responseData(StatusCode.SUCCESS,
			SuccessCode.USER_FINDMEMBER_SUCCESS.getMessage(), responseDto);
		return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
	}

}