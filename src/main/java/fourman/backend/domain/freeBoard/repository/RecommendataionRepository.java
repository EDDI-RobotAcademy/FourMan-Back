package fourman.backend.domain.freeBoard.repository;

import fourman.backend.domain.freeBoard.entity.FreeBoard;
import fourman.backend.domain.freeBoard.entity.Recommendation;
import fourman.backend.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecommendataionRepository extends JpaRepository<Recommendation, Long> {

    Recommendation findByFreeBoardAndMemberId(FreeBoard freeBoard, Long memberId);

    List<Recommendation> findAllByFreeBoard(FreeBoard freeBoard);
}
