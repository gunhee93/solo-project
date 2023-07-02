package project.solo.first.post.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import project.solo.first.user.domain.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "COMMENT")
public class Comment {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "comment_like_count")
    private Long like;

    private String comment;

    public void mappingPost(Post post) {
        this.post = post;
        post.mappingComments(this);
    }

    @Builder
    public Comment(LocalDateTime createdAt, LocalDateTime modifiedAt, Post post, User user, String comment) {
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.post = post;
        this.user = user;
        this.comment = comment;
    }


}
