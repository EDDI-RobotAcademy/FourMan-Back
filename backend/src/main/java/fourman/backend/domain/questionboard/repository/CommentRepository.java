package fourman.backend.domain.questionboard.repository;

import fourman.backend.domain.questionboard.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
