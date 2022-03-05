package com.albamon.auth.auth.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhoneSMSRequest {

    @NotBlank(message = "Input Your ID")
    private String phoneNumber;
    @NotBlank(message = "Input Your Password")
    private String code;



}
