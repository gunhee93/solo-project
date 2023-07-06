package project.solo.first.user.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.solo.first.common.code.SuccessCode;
import project.solo.first.common.response.ApiResponse;
import project.solo.first.user.dto.LoginRequest;
import project.solo.first.user.dto.LoginResponse;
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

    @GetMapping("/login")
    public ResponseEntity login(LoginRequest loginRequest) {
        LoginResponse loginResponse = userService.login(loginRequest);

        return new ResponseEntity(loginResponse, HttpStatus.OK);
    }
}
