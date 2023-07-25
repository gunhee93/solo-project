package project.solo.first.post.dto.commentDto;

import lombok.Getter;
import project.solo.first.post.domain.Comment;

import java.time.format.DateTimeFormatter;

@Getter
public class CommentResponse {

    private Long like;
    private String nickname;
    private String comment;
    private String createdAt;

    public CommentResponse(Comment comment) {
        this.like = comment.getLike();
        this.nickname = comment.getUser().getNickname();
        this.comment = comment.getComment();
        this.createdAt = comment.getCreatedAt().format(
                DateTimeFormatter.ofPattern("dd HH:mm")
        );
    }
}
