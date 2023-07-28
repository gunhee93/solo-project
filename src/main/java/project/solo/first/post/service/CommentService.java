package project.solo.first.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.solo.first.common.code.ErrorCode;
import project.solo.first.common.exception.CustomIllegalStateException;
import project.solo.first.post.domain.Comment;
import project.solo.first.post.domain.Post;
import project.solo.first.post.dto.commentDto.CreateCommentRequest;
import project.solo.first.post.repository.CommentRepository;
import project.solo.first.user.domain.User;
import project.solo.first.user.service.UserService;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;
    private final UserService userService;

    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> {
                    throw new CustomIllegalStateException(ErrorCode.NOT_FOUND_COMMENT);
                });
    }

    public void createComment(CreateCommentRequest createCommentRequest) {
        Post post = postService.findById(createCommentRequest.getPostId());
        User user = userService.findById(createCommentRequest.getUserId());

        Comment comment = Comment.of(createCommentRequest, user, post);
        commentRepository.save(comment);

    }

    public void like(Long commentId) {
        Comment comment = findById(commentId);
        comment.addLikeCount();
    }

    public void cancelLike(Long commentId) {
        Comment comment = findById(commentId);
        comment.cancelLikeCount();
    }
}
