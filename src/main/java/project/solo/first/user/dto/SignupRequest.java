package project.solo.first.user.dto;

import lombok.Getter;
import project.solo.first.user.domain.Role;
import project.solo.first.user.domain.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class SignupRequest {

    @NotBlank(message = "아이디를 입력해 주세요")
    private String loginId;
    @NotBlank(message = "비밀번호를 입력해 주세요")
    private String password;
    @NotBlank(message = "닉네임을 입력해 주세요")
    private String nickname;
    private Role role;
    @Email(message = "이메일 형식으로 입력해 주세요")
    @NotBlank(message = "이메일을 입력해 주세요")
    private String email;

    public User toEntity() {
        return User.builder()
                .role(role)
                .loginId(loginId)
                .password(password)
                .nickname(nickname)
                .email(email)
                .build();
    }
}
