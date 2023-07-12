package project.solo.first.common.code;

import lombok.Getter;

@Getter
public enum SuccessCode {

    SIGNUP_SUCCESS("SignUp", "회원가입에 성공하였습니다."),
    LOGIN_SUCCESS("Login", "로그인 되었습니다."),
    CAN_USE_ID("LoginId", "사용 가능한 아이디입니다."),
    CAN_USE_EMAIL("LoginEmail", "사용 가능한 이메일입니다."),
    LOGOUT_SUCCESS("Logout", "로그아웃 되었습니다."),
    SEND_EMAIL("Email", "인증 코드가 이메일로 발송되었습니다."),
    VALIDATED_CODE("ValidatedCode", "인증완료. 비밀번호 변경 페이지로 이동합니다."),
    CHANGE_PASSWORD_SUCCESS("ChangedPassword", "비밀번호가 변경되었습니다."),
    DELETE_USER("Delete", "회원이 삭제되었습니다.");

    private final String code;
    private final String message;


    SuccessCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
