package fourman.backend.domain.eventBoard.repository;

import fourman.backend.domain.eventBoard.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventRepository extends JpaRepository<Event, Long> {

}
