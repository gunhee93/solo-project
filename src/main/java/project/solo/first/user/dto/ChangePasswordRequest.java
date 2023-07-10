package project.solo.first.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class ChangePasswordRequest {

    private Long userId;

    @NotBlank(message = "비밀번호를 입력해 주세요")
    private String password;

    @NotBlank(message = "비밀번호를 다시 입력해 주세요")
    private String passwordCheck;
}
