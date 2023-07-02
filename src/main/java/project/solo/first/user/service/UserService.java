package project.solo.first.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.solo.first.user.domain.User;
import project.solo.first.user.dto.SignupRequest;
import project.solo.first.user.repository.UserRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public void signup(SignupRequest signupRequest) {
        User saveUser = signupRequest.toEntity();

        userRepository.save(saveUser);
    }
}
