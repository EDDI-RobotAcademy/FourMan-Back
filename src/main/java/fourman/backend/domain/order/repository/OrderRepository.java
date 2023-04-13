package fourman.backend.domain.order.repository;

import fourman.backend.domain.order.entity.OrderInfo;
import fourman.backend.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.keyvalue.repository.config.QueryCreatorType;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderInfo, Long> {

    @Query("select oi.orderNo from OrderInfo oi")
    List<String> findFullOrderNumberByOrderNumber();

    @Query("select oi from OrderInfo oi where oi.memberId = :memberId")
    List<OrderInfo> findOrderInfoByMemberId(Long memberId);

}
