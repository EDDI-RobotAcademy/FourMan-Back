package fourman.backend.domain.reviewBoard.repository;

import fourman.backend.domain.reviewBoard.entity.ReviewBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ReviewBoardRepository extends JpaRepository<ReviewBoard, Long> {

    @Query("select r from ReviewBoard r join r.reviewBoardImageResourceList irl where r.reviewBoardId = :reviewBoardId")
    Optional<ReviewBoard> findImagePathByReviewBoardId(Long reviewBoardId);

}
