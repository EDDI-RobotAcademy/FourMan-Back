package fourman.backend.domain.freeBoard.repository;

import fourman.backend.domain.freeBoard.entity.FreeBoardImageResource;
import fourman.backend.domain.reviewBoard.entity.ReviewBoardImageResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FreeBoardImageResourceRepository extends JpaRepository<FreeBoardImageResource, Long> {

    @Query("select I from FreeBoardImageResource I join I.freeBoard f where f.boardId = :freeBoardId")
    List<FreeBoardImageResource> findAllImagesByFreeBoardId(Long freeBoardId);

    @Query("select ir from FreeBoardImageResource ir join ir.freeBoard f where f.boardId = :freeBoardId")
    List<FreeBoardImageResource> findImagePathByFreeBoardId(Long freeBoardId);

}
