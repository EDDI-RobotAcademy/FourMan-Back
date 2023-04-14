package fourman.backend.domain.reservation.repository;

import fourman.backend.domain.member.entity.Member;
import fourman.backend.domain.reservation.entity.Reservation;
import fourman.backend.domain.reservation.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("select r from Reservation r join fetch r.member m where m.id = :memberId")
    List<Reservation> findReservationByMemberId(Long memberId);
}
