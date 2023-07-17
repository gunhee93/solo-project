package project.solo.first.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class NewestListResponse {

    List<NewestPostsDto> newestPostsList;
}
