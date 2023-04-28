package fourman.backend.domain.reviewBoard.repository;

import fourman.backend.domain.reviewBoard.entity.ReviewBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReviewBoardRepository extends JpaRepository<ReviewBoard, Long> {

    @Query("select r from ReviewBoard r join r.reviewBoardImageResourceList irl where r.reviewBoardId = :reviewBoardId")
    Optional<ReviewBoard> findImagePathByReviewBoardId(Long reviewBoardId);

    @Query("select r from ReviewBoard r join fetch r.cafe c join fetch c.cafeCode cc where cc.cafeName = :cafeName")
    List<ReviewBoard> findByCafeName(String cafeName);

    @Query("select r from ReviewBoard r join fetch r.member m where m.id = :memberId")
    List<ReviewBoard> findReviewBoardByMemberId(Long memberId);

    void deleteByCafeCafeId(Long cafeId);
}
