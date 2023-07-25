package project.solo.first.post.dto.postDto;

import lombok.Getter;
import project.solo.first.post.domain.Post;
import project.solo.first.post.dto.commentDto.CommentResponse;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
public class ViewPostResponse {

    private Long count;
    private Long like;
    private String title;
    private String content;
    private String category;
    private String nickname;
    private String createdAt;
    private List<CommentResponse> commentList;

    public ViewPostResponse(Post post, List<CommentResponse> commentList) {
        this.count = post.getCount();
        this.like = post.getLike();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt().format(
                DateTimeFormatter.ofPattern("dd HH:mm"));
        this.category = post.getCategory().getName();
        this.nickname = post.getUser().getNickname();
        this.commentList = commentList;
    }
}
