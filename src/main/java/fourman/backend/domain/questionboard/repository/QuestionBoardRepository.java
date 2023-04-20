package fourman.backend.domain.questionboard.repository;

import fourman.backend.domain.questionboard.entity.QuestionBoard;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionBoardRepository extends JpaRepository<QuestionBoard, Long> {

    @Query("SELECT q FROM QuestionBoard q WHERE q.title LIKE %:searchText%")
    List<QuestionBoard> findQuestionBoardBySearchText(@Param("searchText") String searchText);

    @Query("SELECT q FROM QuestionBoard q join fetch q.member m where m.id = :memberId")
    List<QuestionBoard> findMyQuestionBoardByMemberId(Long memberId);
}
