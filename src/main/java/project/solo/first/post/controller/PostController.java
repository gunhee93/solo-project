package project.solo.first.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.solo.first.common.code.SuccessCode;
import project.solo.first.common.response.ApiResponse;
import project.solo.first.post.dto.CreatePostRequest;
import project.solo.first.post.dto.NewestListResponse;
import project.solo.first.post.dto.NewestPostsDto;
import project.solo.first.post.dto.ViewedListResponse;
import project.solo.first.post.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // 게시글 작성
    @PostMapping("/create")
    public ResponseEntity createPost(@Validated @RequestBody CreatePostRequest createPostRequest) {
        postService.createPost(createPostRequest);

        return new ResponseEntity(new ApiResponse(SuccessCode.CREATED_POST), HttpStatus.OK);
    }

    // 게시판 홈(최신순)
    @GetMapping("/newest")
    public ResponseEntity postsNewest(@PageableDefault(size = 10, sort = {"id"}) Pageable pageable) {
        NewestListResponse newestListResponse = postService.findPostsNewest(pageable);

        return new ResponseEntity(newestListResponse, HttpStatus.OK);
    }

    // 게시판 홈(조회순)
    @GetMapping("/viewed")
    public ResponseEntity postsViewed(@PageableDefault(size = 10, sort = {"id"}) Pageable pageable) {
        ViewedListResponse viewedListResponse = postService.findPostsViewed(pageable);

        return new ResponseEntity(viewedListResponse, HttpStatus.OK);
    }
}
