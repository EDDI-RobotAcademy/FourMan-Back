package fourman.backend.domain.freeBoard.repository;

import fourman.backend.domain.freeBoard.entity.FreeBoardComment;
import fourman.backend.domain.questionboard.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FreeBoardCommentRepository extends JpaRepository<FreeBoardComment, Long> {

    @Query("select c from FreeBoardComment c join fetch c.freeBoard f where f.boardId = :boardId")
    List<FreeBoardComment> findFreeBoardCommentByBoardId(Long boardId);

    @Query("SELECT c FROM FreeBoardComment c where c.memberId = :memberId")
    List<FreeBoardComment> findFreeBoardCommentByMemberId(Long memberId);
}
