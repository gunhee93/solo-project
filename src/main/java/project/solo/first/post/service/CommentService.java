package project.solo.first.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    public void createComment(CreateCommentRequest createCommentRequest) {
        Post post = postService.findById(createCommentRequest.getPostId());
        User user = userService.findById(createCommentRequest.getUserId());

        Comment comment = Comment.of(createCommentRequest, user, post);
        commentRepository.save(comment);
        
    }
}
