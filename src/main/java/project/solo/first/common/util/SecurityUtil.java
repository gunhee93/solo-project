package project.solo.first.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import project.solo.first.common.code.ErrorCode;
import project.solo.first.common.exception.CustomIllegalStateException;

public class SecurityUtil {

    private SecurityUtil() {}

    public static Long getCurrentUserId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new CustomIllegalStateException(ErrorCode.NOT_FOUND_INFORMATION);
        }

        return Long.parseLong(authentication.getName());
    }
}
