package fourman.backend.domain.reservation.repository;

import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import fourman.backend.domain.reservation.entity.Seat;
import fourman.backend.domain.reservation.entity.Time;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByCafeAndTime(Cafe cafe, Time time);

    @Query("SELECT s FROM Seat s WHERE s.cafe = :cafe AND s.time = :time AND s.seatNo = :seatNo")
    Optional<Seat> findSeatNoByCafeAndTimeAndSeatNo(Cafe cafe, Time time, int seatNo);

//    Optional<Seat> findByIdAndCafeAndTime(Long seatId, Cafe cafe, Time time);

    Optional<Seat> findBySeatNoAndCafeAndTime(int seatNo, Cafe cafe, Time time);

    void deleteByCafeCafeId(Long cafeId);
}
