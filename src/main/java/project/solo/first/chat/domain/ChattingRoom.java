package project.solo.first.chat.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.solo.first.user.domain.User;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "ROOM")
public class ChattingRoom {

    @Id @GeneratedValue
    @Column(name = "room_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "send_user_id")
    private User sendUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receive_user_id")
    private User receiveUser;

    public ChattingRoom(User sendUser, User receiveUser) {
        this.sendUser = sendUser;
        this.receiveUser = receiveUser;
    }

    public void enterUser(User receiveUser) {
        this.receiveUser = receiveUser;
    }
}
