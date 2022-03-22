package com.albamon.auth.board.api;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeNicknameRequest {


    @NotBlank(message = "Input Your nickname")
    private String nickname;





}
