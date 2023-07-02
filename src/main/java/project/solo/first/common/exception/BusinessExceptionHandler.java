package project.solo.first.common.exception;

import lombok.Getter;
import project.solo.first.common.code.ErrorCode;

@Getter
public class BusinessExceptionHandler extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessExceptionHandler(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
