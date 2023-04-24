package fourman.backend.domain.order.repository;

import fourman.backend.domain.order.entity.OrderInfo;
import fourman.backend.domain.order.entity.OrderReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrderReservationRepository extends JpaRepository<OrderReservation, Long> {

    @Query("select orr from OrderReservation orr join fetch orr.seatNoList where orr.id = :id")
    Optional<OrderReservation> findByReservationId(Long id);

}
