package com.albamon.auth.auth.application;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.albamon.auth.auth.api.dto.AuthApiResponse;
import com.albamon.auth.auth.api.dto.TokenRequestDto;
import com.albamon.auth.auth.api.dto.request.EmailSMSRequest;
import com.albamon.auth.auth.api.dto.request.PhoneSMSRequest;
import com.albamon.auth.auth.api.dto.request.UpdatePasswordByChangeRequest;
import com.albamon.auth.auth.domain.EmailSMS;
import com.albamon.auth.auth.domain.PhoneSMS;
import com.albamon.auth.auth.domain.RefreshToken;
import com.albamon.auth.auth.repository.EmailRedisRepository;
import com.albamon.auth.auth.repository.PhoneRedisRepository;
import com.albamon.auth.auth.repository.RefreshTokenRepository;
import com.albamon.auth.common.TokenDto;
import com.albamon.auth.common.response.ErrorCode;
import com.albamon.auth.security.jwt.TokenProvider;
import com.albamon.auth.user.domain.User;
import com.albamon.auth.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CommonAuthServiceImpl implements CommonAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PhoneRedisRepository phoneRedisRepository;
    private final EmailRedisRepository emailRedisRepository;





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
    public void checkPhoneNumber(PhoneSMSRequest dto) {

        PhoneSMS phoneSMS = phoneRedisRepository.findById(dto.getPhoneNumber())
            .orElseThrow(() -> new NullPointerException("????????? ????????? ????????????."));

        if (!phoneSMS.getCode().equals(dto.getCode()) ){
            throw new IllegalArgumentException("?????? ???????????????.");
        }


    }

    @Override
    public void checkMessage(EmailSMSRequest dto) {



        EmailSMS emailSMS = emailRedisRepository.findById(dto.getEmail())
            .orElseThrow(() -> new NullPointerException("????????? ????????? ????????????."));

        System.out.println(emailSMS);

        if (!emailSMS.getCode().equals(dto.getCode())){
            throw new IllegalArgumentException("?????? ???????????????.");
        }

    }



    @Override
    public AuthApiResponse changePassword(long id, UpdatePasswordByChangeRequest request) {

        // todo. dto > entity  ???????????? ?????? ??????
        // todo. ????????? ??? ?????????

        User user = userRepository.findById(id)
            .orElseThrow(() -> new NullPointerException("?????? ID??? ????????????."));

        if (!request.getCheckPassword().equals(request.getPassword())){
            throw new IllegalArgumentException("??????????????? ???????????? ????????????.");
        }

        user.changePassword(passwordEncoder.encode(request.getPassword()));

        return AuthApiResponse.finalPasswordChangeToRes(user);
    }
}
