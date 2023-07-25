package project.solo.first.post.dto.postDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CategoryDto {

    private Long id;
    private String name;
    private Long parentId;
    private List<CategoryDto> subCategories;

    public CategoryDto(Long id, String name, Long parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }
}
