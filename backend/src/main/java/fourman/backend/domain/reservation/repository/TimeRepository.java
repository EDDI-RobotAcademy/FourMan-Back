package fourman.backend.domain.reservation.repository;

import fourman.backend.domain.reservation.entity.Time;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TimeRepository extends JpaRepository<Time, Long> {
    Optional<Time> findByTime(String timeString);
}
