package project.solo.first.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class LikesListResponse {

    List<LikesPostsDto> likesPostsList;
}
