package fourman.backend.domain.eventBoard.repository;

import fourman.backend.domain.eventBoard.entity.EventBoardImageResource;
import fourman.backend.domain.reviewBoard.entity.ReviewBoardImageResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventBoardImageResourceRepository extends JpaRepository<EventBoardImageResource, Long> {


}
