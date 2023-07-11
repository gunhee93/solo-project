package project.solo.first.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateProfileResponse {

    private String loginId;
    private String email;
    private String nickname;
}
