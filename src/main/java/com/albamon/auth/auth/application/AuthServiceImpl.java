package com.albamon.auth.auth.application;

import java.util.HashMap;

import org.json.simple.JSONObject;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

import java.util.Optional;
import java.util.Random;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


import com.albamon.auth.auth.api.dto.AuthApiResponse;
import com.albamon.auth.auth.api.dto.EmailSMSRequest;
import com.albamon.auth.auth.api.dto.PhoneSMSRequest;
import com.albamon.auth.auth.api.dto.TokenDto;
import com.albamon.auth.auth.api.dto.TokenRequestDto;
import com.albamon.auth.auth.api.dto.UserLoginRequest;
import com.albamon.auth.auth.api.dto.UserSignUpRequest;
import com.albamon.auth.auth.domain.EmailSMS;
import com.albamon.auth.auth.domain.PhoneSMS;
import com.albamon.auth.auth.domain.RefreshToken;
import com.albamon.auth.auth.repository.EmailRedisRepository;
import com.albamon.auth.auth.repository.PhoneRedisRepository;
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
    private final JavaMailSender emailSender;
    private final PhoneRedisRepository phoneRedisRepository;
    private final EmailRedisRepository emailRedisRepository;


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

    @Override
    public void checkPhoneNumber(PhoneSMSRequest dto) {

        PhoneSMS phoneSMS = phoneRedisRepository.findById(dto.getPhoneNumber())
            .orElseThrow();

        if (!phoneSMS.getCode().equals(dto.getCode()) ){
            throw new IllegalArgumentException("코드 틀렸습니다.");
        }


    }

    public void certifiedPhoneNumber(String phoneNumber, String cerNum) {

        String api_key = "NCSROQ70N3X3CU6A";
        String api_secret = "QW5S9RO93B6MSLQEHQA4D2XQKHHYNGNG";
        Message coolsms = new Message(api_key, api_secret);

        // 4 params(to, from, type, text) are mandatory. must be filled
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("to", phoneNumber);    // 수신전화번호
        params.put("from", "01079286788");    // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        params.put("type", "SMS");
        params.put("text", "핫띵크 휴대폰인증 테스트 메시지 : 인증번호는" + "["+cerNum+"]" + "입니다.");
        params.put("app_version", "test app 1.2"); // application name and version

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);

            PhoneSMS phoneSMS = new PhoneSMS(phoneNumber,cerNum);
            phoneRedisRepository.save(phoneSMS);

            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
            throw new IllegalArgumentException("sms 보내는 도중 오류 발생" + e.getMessage());
        }

    }




    public static final String ePw = createKey();

    private MimeMessage createMessage(String to)throws Exception{
        System.out.println("보내는 대상 : "+ to);
        System.out.println("인증 번호 : "+ePw);
        MimeMessage  message = emailSender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to);//보내는 대상
        message.setSubject("Babble회원가입 이메일 인증");//제목

        String msgg="";
        msgg+= "<div style='margin:100px;'>";
        msgg+= "<h1> 안녕하세요 Babble입니다. </h1>";
        msgg+= "<br>";
        msgg+= "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
        msgg+= "<br>";
        msgg+= "<p>감사합니다!<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= ePw+"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("ensu7928@gmail.com","Job Korea & Albamon 테스트 API"));//보내는 사람

        EmailSMS emailSMS = new EmailSMS(to,ePw);

        emailRedisRepository.save(emailSMS);


        return message;
    }

    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }

        return key.toString();
    }
    @Override
    public String sendSimpleMessage(String to)throws Exception {
        // TODO Auto-generated method stub
        MimeMessage message = createMessage(to);
        try{//예외처리
            emailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException("이메일 발송 도중 오류");
        }
        return ePw;
    }

    @Override
    public void checkMessage(EmailSMSRequest dto) {



        EmailSMS emailSMS = emailRedisRepository.findById(dto.getEmail())
            .orElseThrow();

        System.out.println(emailSMS);

        if (!emailSMS.getCode().equals(dto.getCode())){
            throw new IllegalArgumentException("코드 틀렸습니다.");
        }

    }
}
