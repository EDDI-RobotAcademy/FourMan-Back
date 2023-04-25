package fourman.backend.domain.freeBoard.repository;

import fourman.backend.domain.freeBoard.entity.FreeBoard;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;

public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long> {

    @Query("select f from FreeBoard f join fetch f.member m where m.id = :memberId")
    List<FreeBoard> findFreeBoardByMemberId(Long memberId);

    @Query("select f from FreeBoard f WHERE f.title LIKE %:searchText%")
    List<FreeBoard> findSearchFreeBoardBySearchText(String searchText);
    @Query("select fb from FreeBoard fb order by fb.recommendation desc")
    List<FreeBoard> findThreeBoardByRecommendation(Pageable pageable);
}
