package fourman.backend.domain.eventBoard.repository;

import fourman.backend.domain.eventBoard.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByCafeCafeIdOrderByEventIdDesc(Long cafeId);
}
