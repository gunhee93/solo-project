package project.solo.first.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateProfileRequest {

    @NotBlank(message = "로그인 아이디를 입력하세요")
    private String loginId;

    @NotBlank(message = "이메일을 입력하세요")
    private String email;

    @NotBlank(message = "닉네임을 입력하세요")
    private String nickname;
}
