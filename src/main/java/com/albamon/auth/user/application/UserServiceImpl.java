package com.albamon.auth.user.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.albamon.auth.auth.repository.RefreshTokenRepository;
import com.albamon.auth.common.response.ErrorCode;
import com.albamon.auth.security.authentication.AuthInfo;
import com.albamon.auth.user.api.dto.DeleteUserTarget;
import com.albamon.auth.user.api.dto.FindUserInfoResponse;
import com.albamon.auth.user.api.dto.ModifyNicknameTarget;
import com.albamon.auth.user.api.dto.ModifyPasswordTarget;
import com.albamon.auth.user.api.dto.ReSaveDeviceTokenTarget;
import com.albamon.auth.user.api.dto.UserApiResponse;
import com.albamon.auth.user.api.dto.UserResponseDto;
import com.albamon.auth.user.domain.User;
import com.albamon.auth.user.repository.UserRepository;
import com.albamon.auth.util.SecurityUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final RefreshTokenRepository refreshTokenRepository;
	// private final S3Uploader s3Uploader;

	@Override
	public UserResponseDto changePassword(ModifyPasswordTarget target, AuthInfo authInfo) {
		validUser(target.getId(), authInfo.getId());
		User user = findUserById(target.getId());
		user.changePassword(passwordEncoder.encode(target.getPassword()));

		return UserResponseDto.of(userRepository.save(user));
	}

	@Override
	public UserResponseDto changeNickname(ModifyNicknameTarget target, AuthInfo authInfo) {
		validUser(target.getId(), authInfo.getId());
		User user = findUserById(target.getId());
		user.changeNickname(target.getNickname());
		return UserResponseDto.of(userRepository.save(user));
	}

	@Override
	public void deleteUser(DeleteUserTarget target, AuthInfo authInfo) {
		User user = findUserById(authInfo.getId());

		validUser(target.getId(), authInfo.getId());
		validPassword(target.getPassword(), user.getPassword());

		userRepository.deleteById(target.getId());
		refreshTokenRepository.deleteById(String.valueOf(target.getId()));
	}

	// @Override
	// public void saveProfileImg(long userId, MultipartFile multipartFile) throws IOException {
	// 	User user = findUserById(userId);
	//
	// 	String userImg = s3Uploader.upload(multipartFile, user.getId());
	// 	user.changeProfileUrl(userImg);
	// 	userRepository.save(user);
	//
	// }

	@Override
	public UserApiResponse saveDeviceToken(ReSaveDeviceTokenTarget reSaveDeviceTokenTarget) {
		User user = findUserById(reSaveDeviceTokenTarget.getId());

		// user.changeDeviceToken(reSaveDeviceTokenTarget.getDeviceToken());
		userRepository.save(user);

		return UserApiResponse.of(user);
	}

	// 현재 SecurityContext 에 있는 유저 정보 가져오기
	@Override
	public FindUserInfoResponse getMyInfo(long userId, AuthInfo authInfo) {
		validUser(userId, authInfo.getId());
		User user = userRepository.findById(SecurityUtil.getCurrentMemberId())
			.orElseThrow(() -> new RuntimeException(ErrorCode.ID_NOT_EXIST.getMessage()));

		return FindUserInfoResponse.of(user);
	}

	private User findUserById(Long id) {
		return userRepository.findById(id)
			.orElseThrow(() -> new NullPointerException(ErrorCode.ID_NOT_EXIST.getMessage()));
	}

	private void validUser(long requestedUserId, long AuthedUserId) {
		if (requestedUserId != AuthedUserId) {
			throw new SecurityException(ErrorCode.ID_NOT_MATCH.getMessage());
		}
	}

	private void validPassword(String requestPassword, String encodedPassword) {
		if (!passwordEncoder.matches(requestPassword, encodedPassword)) {
			throw new SecurityException(ErrorCode.PASSWORD_NOT_MATCH.getMessage());
		}
	}
}