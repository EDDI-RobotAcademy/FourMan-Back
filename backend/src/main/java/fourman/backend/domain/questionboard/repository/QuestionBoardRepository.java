package fourman.backend.domain.questionboard.repository;

import fourman.backend.domain.questionboard.entity.QuestionBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionBoardRepository extends JpaRepository<QuestionBoard, Long> {


}
