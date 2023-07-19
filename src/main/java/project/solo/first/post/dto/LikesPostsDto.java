package project.solo.first.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikesPostsDto {

    private Long postId;
    private Long count;
    private Long like;
    private String title;
    private String category;
    private String nickname;
    private String writeTime;
}
