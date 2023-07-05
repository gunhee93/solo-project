package project.solo.first.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.solo.first.post.domain.PostCategory;

public interface CategoryRepository extends JpaRepository<PostCategory, Long> {
}
