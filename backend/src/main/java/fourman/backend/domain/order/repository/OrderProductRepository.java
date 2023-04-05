package fourman.backend.domain.order.repository;

import fourman.backend.domain.order.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

}
