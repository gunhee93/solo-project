package project.solo.first.common.exception;

import lombok.Getter;
import project.solo.first.common.code.ErrorCode;

@Getter
public class CustomIllegalStateException extends IllegalStateException {

    private final ErrorCode errorCode;

    public CustomIllegalStateException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
