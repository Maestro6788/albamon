package com.albamon.auth.auth.application;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.albamon.auth.auth.api.dto.AuthApiResponse;
import com.albamon.auth.auth.api.dto.TokenDto;
import com.albamon.auth.auth.api.dto.TokenRequestDto;
import com.albamon.auth.auth.api.dto.UserLoginRequest;
import com.albamon.auth.auth.api.dto.UserSignUpRequest;
import com.albamon.auth.auth.domain.RefreshToken;
import com.albamon.auth.auth.repository.RefreshTokenRepository;
import com.albamon.auth.common.response.ErrorCode;
import com.albamon.auth.security.jwt.TokenProvider;
import com.albamon.auth.user.domain.User;
import com.albamon.auth.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService{
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void signup(UserSignUpRequest signUpDto) {

        User user = signUpDto.toEntity(passwordEncoder);


        if (userRepository.existsByUserId(signUpDto.getUserId())) {
            throw new DuplicateKeyException(ErrorCode.ID_ALREADY_EXIST.getMessage());
        }

        if (userRepository.existsByNickname(signUpDto.getNickname())) {
            throw new DuplicateKeyException(ErrorCode.NICKNAME_ALREADY_EXIST.getMessage());
        }

        userRepository.save(user);
    }

    @Override
    public AuthApiResponse login(UserLoginRequest loginDto) {

        User entity = loginDto.toEntity(passwordEncoder);
        User user = userRepository.findByUserId(entity.getUserId())
            .orElseThrow(() -> new NullPointerException(ErrorCode.ID_NOT_EXIST.getMessage()));
        user.changeDeviceToken(user.getDeviceToken());

        UsernamePasswordAuthenticationToken authenticationToken = loginDto.toAuthentication();
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication, user.getUserId());

        RefreshToken refreshToken = RefreshToken.builder()
            .refreshKey(authentication.getName())
            .refreshValue(tokenDto.getRefreshToken())
            .build();

        refreshTokenRepository.save(refreshToken);


        return AuthApiResponse.of(user,tokenDto);
    }

    @Override
    public AuthApiResponse reissue(TokenRequestDto tokenRequestDto) {

        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException(ErrorCode.REFRESH_TOKEN_IS_NOT_VALID.getMessage());
        }

        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        User user = userRepository.findById(Long.parseLong(authentication.getName()))
            .orElseThrow(() -> new NullPointerException(ErrorCode.ID_NOT_EXIST.getMessage()));
        RefreshToken refreshToken = refreshTokenRepository.findById(authentication.getName())
            .orElseThrow(() -> new RuntimeException(ErrorCode.ALREADY_LOGOUT.getMessage()));

        if (!refreshToken.getRefreshValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException(ErrorCode.USER_INFO_NOT_MATCH.getMessage());
        }

        TokenDto tokenDto = tokenProvider.generateTokenDto(authentication,user.getUserId());
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);


        return AuthApiResponse.of(user,tokenDto);
    }

    @Override
    public boolean checkDuplicateNickname(String nickname){
        return userRepository.existsByNickname(nickname);
    }

    @Override
    public boolean checkDuplicateUserId(String userId){
        return userRepository.existsByUserId(userId);
    }
}
