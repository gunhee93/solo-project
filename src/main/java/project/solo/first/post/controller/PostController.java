package project.solo.first.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.solo.first.post.dto.CreatePostRequest;
import project.solo.first.post.service.PostService;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/create")
    public void createPost(@Validated @RequestBody CreatePostRequest createPostRequest) {
        postService.createPost(createPostRequest);
    }
}
