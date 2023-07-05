package project.solo.first.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.solo.first.post.dto.CategoryDto;
import project.solo.first.post.repository.CategoryRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final CategoryRepository categoryRepository;

    public CategoryDto createCategoryRoot() {
        Map<Long, List<CategoryDto>> groupingByParent = categoryRepository.findAll()
                .stream()
                .map(ce -> new CategoryDto(ce.getId(), ce.getName(), ce.getParentId()))
                .collect(groupingBy(cd -> cd.))
    }
}
