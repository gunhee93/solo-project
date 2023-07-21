package project.solo.first.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdatePostRequest {

    private Long postId;
    private String category;
    private String title;
    private String content;

}
