package fourman.backend.domain.order.repository;

import fourman.backend.domain.cafeIntroduce.entity.Cafe;
import fourman.backend.domain.member.entity.Member;
import fourman.backend.domain.order.entity.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderInfoRepository extends JpaRepository<OrderInfo, Long> {

    @Query("select oi from OrderInfo oi where oi.orderNo = :orderNo")
    Optional<OrderInfo> findExistOrderNumber(String orderNo);

    @Query("select oi from OrderInfo oi where oi.member = :member")
    List<OrderInfo> findOrderInfoByMember(Member member);

    @Query("select oi from OrderInfo oi join fetch oi.cafe c where c.cafeId = :cafeId and MONTH(oi.orderDate) = MONTH(CURRENT_DATE) and YEAR(oi.orderDate) = YEAR(CURRENT_DATE) and oi.canceledAt = null")
    List<OrderInfo> findMonthOrderInfoByCafeId(Long cafeId);

    @Query("select oi from OrderInfo oi join fetch oi.cafe c where c.cafeId = :cafeId and DAY(oi.orderDate) = DAY(CURRENT_DATE) and MONTH(oi.orderDate) = MONTH(CURRENT_DATE) and YEAR(oi.orderDate) = YEAR(CURRENT_DATE) and oi.canceledAt = null")
    List<OrderInfo> findDayOrderInfoByCafeId(Long cafeId);

    @Query("select oi from OrderInfo oi where oi.cafe = :cafe")
    List<OrderInfo> findOrderInfoByCafe(Cafe cafe);

    void deleteByCafeCafeId(Long cafeId);
}
