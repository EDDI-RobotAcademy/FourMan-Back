package fourman.backend.domain.eventBoard.repository;

import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import fourman.backend.domain.eventBoard.entity.Event;
import fourman.backend.domain.member.entity.CafeCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Cafe> findByCafeCode(CafeCode cafeCode);

}
