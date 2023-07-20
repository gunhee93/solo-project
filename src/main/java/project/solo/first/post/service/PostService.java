package project.solo.first.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.solo.first.common.code.ErrorCode;
import project.solo.first.common.exception.CustomIllegalStateException;
import project.solo.first.jwt.TokenProvider;
import project.solo.first.post.domain.Category;
import project.solo.first.post.domain.Post;
import project.solo.first.post.dto.*;
import project.solo.first.post.repository.CategoryRepository;
import project.solo.first.post.repository.PostRepository;
import project.solo.first.user.domain.User;
import project.solo.first.user.service.UserService;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;
    private final UserService userService;
    private final TokenProvider tokenProvider;


    // 게시글 작성 (카테고리를 저장하는 과정에서 쿼리가 여러번 나가는 문제 해결 필요)
    public void createPost(CreatePostRequest createPostRequest) {
        User user = userService.findById(createPostRequest.getUserId());
        Category category = Category.of(createPostRequest.getCategoryName());
        Category savedCategory = categoryRepository.save(category);
        Post post = Post.of(createPostRequest, user, savedCategory);
        postRepository.save(post);
    }

    public NewestListResponse findPostsNewest(Pageable pageable) {
        Page<Post> allByNewest = postRepository.findAllByNewest(pageable);

        List<NewestPostsDto> newestPostsDtoList = allByNewest.stream().map(post -> new NewestPostsDto(
                post.getId(), post.getCount(), post.getLike(), post.getTitle(), post.getUser().getNickname(),
                post.getCategory().getName(), post.getCreatedAt().format(DateTimeFormatter.ofPattern("MM dd HH:mm"))
        )).collect(Collectors.toList());

        return new NewestListResponse(newestPostsDtoList);
    }

    public ViewedListResponse findPostsViewed(Pageable pageable) {
        Page<Post> allByViewed = postRepository.findAllByViewed(pageable);

        List<ViewedPostsDto> viewedPostsDtoList = allByViewed.stream().map(post -> new ViewedPostsDto(
                post.getId(), post.getCount(), post.getLike(), post.getTitle(), post.getUser().getNickname(),
                post.getCategory().getName(), post.getCreatedAt().format(DateTimeFormatter.ofPattern("MM dd HH:mm"))
        )).collect(Collectors.toList());

        return new ViewedListResponse(viewedPostsDtoList);
    }

    public LikesListResponse findPostsLikes(Pageable pageable) {
        Page<Post> allByLikes = postRepository.findAllByLikes(pageable);

        List<LikesPostsDto> likesPostsDtoList = allByLikes.stream().map(post -> new LikesPostsDto(
                post.getId(), post.getCount(), post.getLike(), post.getTitle(), post.getUser().getNickname(),
                post.getCategory().getName(), post.getCreatedAt().format(DateTimeFormatter.ofPattern("MM dd HH:mm"))
        )).collect(Collectors.toList());

        return new LikesListResponse(likesPostsDtoList);
    }

    public void deletePost(String acTokenRequest, DeletePostRequest deletePostRequest) {
        String accessToken = acTokenRequest.substring(7);
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        String strUserId = authentication.getName();
        Long userId = Long.parseLong(strUserId);

        Post post = postRepository.findById(deletePostRequest.getPostId()).orElseThrow(() -> {
            throw new CustomIllegalStateException(ErrorCode.NOT_FOUND_POST);
        });

        postRepository.delete(post);
    }
}
