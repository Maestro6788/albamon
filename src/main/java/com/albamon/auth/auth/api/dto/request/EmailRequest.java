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
public class EmailRequest {


    @NotBlank(message = "Input Your userId")
    private String userId;
    @NotBlank(message = "Input Your userName")
    private String userName;
    @NotBlank(message = "Input Your email")
    private String email;




}
