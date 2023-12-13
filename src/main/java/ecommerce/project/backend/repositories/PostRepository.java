package ecommerce.project.backend.repositories;

import ecommerce.project.backend.entities.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByTitleContaining(String s, Sort sort);
    Page<Post> findAllByTitleContaining(String s, Pageable pageable);
}
