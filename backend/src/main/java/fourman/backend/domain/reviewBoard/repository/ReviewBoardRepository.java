package fourman.backend.domain.reviewBoard.repository;

import fourman.backend.domain.reviewBoard.entity.ReviewBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewBoardRepository extends JpaRepository<ReviewBoard, Long> {
}
