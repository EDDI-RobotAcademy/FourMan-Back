package fourman.backend.domain.freeBoard.repository;

import fourman.backend.domain.freeBoard.entity.FreeBoard;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FreeBoardRepository extends JpaRepository<FreeBoard, Long> {

    @Query("select f from FreeBoard f where f.memberId = :memberId")
    List<FreeBoard> findFreeBoardByMemberId(Long memberId);

    @Query("select f from FreeBoard f WHERE f.title LIKE %:searchText%")
    List<FreeBoard> findSearchFreeBoardBySearchText(String searchText);
}
