package project.solo.first.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.solo.first.user.domain.User;
import project.solo.first.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        return userRepository.findByLoginId(loginId)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(loginId + "를 찾을 수 없습니다."));


    }

    private UserDetails createUserDetails(User user) {
        return new PrincipalDetail(user);
    }
}
