package project.solo.first.common.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.solo.first.common.code.SuccessCode;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse {

    private String code;
    private String message;

    public ApiResponse(SuccessCode successCode) {
        this.code = successCode.getCode();
        this.message = successCode.getMessage();
    }
}
