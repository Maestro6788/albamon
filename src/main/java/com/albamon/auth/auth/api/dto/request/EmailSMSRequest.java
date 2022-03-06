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
public class EmailSMSRequest {

    @NotBlank(message = "Input Your ID")
    private String email;
    @NotBlank(message = "Input Your code")
    private String code;



}
