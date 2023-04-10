package fourman.backend.domain.questionboard.repository;

import fourman.backend.domain.questionboard.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select c from Comment c join fetch c.questionBoard q where q.boardId = :boardId")
    List<Comment> findCommentByBoardId(Long boardId);

}
