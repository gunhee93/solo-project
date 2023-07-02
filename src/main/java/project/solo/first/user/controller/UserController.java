package project.solo.first.user.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.solo.first.common.code.SuccessCode;
import project.solo.first.common.response.ApiResponse;
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
}
