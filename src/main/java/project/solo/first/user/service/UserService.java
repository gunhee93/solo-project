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
import project.solo.first.user.dto.*;
import project.solo.first.user.repository.UserRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final BCryptPasswordEncoder encoder;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final RedisUtil redisUtil;

    private User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> {
                    throw new CustomIllegalStateException(ErrorCode.NOT_FOUND_USER);
                });
    }

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

    public void logout(String accessToken, String refreshToken) {
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new CustomIllegalStateException(ErrorCode.INVALID_TOKEN);
        }

        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        String userId = authentication.getName();

        String redisRefreshToken = redisUtil.getData(userId);
        if (redisRefreshToken != null) {
            redisUtil.deleteData(userId);
        }

        Long expiration = tokenProvider.getExpiration(accessToken);
        redisUtil.setDataExpire(accessToken, "logout", expiration);
    }

    public ProfileResponse getProfile(String acTokenRequest) {
        String accessToken = acTokenRequest.substring(7);
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        String strUserId = authentication.getName();
        Long userId = Long.parseLong(strUserId);

        User findUser = findById(userId);

        return new ProfileResponse(
                findUser.getLoginId(),
                findUser.getEmail(),
                findUser.getNickname()
        );
    }

    public UpdateProfileResponse updateProfile(String acTokenRequest, UpdateProfileRequest updateProfileRequest) {
        String accessToken = acTokenRequest.substring(7);
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        String strUserId = authentication.getName();
        Long userId = Long.parseLong(strUserId);

        User findUser = findById(userId);

        String updatedLoginId = updateProfileRequest.getLoginId();
        String updatedNickname = updateProfileRequest.getNickname();
        String updatedEmail = updateProfileRequest.getEmail();

        findUser.updateProfile(updatedLoginId, updatedEmail, updatedNickname);

        return new UpdateProfileResponse(updatedLoginId, updatedEmail, updatedNickname);
    }

    public String findLoginId(FindLoginIdRequest findLoginIdRequest) {
        String redisEmail = redisUtil.getData(findLoginIdRequest.getCode());
        if (!redisEmail.equals(findLoginIdRequest.getEmail())) {
            throw new CustomIllegalStateException(ErrorCode.NOT_MATCHED_CODE);
        }

        User findUserByEmail = userRepository.findByEmail(findLoginIdRequest.getEmail())
                .orElseThrow(() -> {
                    throw new CustomIllegalStateException(ErrorCode.NOT_FOUND_USER);
                });

        return findUserByEmail.getLoginId();
    }


    public Long findPassword(FindPasswordRequest findPasswordRequest) {
        String redisEmail = redisUtil.getData(findPasswordRequest.getCode());
        if (!redisEmail.equals(findPasswordRequest.getEmail())) {
            throw new CustomIllegalStateException(ErrorCode.NOT_MATCHED_CODE);
        }

        User findUser = userRepository
                .findByEmailAndLoginId(findPasswordRequest.getEmail(), findPasswordRequest.getLoginId())
                .orElseThrow(() -> {
                    throw new CustomIllegalStateException(ErrorCode.NOT_FOUND_USER);
                });

        return findUser.getId();
    }

    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        if (!changePasswordRequest.getPasswordCheck().equals(changePasswordRequest.getPassword())) {
            throw new CustomIllegalStateException(ErrorCode.NOT_MATCHED_PASSWORD);
        }

        User findUser = findById(changePasswordRequest.getUserId());
        findUser.changePassword(encoder.encode(changePasswordRequest.getPassword()));

    }

    public void deleteUser(String acTokenRequest, String password) {
        String accessToken = acTokenRequest.substring(7);
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        String strUserId = authentication.getName();
        Long userId = Long.parseLong(strUserId);

        User findUser = findById(userId);

        if (!findUser.getPassword().equals(password)) {
            throw new CustomIllegalStateException(ErrorCode.NOT_MATCHED_PASSWORD);
        }

        userRepository.delete(findUser);
    }

    public TokenResponse reissue(String acTokenRequest, String rfTokenRequest) {
        String accessToken = acTokenRequest.substring(7);
        String refreshToken = rfTokenRequest.substring(7);

        if (!tokenProvider.validateToken(refreshToken)) {
            throw new CustomIllegalStateException(ErrorCode.INVALID_TOKEN);
        }

        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        String userId = authentication.getName();

        String redisRefreshToken = redisUtil.getData(userId);
        if (redisRefreshToken == null) {
            throw new CustomIllegalStateException(ErrorCode.INVALID_TOKEN);
        }
        if (!refreshToken.equals(redisRefreshToken)) {
            throw new CustomIllegalStateException(ErrorCode.NO_MATCHED_INFO);
        }

        TokenResponse tokenResponse = tokenProvider.generateTokenDto(authentication);
        redisUtil.setDataExpire(userId, tokenResponse.getRefreshToken(), 1000 * 60 * 60 * 24 * 7);

        return tokenResponse;
    }
}
