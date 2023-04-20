package fourman.backend.domain.questionboard.repository;

import fourman.backend.domain.questionboard.entity.Comment;
import fourman.backend.domain.questionboard.entity.QuestionBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select c from Comment c join fetch c.questionBoard q where q.boardId = :boardId")
    List<Comment> findCommentByBoardId(Long boardId);

    @Query("SELECT c FROM Comment c join fetch c.member m where m.id = :memberId")
    List<Comment> findCommentByMemberId(Long memberId);
}
