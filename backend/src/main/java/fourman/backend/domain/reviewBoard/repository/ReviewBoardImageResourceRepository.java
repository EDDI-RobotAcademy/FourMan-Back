package fourman.backend.domain.reviewBoard.repository;

import fourman.backend.domain.reviewBoard.entity.ReviewBoardImageResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewBoardImageResourceRepository extends JpaRepository<ReviewBoardImageResource, Long> {

    @Query("select I from ReviewBoardImageResource I join I.reviewBoard R where R.reviewBoardId = :reviewBoardId")
    List<ReviewBoardImageResource> findAllImagesByProductId(Long reviewBoardId);

}
