package fourman.backend.domain.order.repository;

import fourman.backend.domain.order.entity.OrderSeat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderSeatRepository extends JpaRepository<OrderSeat, Long> {
}
