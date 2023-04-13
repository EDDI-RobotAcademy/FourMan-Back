package fourman.backend.domain.order.repository;

import fourman.backend.domain.order.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    @Query("select op from OrderProduct op join fetch op.orderInfo oi where oi.orderId = :orderId")
    List<OrderProduct> findOrderProductByOrderId(Long orderId);

}
