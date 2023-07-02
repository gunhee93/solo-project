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
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
//@MappedSuperclass  // jpa entity 클래스들이 해당 추상 클래스를 상속할 경우 createDate, modifiedDate 를 컬럼으로 인식
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "POST")
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_category_id")
    private PostCategory postCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

    @Column(name = "post_view_count")
    private Long count;

    @Column(name = "post_like_count")
    private Long like;

    private String title;
    private String content;


    public void mappingCategory(PostCategory postCategory) {
        this.postCategory = postCategory;
        postCategory.mappingPost(this);
    }

    public void mappingComments(Comment comment) {
        this.comments.add(comment);
    }

    public void postCount() {
        this.count++;
    }

    @Builder
    public Post(PostCategory postCategory, User user, List<Comment> comments, String title, String content,
                LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.postCategory = postCategory;
        this.user = user;
        this.comments = comments;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

}
