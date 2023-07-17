package project.solo.first.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NewestPostsDto {

    private Long postId;
    private String title;
    private String category;
    private String nickname;
    private String writeTime;
}
