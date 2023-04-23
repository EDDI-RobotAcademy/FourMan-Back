package fourman.backend.domain.order.repository;

import fourman.backend.domain.member.entity.Member;
import fourman.backend.domain.order.entity.OrderInfo;
import fourman.backend.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.keyvalue.repository.config.QueryCreatorType;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderInfo, Long> {

    @Query("select oi from OrderInfo oi where oi.orderNo = :orderNo")
    Optional<OrderInfo> findExistOrderNumber(String orderNo);

    @Query("select oi from OrderInfo oi where oi.member = :member")
    List<OrderInfo> findOrderInfoByMember(Member member);

}
