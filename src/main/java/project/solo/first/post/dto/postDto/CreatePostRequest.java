package project.solo.first.post.dto.postDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatePostRequest {

    private String categoryName;
    private Long userId;
    private String title;
    private String content;
}
