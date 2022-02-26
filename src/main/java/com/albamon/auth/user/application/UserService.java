package com.albamon.auth.user.application;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.albamon.auth.security.authentication.AuthInfo;
import com.albamon.auth.user.api.dto.DeleteUserTarget;
import com.albamon.auth.user.api.dto.FindUserInfoResponse;
import com.albamon.auth.user.api.dto.ModifyNicknameTarget;
import com.albamon.auth.user.api.dto.ModifyPasswordTarget;
import com.albamon.auth.user.api.dto.ReSaveDeviceTokenTarget;
import com.albamon.auth.user.api.dto.UserApiResponse;
import com.albamon.auth.user.api.dto.UserResponseDto;

public interface UserService {

	UserResponseDto changePassword(ModifyPasswordTarget target, AuthInfo authInfo);
	UserResponseDto changeNickname(ModifyNicknameTarget target, AuthInfo authInfo);
	void deleteUser(DeleteUserTarget target, AuthInfo authInfo);
	// void saveProfileImg(long userId, MultipartFile multipartFile) throws IOException;
	UserApiResponse saveDeviceToken(ReSaveDeviceTokenTarget reSaveDeviceTokenTarget);
	FindUserInfoResponse getMyInfo(long userId, AuthInfo aUthInfo);


}
