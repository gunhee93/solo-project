package project.solo.first.post.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import project.solo.first.post.dto.CreatePostRequest;
import project.solo.first.post.dto.UpdatePostRequest;
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
    @JoinColumn(name = "category_id")
    private Category category;

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


    public void mappingComments(Comment comment) {
        this.comments.add(comment);
    }

    public void mappingCategory(Category category){
        this.category = category;
        category.mappingPost(this);
    }
    public void postCount() {
        this.count++;
    }

    @Builder
    public Post(Category category, User user, List<Comment> comments, String title, String content,
                LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.category = category;
        this.user = user;
        this.comments = comments;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    // 게시글 작성
    public static Post of(CreatePostRequest createPostRequest, User user, Category category) {
        return Post.builder()
                .title(createPostRequest.getTitle())
                .content(createPostRequest.getContent())
                .category(category)
                .user(user)
                .build();
    }

    // 게시글 수정
    public static Post updatePost(String title, String content) {
        return Post.builder()
                .title(title)
                .content(title)
                .build();
    }

    // 조회수 up
    public void addViewCount() {
        this.count++;
    }

    // 좋아요
    public void addLikeCount() {
        this.like++;
    }


}
