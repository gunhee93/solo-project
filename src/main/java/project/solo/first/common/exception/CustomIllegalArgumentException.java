package project.solo.first.common.exception;

import lombok.Getter;
import project.solo.first.common.code.ErrorCode;

@Getter
public class CustomIllegalArgumentException extends IllegalArgumentException {

    private final ErrorCode errorCode;


    public CustomIllegalArgumentException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
