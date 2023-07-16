package project.solo.first.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.solo.first.post.domain.Category;
import project.solo.first.post.domain.Post;
import project.solo.first.post.dto.CategoryDto;
import project.solo.first.post.dto.CreatePostRequest;
import project.solo.first.post.repository.CategoryRepository;
import project.solo.first.post.repository.PostRepository;
import project.solo.first.user.domain.User;
import project.solo.first.user.service.UserService;

import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;
    private final UserService userService;


    // 게시글 작성 (카테고리를 저장하는 과정에서 쿼리가 여러번 나가는 문제 해결 필요)
    public void createPost(CreatePostRequest createPostRequest) {
        User user = userService.findById(createPostRequest.getUserId());
        Category category = Category.of(createPostRequest.getCategoryName());
        Category savedCategory = categoryRepository.save(category);
        Post post = Post.of(createPostRequest, user, savedCategory);
        postRepository.save(post);
    }

//    public CategoryDto createCategoryRoot() {
//        Map<Long, List<CategoryDto>> groupingByParent = categoryRepository.findAll()
//                .stream()
//                .map(ce -> new CategoryDto(ce.getId(), ce.getName(), ce.getParent()))
//                .collect(groupingBy(cd -> cd.))
//    }
}
