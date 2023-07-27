package project.solo.first.post.dto.commentDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
public class CreateCommentRequest {

    private Long postId;
    private Long userId;
    @NotBlank(message = "내용을 입력하세요")
    private String content;
}
