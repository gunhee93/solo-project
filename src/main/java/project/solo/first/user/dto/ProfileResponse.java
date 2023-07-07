package project.solo.first.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProfileResponse {

    private String loginId;
    private String email;
    private String nickname;
}
