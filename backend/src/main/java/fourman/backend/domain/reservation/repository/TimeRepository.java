package fourman.backend.domain.reservation.repository;

import fourman.backend.domain.reservation.entity.Time;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeRepository extends JpaRepository<Time, Long> {
}
