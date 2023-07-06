package project.solo.first.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.solo.first.common.code.ErrorCode;
import project.solo.first.common.exception.CustomIllegalStateException;
import project.solo.first.common.util.RedisUtil;
import project.solo.first.jwt.TokenProvider;
import project.solo.first.jwt.dto.TokenResponse;
import project.solo.first.user.domain.User;
import project.solo.first.user.dto.LoginRequest;
import project.solo.first.user.dto.LoginResponse;
import project.solo.first.user.dto.SignupRequest;
import project.solo.first.user.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final RedisUtil redisUtil;


    public void signup(SignupRequest signupRequest) {
        User saveUser = signupRequest.toEntity();
        saveUser.encodingPassword(encoder.encode(signupRequest.getPassword()));

        userRepository.save(saveUser);
    }

    public void duplicateLoginId(String loginId) {
        userRepository.findByLoginId(loginId)
                .ifPresent(d -> {
                    throw new CustomIllegalStateException(ErrorCode.DUPLICATE_LOGIN_ID);
                });
    }

    public void duplicateEmail(String email) {
        userRepository.findByEmail(email)
                .ifPresent(d -> {
                    throw new CustomIllegalStateException(ErrorCode.DUPLICATE_EMAIL);
                });
    }

    public LoginResponse login(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginRequest.getLoginId(), loginRequest.getPassword());
        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        User requestUser = findById(Long.valueOf(authenticate.getName()));

        TokenResponse tokenResponse = tokenProvider.generateTokenDto(authenticate);
        redisUtil.setDataExpire(authenticate.getName(), tokenResponse.getRefreshToken(), 1000 * 60 * 60 * 24 * 7);

        return new LoginResponse(requestUser.getNickname(), tokenResponse);
    }

    private User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    throw new CustomIllegalStateException(ErrorCode.NOT_FOUND_USER);
                });
    }
}
