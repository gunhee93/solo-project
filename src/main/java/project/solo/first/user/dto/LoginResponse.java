package project.solo.first.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import project.solo.first.jwt.dto.TokenResponse;

@Getter
public class LoginResponse {

    private String nickname;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;


    public LoginResponse(String nickname, TokenResponse tokenResponse) {
        this.nickname = nickname;
        this.accessToken = tokenResponse.getAccessToken();
        this.refreshToken = tokenResponse.getRefreshToken();
        this.accessTokenExpiresIn = tokenResponse.getAccessTokenExpiresIn();
    }
}
