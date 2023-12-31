package project.solo.first.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import project.solo.first.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "select p from Post p " +
            "join fetch p.user u " +
            "join fetch p.category c " +
            "order by p.createdAt desc",
    countQuery = "select count(p) from Post p")
    Page<Post> findAllByNewest(Pageable pageable);

    @Query(value = "select p from Post p " +
            "join fetch p.user u " +
            "join fetch p.category c " +
            "order by p.count desc",
    countQuery = "select count(p) from Post p")
    Page<Post> findAllByViewed(Pageable pageable);

    @Query(value = "select p from Post p " +
            "join fetch p.user u " +
            "join fetch p.category c " +
            "order by p.like desc",
    countQuery = "select count(p) from Post p")
    Page<Post> findAllByLikes(Pageable pageable);
}
