package project.solo.first.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.solo.first.common.code.SuccessCode;
import project.solo.first.common.response.ApiResponse;
import project.solo.first.post.dto.commentDto.CreateCommentRequest;
import project.solo.first.post.service.CommentService;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity createComment(@Validated @RequestBody CreateCommentRequest createCommentRequest) {
        commentService.createComment(createCommentRequest);

        return new ResponseEntity(new ApiResponse(SuccessCode.CREATED_COMMENT), HttpStatus.OK);
    }
}
