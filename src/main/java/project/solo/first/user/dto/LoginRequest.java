package project.solo.first.user.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class LoginRequest {

    @NotBlank(message = "아이디를 입력해 주세요")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력해 주세요")
    private String password;
}
