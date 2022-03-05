package com.albamon.auth.security.authentication;

import com.albamon.auth.user.domain.Authority;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthInfo {
	public static String AUTH_KEY = "AUTH_KEY"; // session에 key로 스트링을 넣을 때 사용

	private Long id;
	private Authority userType; //


	public static AuthInfo of(Long id, Authority userType){
		AuthInfo authInfo =  new AuthInfo();
		authInfo.id = id;
		authInfo.userType = userType;
		return authInfo;
	}

	public static AuthInfo UserOf(Long id){
		return of(id,Authority.COMPANY);
	}
	public static AuthInfo AdminOf(Long id){
		return of(id,Authority.COMPANY);
	}

}

