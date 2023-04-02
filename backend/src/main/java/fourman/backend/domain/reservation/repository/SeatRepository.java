package fourman.backend.domain.reservation.repository;

import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import fourman.backend.domain.reservation.entity.Seat;
import fourman.backend.domain.reservation.entity.Time;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByCafeAndTime(Cafe cafe, Time time);

    Optional<Seat> findByIdAndCafeAndTime(Long seatId, Cafe cafe, Time time);
}
