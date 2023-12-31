package project.solo.first.user.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.solo.first.common.code.SuccessCode;
import project.solo.first.common.response.ApiResponse;
import project.solo.first.jwt.dto.TokenResponse;
import project.solo.first.user.dto.*;
import project.solo.first.user.service.EmailService;
import project.solo.first.user.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final EmailService emailService;

    // 회원 가입
    @PostMapping("/signup")
    public ResponseEntity signup(@Validated @RequestBody SignupRequest signupRequest) {
        userService.signup(signupRequest);

        return new ResponseEntity(new ApiResponse(SuccessCode.SIGNUP_SUCCESS), HttpStatus.OK);
    }

    //회원 탈퇴
    @DeleteMapping("/delete")
    public ResponseEntity deleteUser(@RequestHeader(value = "Authorization") String acTokenRequest,
                                     @Validated @RequestBody String password) {
        userService.deleteUser(acTokenRequest, password);

        return new ResponseEntity(new ApiResponse(SuccessCode.DELETE_USER), HttpStatus.OK);
    }

    // 아이디 중복 확인
    @PostMapping("/signup/id")
    public ResponseEntity duplicateId(@RequestBody String loginId) {
        userService.duplicateLoginId(loginId);

        return new ResponseEntity(new ApiResponse(SuccessCode.CAN_USE_ID), HttpStatus.OK);
    }

    // 이메일 중복 확인
    @PostMapping("/signup/email")
    public ResponseEntity duplicateEmail(@RequestBody String email) {
        userService.duplicateEmail(email);

        return new ResponseEntity(new ApiResponse(SuccessCode.CAN_USE_EMAIL), HttpStatus.OK);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity login(@Validated @RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = userService.login(loginRequest);

        return new ResponseEntity(loginResponse, HttpStatus.OK);
    }

    // 로그아웃
    @GetMapping("/logout")
    public ResponseEntity logout(@RequestHeader(value = "Authorization") String acTokenRequest,
                                 @RequestHeader(value = "RefreshToken") String rfTokenRequest) {
        String accessToken = acTokenRequest.substring(7);
        String refreshToken = rfTokenRequest.substring(7);

        userService.logout(accessToken, refreshToken);

        return new ResponseEntity(new ApiResponse(SuccessCode.LOGOUT_SUCCESS), HttpStatus.OK);
    }

    // 마이페이지
    @GetMapping("/mypage")
    public ResponseEntity myPage(@RequestHeader(value = "Authorization") String acTokenRequest) {
        ProfileResponse userProfile = userService.getProfile(acTokenRequest);

        return new ResponseEntity(userProfile, HttpStatus.OK);
    }

    // 정보 수정
    @PatchMapping("/mypage")
    public ResponseEntity updateProfile(@RequestHeader(value = "Authorization") String acTokenRequest,
                                        @Validated @RequestBody UpdateProfileRequest updateProfileRequest) {
        UpdateProfileResponse updateProfileResponse = userService
                .updateProfile(acTokenRequest, updateProfileRequest);

        return new ResponseEntity(updateProfileResponse, HttpStatus.OK);
    }

    // 이메일 인증코드 전송
    @PostMapping("/email")
    public ResponseEntity authEmail(@Validated @RequestBody EmailRequest request) {
        emailService.sendEmail(request.getEmail());

        return new ResponseEntity(new ApiResponse(SuccessCode.SEND_EMAIL), HttpStatus.OK);
    }

    // 아이디 찾기
    @PostMapping("/find/id")
    public ResponseEntity findLoginId(@Validated @RequestBody FindLoginIdRequest findLoginIdRequest) {
        String loginId = userService.findLoginId(findLoginIdRequest);

        return new ResponseEntity(loginId, HttpStatus.OK);
    }

    // 비밀번호 찾기
    @PostMapping("/find/password")
    public ResponseEntity findPassword(@Validated @RequestBody FindPasswordRequest findPasswordRequest) {
        Long userId = userService.findPassword(findPasswordRequest);

        return new ResponseEntity(userId, HttpStatus.OK);
    }

    // 비밀번호 찾기 이후 비밀번호 변경
    @PatchMapping("/change/password")
    public ResponseEntity changePassword(@Validated @RequestBody ChangePasswordRequest changePasswordRequest) {
        userService.changePassword(changePasswordRequest);

        return new ResponseEntity(new ApiResponse(SuccessCode.CHANGE_PASSWORD_SUCCESS), HttpStatus.OK);
    }

    // 토큰 재발급
    @PostMapping("/reissue")
    public ResponseEntity reissue(
            @RequestHeader(value = "Authorization") String acTokenRequest,
            @RequestHeader(value = "RefreshToken") String rfTokenRequest
    ) {


        TokenResponse tokenResponse = userService.reissue(acTokenRequest, rfTokenRequest);

        return new ResponseEntity<>(tokenResponse, HttpStatus.OK);
    }
}
