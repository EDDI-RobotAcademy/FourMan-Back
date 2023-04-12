package fourman.backend.domain.reviewBoard.repository;

import fourman.backend.domain.reviewBoard.entity.ReviewBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewBoardRepository extends JpaRepository<ReviewBoard, Long> {

    @Query("select r from ReviewBoard r join r.reviewBoardImageResourceList irl where r.reviewBoardId = :reviewBoardId")
    Optional<ReviewBoard> findImagePathByReviewBoardId(Long reviewBoardId);

    @Query("select r.rating from ReviewBoard r where r.cafeName = :cafeName")
    List<Long> findRatingByCafeName(String cafeName);

    @Query("select r from ReviewBoard r where r.memberId = :memberId")
    List<ReviewBoard> findReviewBoardByMemberId(Long memberId);

}
