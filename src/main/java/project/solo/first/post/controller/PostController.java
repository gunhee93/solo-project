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
import project.solo.first.post.dto.postDto.*;
import project.solo.first.post.service.PostService;

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

    // 게시글 삭제
    @DeleteMapping("/delete")
    public ResponseEntity deletePost(@Validated @RequestBody DeletePostRequest deletePostRequest) {
        postService.deletePost(deletePostRequest);

        return new ResponseEntity(new ApiResponse(SuccessCode.DELETE_POST), HttpStatus.OK);
    }

    // 게시글 수정
    @PatchMapping("/update")
    public ResponseEntity updatePost(@Validated @RequestBody UpdatePostRequest updatePostRequest) {
        UpdatePostResponse updatePostResponse = postService.updatePost(updatePostRequest);

        return new ResponseEntity(updatePostResponse, HttpStatus.OK);
    }

    // 게시글 상세
    @GetMapping("/{postId}")
    public ResponseEntity viewPost(@PathVariable("postId") Long postId) {
        ViewPostResponse viewPostResponse = postService.viewPost(postId);

        return new ResponseEntity(viewPostResponse, HttpStatus.OK);
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

    // 게시판 홈(좋아요순)
    @GetMapping("/like")
    public ResponseEntity postsLikes(@PageableDefault(size = 10, sort = {"id"}) Pageable pageable) {
        LikesListResponse likesListResponse = postService.findPostsLikes(pageable);

        return new ResponseEntity(likesListResponse, HttpStatus.OK);
    }


}
