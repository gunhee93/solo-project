package project.solo.first.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.solo.first.post.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
