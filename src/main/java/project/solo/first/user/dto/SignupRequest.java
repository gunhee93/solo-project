package project.solo.first.user.dto;

import lombok.Getter;
import project.solo.first.user.domain.Role;
import project.solo.first.user.domain.User;

@Getter
public class SignupRequest {

    private String loginId;
    private String password;
    private String nickname;
    private Role role;
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
