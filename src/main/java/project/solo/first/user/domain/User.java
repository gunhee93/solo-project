package project.solo.first.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @CreatedDate
    private LocalDateTime signupDay;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String loginId;
    private String password;
    private String email;
    private String nickname;

    @Builder
    public User(Role role, String loginId, String password, String nickname, String email) {
        this.role = role;
        this.loginId = loginId;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
    }

    public static User of (Role role, String loginId, String password, String nickname, String email) {
        return User.builder()
                .loginId(loginId)
                .password(password)
                .nickname(nickname)
                .email(email)
                .role(role)
                .build();
    }

    //      패스워드 인코딩
    public void encodingPassword(String password) {
        this.password = password;
    }
}
