package com.albamon.auth.common;

import com.albamon.auth.user.domain.Authority;

public class AuthInfo {
	public static String AUTH_KEY = "AUTH_KEY"; // session에 key로 스트링을 넣을 때 사용

	private Long id;
	private String userId;
	private UserType userType;


	protected AuthInfo(){

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public static AuthInfo of(Long id, String userId, UserType userType){
		AuthInfo authInfo =  new AuthInfo();
		authInfo.id = id;
		authInfo.userId = userId;
		authInfo.userType = userType;
		return authInfo;
	}



	public static AuthInfo userOf(Long id ){
		return of(id,null,UserType.NOT_ASSIGNED);
	}
	public static AuthInfo companyOf(Long id ,String userId){
		return of(id, userId,UserType.COMPANY);
	}
	public static AuthInfo jobSeekerOf(Long id ,String userId){
		return of(id, userId,UserType.JOB_SEEKER);
	}

	public AuthInfo(String userId, UserType userType) {
		this.userId = userId;
		this.userType = userType;
	}

	@Override
	public String toString() {
		return "AuthInfo{" +
			"userId='" + userId + '\'' +
			", userType=" + userType +
			'}';
	}
}

