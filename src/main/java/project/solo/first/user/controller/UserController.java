package project.solo.first.user.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.solo.first.common.code.SuccessCode;
import project.solo.first.common.response.ApiResponse;
import project.solo.first.common.util.SecurityUtil;
import project.solo.first.user.dto.LoginRequest;
import project.solo.first.user.dto.LoginResponse;
import project.solo.first.user.dto.ProfileResponse;
import project.solo.first.user.dto.SignupRequest;
import project.solo.first.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity signup(SignupRequest signupRequest) {
        userService.signup(signupRequest);

        return new ResponseEntity(new ApiResponse(SuccessCode.SIGNUP_SUCCESS), HttpStatus.OK);
    }

    @PostMapping("/signup/id")
    public ResponseEntity duplicateId(String loginId) {
        userService.duplicateLoginId(loginId);

        return new ResponseEntity(new ApiResponse(SuccessCode.CAN_USE_ID), HttpStatus.OK);
    }

    @PostMapping("/signup/email")
    public ResponseEntity duplicateEmail(String email) {
        userService.duplicateEmail(email);

        return new ResponseEntity(new ApiResponse(SuccessCode.CAN_USE_EMAIL), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity login(LoginRequest loginRequest) {
        LoginResponse loginResponse = userService.login(loginRequest);

        return new ResponseEntity(loginResponse, HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity logout(@RequestHeader(value = "Authorization") String acTokenRequest,
                                 @RequestHeader(value = "RefreshToken") String rfTokenRequest) {
        String accessToken = acTokenRequest.substring(7);
        String refreshToken = rfTokenRequest.substring(7);

        userService.logout(accessToken, refreshToken);

        return new ResponseEntity(new ApiResponse(SuccessCode.LOGOUT_SUCCESS), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity myPage(@PathVariable(value = ("userId")) Long userId) {
        ProfileResponse userProfile = userService.getProfile(userId);

        return new ResponseEntity(userProfile, HttpStatus.OK);
    }
}
