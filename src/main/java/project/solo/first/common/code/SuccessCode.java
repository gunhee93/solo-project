package project.solo.first.common.code;

import lombok.Getter;

@Getter
public enum SuccessCode {

    SIGNUP_SUCCESS("SignUp", "회원가입에 성공하였습니다.");

    private final String code;
    private final String message;


    SuccessCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
