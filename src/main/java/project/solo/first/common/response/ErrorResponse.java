package project.solo.first.common.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.solo.first.common.code.ErrorCode;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    private int status;
    private String divisionCode;
    private String message;
    private String reason;

    protected ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.divisionCode = errorCode.getDivisionCode();
        this.message = errorCode.getMessage();
    }

    protected ErrorResponse(ErrorCode errorCode, String reason) {
        this.status = errorCode.getStatus();
        this.divisionCode = errorCode.getDivisionCode();
        this.message = errorCode.getMessage();
        this.reason = reason;
    }

    public static ErrorResponse from(ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }

    public static ErrorResponse of(ErrorCode errorCode, String reason) {
        return new ErrorResponse(errorCode, reason);
    }
}
