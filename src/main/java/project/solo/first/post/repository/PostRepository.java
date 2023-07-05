package project.solo.first.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.solo.first.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
