package project.solo.first.common.exception;

import lombok.Getter;
import project.solo.first.common.code.ErrorCode;

@Getter
public class UnauthorizedException extends RuntimeException {

    private final ErrorCode errorCode;

    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
