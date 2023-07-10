package project.solo.first.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class FindPasswordRequest {

    @Email(message = "이메일 형식으로 입력해 주세요")
    @NotBlank(message = "이메일을 입력해 주세요")
    private String email;

    @NotBlank(message = "아이디를 입력해 주세요")
    private String loginId;

    @NotBlank(message = "인증 코드를 입력해 주세요")
    private String code;
}
