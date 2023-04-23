package fourman.backend.domain.order.repository;

import fourman.backend.domain.order.entity.OrderReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderReservationRepository extends JpaRepository<OrderReservation, Long> {
}
