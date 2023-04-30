package fourman.backend.domain.order.repository;

import fourman.backend.domain.cafeIntroduce.service.response.CafeTop3ProductResponse;
import fourman.backend.domain.order.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

    @Query("select op from OrderProduct op join fetch op.orderInfo oi where oi.orderId = :orderId")
    List<OrderProduct> findOrderProductByOrderId(Long orderId);

    @Query(value = "SELECT new fourman.backend.domain.cafeIntroduce.service.response.CafeTop3ProductResponse(op.productName, SUM(op.count), op.imageResource) " +
            "FROM OrderProduct op " +
            "JOIN op.orderInfo oi " +
            "JOIN oi.cafe c " +
            "WHERE c.cafeId = :cafeId " +
            "GROUP BY op.productName, op.imageResource " +
            "ORDER BY SUM(op.count) DESC")
    List<CafeTop3ProductResponse> findTopProductByCafeId(@Param("cafeId") Long cafeId);
}
