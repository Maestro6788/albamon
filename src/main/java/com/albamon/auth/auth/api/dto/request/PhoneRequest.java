package com.albamon.auth.auth.api.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhoneRequest {

    @NotBlank(message = "Input Your ID")
    private String phoneNumber;




}
